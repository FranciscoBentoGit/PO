package m19.app.users;

import m19.core.LibraryManager;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.exception.UserIsActiveException;
import m19.app.exception.NoSuchUserException;

/**
 * 4.2.5. Settle a fine.
 */
public class DoPayFine extends Command<LibraryManager> {

	private Input<Integer> _userId;
	/**
	 * @param receiver
	 */
	public DoPayFine(LibraryManager receiver) {
		super(Label.PAY_FINE, receiver);
		_userId = _form.addIntegerInput(Message.requestUserId());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		_form.parse();
		int userKey = -1;
		userKey = _receiver.showUser(_userId.value());
		if (userKey < 0) {
			throw new NoSuchUserException(_userId.value());
		}
		if (_receiver.payUserFine(_userId.value()) == false) {
			throw new UserIsActiveException(_userId.value());
		}
	}

}
