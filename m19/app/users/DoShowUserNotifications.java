package m19.app.users;

import m19.core.LibraryManager;
import m19.core.notifications.Notification;
import m19.core.User;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.exception.NoSuchUserException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
public class DoShowUserNotifications extends Command<LibraryManager> {

	private Input<Integer> _userId;

	/**
	 * @param receiver
	 */
	public DoShowUserNotifications(LibraryManager receiver) {
		super(Label.SHOW_USER_NOTIFICATIONS, receiver);
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
		for(Notification notif: _receiver.showUsers().get(userKey).showNotifications()) {
			_display.addLine(notif.getNotificationMessage());
		}
		_receiver.showUsers().get(userKey).clearNotifications();
		_display.display();
	}

}
