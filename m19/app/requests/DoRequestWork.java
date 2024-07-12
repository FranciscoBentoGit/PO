package m19.app.requests;

import m19.core.LibraryManager;
import m19.core.User;
import m19.core.Work;
import m19.core.rules.*;
import m19.core.Request;
import m19.core.ConcreteRequest;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.exception.NoSuchWorkException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.RuleFailedException;


/**
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {

	private Input<Integer> _workId;
	private Input<Integer> _userId;
	private boolean _notificationClear;
	/**
	 * @param receiver
	 */
	public DoRequestWork(LibraryManager receiver) {
		super(Label.REQUEST_WORK, receiver);
		_userId = _form.addIntegerInput(Message.requestUserId());
		_workId = _form.addIntegerInput(Message.requestWorkId());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		if (_notificationClear == true) {
			_form.clear();
			_userId = _form.addIntegerInput(Message.requestUserId());
			_workId = _form.addIntegerInput(Message.requestWorkId());
			_notificationClear = false;
		}
		_form.parse();
		int userKey;
		int workKey;
		userKey = _receiver.showUser(_userId.value());
		workKey = _receiver.showWork(_workId.value());
		if (userKey < 0) {
			throw new NoSuchUserException(_userId.value());
		} else if (workKey < 0) {
			throw new NoSuchWorkException(_workId.value());
		}
		User user = _receiver.showUsers().get(userKey);
		Work work = _receiver.showWorks().get(workKey);
		int date = _receiver.displayDate();
		Request request = 
			new CheckWorkPriceThreshold(user, work, date, 
			new CheckWorkReference(user, work, date,
			new CheckMaxRequests(user, work, date,
			new CheckAvailableWorks(user, work, date,
			new CheckUserSuspended(user, work, date, 
			new CheckRequestTwice(user, work, date,
			new ConcreteRequest(user, work, date)))))));	

		if (request.isValidRequest() == 3) {
			_form.clear();
			Input<String> notif = _form.addStringInput(Message.requestReturnNotificationPreference());
			_form.parse();
			_notificationClear = true;
			if (notif.value().equals("s")) {
				user.setReturnRequest(_workId.value());
			}
		} else if (request.isValidRequest() != 0) {
			throw new RuleFailedException(_userId.value(), _workId.value(), request.isValidRequest());
		} else {
			_receiver.setUserRequisition(request, user, work);
			_display.addLine(Message.workReturnDay(_workId.value(), request.getRequestDeadline()));
			_display.display();
		}
	}
}
