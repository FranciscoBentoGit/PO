package m19.app.works;

import m19.core.LibraryManager;
import m19.core.Work;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

/**
 * 4.3.2. Display all works.
 */
public class DoDisplayWorks extends Command<LibraryManager> {

	/**
	 * @param receiver
	 */
	public DoDisplayWorks(LibraryManager receiver) {
		super(Label.SHOW_WORKS, receiver);
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {

		List<Map.Entry<Integer, Work>> listWorks = new ArrayList<>(_receiver.showWorks().entrySet());

		Comparator<Work> comparator = Comparator.comparing(Work::getId);
		Collections.sort(listWorks, Map.Entry.comparingByValue(comparator));
		for (Map.Entry<Integer, Work> entry : listWorks) {
			_display.addLine(entry.getValue().getWorkDescription());
		}
		_display.display();
	}
}
