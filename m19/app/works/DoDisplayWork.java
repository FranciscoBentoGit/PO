package m19.app.works;

import m19.core.LibraryManager;
import m19.core.Work;
import m19.core.Book;
import m19.core.Dvd;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import m19.app.exception.NoSuchWorkException;

/**
 * 4.3.1. Display work.
 */
public class DoDisplayWork extends Command<LibraryManager> {

	private Input<Integer> _workId;

	/**
	 * @param receiver
	 */
	public DoDisplayWork(LibraryManager receiver) {
		super(Label.SHOW_WORK, receiver);
		_workId = _form.addIntegerInput(Message.requestWorkId());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		_form.parse();
		int workKey;
		workKey = _receiver.showWork(_workId.value());
		if (workKey < 0) {
			throw new NoSuchWorkException(_workId.value());
		}
		_display.addLine(_receiver.showWorks().get(workKey).getWorkDescription());
		_display.display();
	}
}
