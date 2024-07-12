package m19.core;

import java.io.Serializable;

import m19.core.behaviour.Behaviour;
import m19.core.behaviour.Regular;
import m19.core.notifications.*;
import m19.core.Category;
import m19.core.Work;
import m19.core.Book;
import m19.core.Request;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class User implements Serializable {

	private static final long serialVersionUID = 201901101344L;

	private int _userId;
	private int _userFine;
	private String _userName;
	private String _userEmail;
	private boolean _userState;
	private Behaviour _userBehaviour;

	//Works requested List
	private List<Request> _userWorksRequested = new ArrayList<>();

	//Notification Lists
	private List<Integer> _returnRequests = new ArrayList<>();
	private List<Integer> _borrowRequests = new ArrayList<>();
	private List<Notification> _userNotifications = new ArrayList<>();

	public User(String userName, String userEmail) {
		_userName = userName;
		_userEmail = userEmail;
		_userFine = 0;
		_userState = true;
		_userBehaviour = new Regular(0, 0);
	}

	public void returnCheck(Work work) {
		Iterator it = _returnRequests.iterator();
		_userNotifications.add(new ReturnNotification(work));
		while(it.hasNext()) {
			if ((Integer)it.next() == work.getId()) {
				it.remove();
				return;
			}
		}
	}

	public void borrowCheck(Work work) {
		_userNotifications.add(new BorrowNotification(work));
	}

	public List<Notification> showNotifications() {
		return _userNotifications;
	}

	public void returnWork(int workId) {
		Iterator<Request> it = _userWorksRequested.iterator();
		while (it.hasNext()) {
			Request request = it.next();
			if (request.getRequestWork().getId() == workId) {
				it.remove();
			}
		}
	}

	public void clearNotifications() {
		_userNotifications.clear();
	}

	public void setUserId(int userId) {
		_userId = userId;
	}

	public void setUserFine(int userFine) {
		_userFine = userFine;
	}

	public void setUserState(boolean state) {
		_userState = state;
	}

	public void setUserRequisition(Request request) {
		_userWorksRequested.add(request);
	}

	public void setReturnRequest(int workId) {
		_returnRequests.add(workId);
	}

	public void setBorrowRequest(int workId) {
		_borrowRequests.add(workId);
	}

	public void setUserBehaviour(Behaviour behaviour) {
		_userBehaviour = behaviour;
	}

	public String getUserDescription() {
		String _userPrintFormat = "";
		_userPrintFormat = (getId() + " - " + getName() + " - " + getEmail() + " - ");
		_userPrintFormat += (getUserBehaviour().toString() + " - " + getUserState());
		if (_userState == false) {
			_userPrintFormat += (" - EUR " + getUserFine());
		}
		return _userPrintFormat;
	}

	public boolean getRequestTwice(int workId) {
		for (Request req: _userWorksRequested) {
			if (req.getRequestWork().getId() == workId) {
				return true;
			}
		}
		return false;
	}

	public Integer getId() {
		return _userId;
	}

	public String getName() {
		return _userName;
	}

	public String getEmail() {
		return _userEmail;
	}

	public Behaviour getUserBehaviour() {
		return _userBehaviour;
	}

	public String getUserState() {
		return _userState ? "ACTIVO" : "SUSPENSO";
	}

	public int getUserFine() {
		return _userFine;
	}

	public List<Request> getUserWorksRequested() {
		return _userWorksRequested;
	}

	public int getUserRequestsNumber() {
		return _userWorksRequested.size();
	}

	public List<Integer> getBorrowRequests() {
		return _borrowRequests;
	}

	public List<Integer> getReturnRequests() {
		return _returnRequests;
	}
}