package m19.core;

/*
Projeto realizado por:
Francisco Bento - 93581
Pedro Morais 	- 93607
*/

import m19.core.exception.MissingFileAssociationException;
import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.ImportFileException;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * The façade class.
 */
public class LibraryManager {

	private Library _library;
	private String _fileName;

	public LibraryManager() {
		_library = new Library();
		_fileName = null;
	}

	public LibraryManager(Library library) {
		_library = library;
	}

	public void registerUser(String userName, String userEmail) {
		_library.registerUser(userName, userEmail);
	}

	public int showUser(int userID) {
		return _library.showUser(userID);
	}

	public int showWork(int workID) {
		return (_library.showWork(workID));
	}

	public Map<Integer, User> showUsers() {
		return _library.showUsers();
	}

	public Map<Integer, Work> showWorks() {
		return _library.showWorks();
	}

	public int displayDate() {
		return _library.displayDate();
	}

	public boolean checkForLateRequisition(int date, int userId) {
		return _library.checkForLateRequisition(date, userId);
	}

	public boolean payUserFine(int userId) {
		return _library.payUserFine(userId);
	}

	public void advanceDate(int daysToAdvance) {
		_library.advanceDate(daysToAdvance);
	}

	public void setUserRequisition(Request request, User user, Work work) {
		_library.setUserRequisition(request, user, work);
	}

	public int setUserReturn(User user, Work work) {
		return _library.setUserReturn(user, work);
	}

	public int getUserId() {
		return _library.getUserId();
	}

	public String getFileName() {
		return _fileName;
	}

	public boolean getFileAssociation() {
		return _fileName != null;
	}

	// method to get users library
	/**
	 * Serialize the persistent state of this application.
	 * 
	 * @throws MissingFileAssociationException if the name of the file to store the
	 *                                         persistent state has not been set
	 *                                         yet.
	 * @throws IOException                     if some error happen during the
	 *                                         serialization of the persistent state
	 * 
	 */
	public void save() throws MissingFileAssociationException, IOException {
		if (_fileName != null) {
			try {
				saveAs(_fileName);
			} catch (IOException | MissingFileAssociationException e) {
				throw e;
			}
		} else {
			throw new MissingFileAssociationException();
		}
	}

	/**
	 * Serialize the persistent state of this application into the specified file.
	 * 
	 * @param filename the name of the target file
	 *
	 * @throws MissingFileAssociationException if the name of the file to store the
	 *                                         persistent is not a valid one.
	 * @throws IOException                     if some error happen during the
	 *                                         serialization of the persistent state
	 */
	public void saveAs(String filename) throws MissingFileAssociationException, IOException {
		try (FileOutputStream fileoutput = new FileOutputStream(filename);
			ObjectOutputStream objoutput = new ObjectOutputStream(fileoutput)) {
			objoutput.writeObject(_library);
		} 
		_fileName = filename;
	}

	/**
	 * Recover the previously serialized persitent state of this application.
	 * 
	 * @param filename the name of the file containing the perssitente state to
	 *                 recover
	 *
	 * @throws IOException            if there is a reading error while processing
	 *                                the file
	 * @throws FileNotFoundException  if the file does not exist
	 * @throws ClassNotFoundException
	 */
	public void load(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		try(FileInputStream fileinput = new FileInputStream(filename);
		    ObjectInputStream objinput = new ObjectInputStream(fileinput)) {
			_library = (Library) objinput.readObject();
			_fileName = filename;
		} 
	}

	/**
	 * Set the state of this application from a textual representation stored into a
	 * file.
	 * 
	 * @param datafile the filename of the file with the textual represntation of
	 *                 the state of this application.
	 * @throws ImportFileException if it happens some error during the parsing of
	 *                             the textual representation.
	 */
	public void importFile(String datafile) throws ImportFileException {
		try {
			_library.importFile(datafile);
		} catch (IOException | BadEntrySpecificationException e) {
			throw new ImportFileException(e);
		}
	}
}
