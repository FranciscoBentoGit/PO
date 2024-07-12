package m19.core;

import m19.core.exception.MissingFileAssociationException;
import m19.core.behaviour.Behaviour;
import m19.core.exception.BadEntrySpecificationException;

import java.io.Serializable;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 201901101344L;

	private Map<Integer, User> _userList;
	private Map<Integer, Work> _workList;

	private Date _libraryDate;

	private int _userId;
	private int _workId;

	public Library() {
		_userList = new HashMap<>();
		_workList = new HashMap<>();
		_libraryDate = new Date();
	}

	/**
	 * Register a new user in the library
	 * 
	 * @param userName 		Title of the book to register
	 * @param userEmail 	Author of the book to register
	 */
	public void registerUser(String userName, String userEmail) {
		User user = new User(userName, userEmail);
		user.setUserId(_userId);
		_userList.put(_userId, user);
		_userId++;
	}

	/**
	 * Register a new book in the library
	 * 
	 * @param bookTitle 	Title of the book to register
	 * @param bookAuthor 	Author of the book to register
	 * @param bookPrice 	Price of the book to register
	 * @param bookCategory 	Category of the book to register
	 * @param bookIsbn 		ISBN of the book to register
	 * @param bookNumbers 	Number of copies of the book to register
	 */
	public void registerBook(String bookTitle, String bookAuthor, int bookPrice, Category bookCategory, String bookIsbn, int bookNumbers) {
		Book book = new Book(bookTitle, bookAuthor, bookPrice, bookCategory, bookIsbn, bookNumbers);
		book.setWorkId(_workId);
		_workList.put(_workId, book);
		_workId++;
	}

	/**
	 * Register a new DVD in the library
	 * 
	 * @param dvdTitle 		Title of the DVD to register
	 * @param dvdDirector 	Director of the DVD to register
	 * @param dvdPrice 		Price of the DVD to register
	 * @param dvdCategory 	Category of the DVD to register
	 * @param dvdIgac 		IGAC of the DVD to register
	 * @param dvdNumbers 	Number of copies of the DVD to register
	 */
	public void registerDvd(String dvdTitle, String dvdDirector, int dvdPrice, Category dvdCategory, String dvdIgac, int dvdNumbers) {
		Dvd dvd = new Dvd(dvdTitle, dvdDirector, dvdPrice, dvdCategory, dvdIgac, dvdNumbers);
		dvd.setWorkId(_workId);
		_workList.put(_workId, dvd);
		_workId++;
	}

	/**
	 * Read the given user identifier and search for it in the
	 * library's list of users
	 * 
	 * @param userId ID of the user that the user wants to search
	 */
	public int showUser(int userId) {
		if (_userList.containsKey(userId)) {
			return userId;
		}
		return -1;
	}

	/**
	 * Read the given work identifier and search for it in the
	 * library's list of works
	 * 
	 * @param workId ID of the work that the user wants to search
	 */
	public int showWork(int workId) {
		if (_workList.containsKey(workId)) {
			return workId;
		}
		return -1;
	}

	
	/** 
	 * Check if the user associated with the given user identifier has any
	 * requisitios that are past their deadline date
	 * 
	 * @param date
	 * @param userId
	 */
	public boolean checkForLateRequisition(int date, int userId) {
		for(Request req: _userList.get(userId).getUserWorksRequested()) {
			if (_libraryDate.getDate() - req.getRequestDeadline() > 0) {
				return true;
			}
		}	
		return false;
	}

	
	/** 
	 * Pays the fine associated with a specific user. If the user has no more
	 * late requisitions, the user's state is set to regular
	 * 
	 * @param userId
	 */
	public boolean payUserFine(int userId) {
		if (showUsers().get(userId).getUserState() == "SUSPENSO") {
			showUsers().get(userId).setUserFine(0);
			if (checkForLateRequisition(_libraryDate.getDate(), userId) == false) {
				showUsers().get(userId).setUserState(true);
			}
			return true;
		}
		return false;
	}

	
	/** 
	 * Returns the Map that keeps user ids as keys and users as values
	 */
	public Map<Integer, User> showUsers() {
		return Collections.unmodifiableMap(_userList);
	}

	
	/** 
	 * Returns the Map that keeps work ids as keys and works as values
	 */
	public Map<Integer, Work> showWorks() {
		return Collections.unmodifiableMap(_workList);
	}

	
	/** 
	 * Given a request, a user, and a work, requests a specific work for a user
	 * If any other user has interest in this requisition, it will notify them
	 * 
	 * @param request
	 * @param user
	 * @param work
	 */
	public void setUserRequisition(Request request, User user, Work work) {
		user.setUserRequisition(request);
		work.decrementWorkNumber();
		for(Map.Entry<Integer, User> us: _userList.entrySet()) {
			if (us.getValue().getBorrowRequests().contains(work.getId())) {
				us.getValue().borrowCheck(work);
			}
		}
	}

	
	/** 
	 * Given a request, a user, and a work, returns a specific work for a user
	 * If any other user has interest in this return, it will notify them
	 * 
	 * @param user
	 * @param work
	 */
	public int setUserReturn(User user, Work work) {
		work.incrementWorkNumber();
		for(Request req: user.getUserWorksRequested()) {
			if (req.getRequestWork().getId() == work.getId()) {
				if (_libraryDate.getDate() > req.getRequestDeadline()) {
					user.getUserBehaviour().userLateDelivery(user);
					user.setUserFine(user.getUserFine() + ((_libraryDate.getDate() - req.getRequestDeadline()) * 5));
				} else {
					user.getUserBehaviour().userTimelyDelivery(user);
				}
			}
		}
		for(Map.Entry<Integer, User> us: _userList.entrySet()) {
			if (us.getValue().getReturnRequests().contains(work.getId())) {
				us.getValue().returnCheck(work);
			}
		}
		user.returnWork(work.getId());
		return user.getUserFine();
	}

	
	/** 
	 * Returns the library-kept user id
	 */
	public int getUserId() {
		return _userId;
	}

	
	/** 
	 * Returns the library-kept work id
	 */
	public int getWorkId() {
		return _workId;
	}


	
	/** 
	 * Returns the library's current date
	 */
	public int displayDate() {
		return _libraryDate.getDate();
	}

	
	/** 
	 * Given a specific integer, advance the library's date
	 * by the same value, while also updating the library's state
	 * 
	 * @param daysToAdvance
	 */
	public void advanceDate(int daysToAdvance) {
		_libraryDate.advanceDate(daysToAdvance);
		updateLibrary();
	}

	/** 
	 * This method is called whenever the library's date is advanced
	 * It will check for user's late requisitions and update their status
	 */
	public void updateLibrary() {
		for(Map.Entry<Integer, User> us: _userList.entrySet()) {
			if (checkForLateRequisition(displayDate(),us.getValue().getId()) == true) {
				us.getValue().setUserState(false);
			}
		}
	}

	/**
	 * Read the text input file at the beginning of the program and populates the
	 * instances of the various possible types (books, DVDs, users).
	 * 
	 * @param filename name of the file to load
	 * @throws BadEntrySpecificationException
	 * @throws IOException
	 */
	void importFile(String filename) throws BadEntrySpecificationException, IOException {
		Parser parser = new Parser(this);
		parser.parseFile(filename);
	}

}
