package m19.app.users;

import m19.core.LibraryManager;
import m19.core.User;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.exception.NoSuchUserException;

/**
 * 4.2.2. Show specific user.
 */
public class DoShowUser extends Command<LibraryManager> {

	private Input<Integer> _userId;

	/**
	 * @param receiver
	 */
	public DoShowUser(LibraryManager receiver) {
		super(Label.SHOW_USER, receiver);
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
		_display.addLine(_receiver.showUsers().get(userKey).getUserDescription());
		_display.display();
	}
}
