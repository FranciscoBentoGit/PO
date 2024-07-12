package m19.app.requests;

import m19.core.LibraryManager;
import m19.core.Request;
import m19.core.Work;
import m19.core.User;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.exception.NoSuchWorkException;
import m19.app.exception.NoSuchUserException;
import m19.app.exception.WorkNotBorrowedByUserException;


/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {

	private Input<Integer> _workId;
	private Input<Integer> _userId;
	private boolean _finePrompt;

	/**
	 * @param receiver
	 */
	public DoReturnWork(LibraryManager receiver) {
		super(Label.RETURN_WORK, receiver);
		_userId = _form.addIntegerInput(Message.requestUserId());
		_workId = _form.addIntegerInput(Message.requestWorkId());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		if (_finePrompt == true) {
			_form.clear();
			_userId = _form.addIntegerInput(Message.requestUserId());
			_workId = _form.addIntegerInput(Message.requestWorkId());
			_finePrompt = false;
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
		boolean requested = false;
		User user = _receiver.showUsers().get(userKey);
		Work work = _receiver.showWorks().get(workKey);

		for (Request req: user.getUserWorksRequested()) {
			if (req.getRequestWork().getId() == _workId.value()) {
				requested = true;
			}
		}
		if (requested != true) {
			throw new WorkNotBorrowedByUserException(_workId.value(), _userId.value());
		}
		if (_receiver.setUserReturn(user, work) > 0) {
			_form.clear();
			_display.addLine(Message.showFine(_userId.value(), user.getUserFine()));
			_display.display();
			Input<String> fine = _form.addStringInput(Message.requestFinePaymentChoice());
			_form.parse();
			_finePrompt = true;
			if (fine.value().equals("s")) {
				user.setUserFine(0);
				if (_receiver.checkForLateRequisition(_receiver.displayDate(), _userId.value()) == false) {
					user.setUserState(true);
				} 	
			}
		}
	}

}
