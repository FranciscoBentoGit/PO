package m19.app.main;

import m19.core.LibraryManager;

import pt.tecnico.po.ui.Command;

/**
 * 4.1.2. Display the current date.
 */
public class DoDisplayDate extends Command<LibraryManager> {

	/**
	 * @param receiver
	 */
	public DoDisplayDate(LibraryManager receiver) {
		super(Label.DISPLAY_DATE, receiver);
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
		int date = _receiver.displayDate();
		_display.addLine(Message.currentDate(date));
		_display.display();

	}

}
