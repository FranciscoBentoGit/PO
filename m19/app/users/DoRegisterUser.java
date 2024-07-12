package m19.app.users;

import m19.core.LibraryManager;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.users.Message;
import m19.app.exception.UserRegistrationFailedException;

/**
 * 4.2.1. Register new user.
 */
public class DoRegisterUser extends Command<LibraryManager> {

	private Input<String> _userName;
	private Input<String> _userEmail;

	/**
	 * @param receiver
	 */
	public DoRegisterUser(LibraryManager receiver) {
		super(Label.REGISTER_USER, receiver);
		_userName = _form.addStringInput(Message.requestUserName());
		_userEmail = _form.addStringInput(Message.requestUserEMail());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		_form.parse();
		if ((_userName.value() == null || _userName.value().isEmpty()) || (_userEmail.value() == null) || _userEmail.value().isEmpty()) {
			throw new UserRegistrationFailedException(_userName.value(), _userEmail.value());
		}
		_receiver.registerUser(_userName.value(), _userEmail.value());
		_display.addLine(Message.userRegistrationSuccessful(_receiver.getUserId() - 1));
		_display.display();
	}
}
