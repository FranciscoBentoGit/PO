package m19.app.users;

import m19.core.LibraryManager;
import m19.core.User;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

/**
 * 4.2.4. Show all users.
 */
public class DoShowUsers extends Command<LibraryManager> {

	/**
	 * @param receiver
	 */
	public DoShowUsers(LibraryManager receiver) {
		super(Label.SHOW_USERS, receiver);
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
		List<Map.Entry<Integer, User>> listUsers = new ArrayList<>(_receiver.showUsers().entrySet());

		Comparator<User> comparator = Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER).thenComparingInt(User::getId);
		Collections.sort(listUsers, Map.Entry.comparingByValue(comparator));
		
		for (Map.Entry<Integer, User> entry : listUsers) {
			_display.addLine(entry.getValue().getUserDescription());
		}
		_display.display();
	}
}
