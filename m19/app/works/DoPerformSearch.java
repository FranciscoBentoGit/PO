package m19.app.works;

import m19.core.LibraryManager;
import m19.core.Work;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 4.3.3. Perform search according to miscellaneous criteria.
 */
public class DoPerformSearch extends Command<LibraryManager> {

	private Input<String> _searchTerm;

	/**
	 * @param m
	 */
	public DoPerformSearch(LibraryManager m) {
		super(Label.PERFORM_SEARCH, m);
		_searchTerm = _form.addStringInput(Message.requestSearchTerm());
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
		_form.parse();

		List<Map.Entry<Integer, Work>> listWorks = new ArrayList<>(_receiver.showWorks().entrySet());
		Comparator<Work> comparator = Comparator.comparing(Work::getId);

		for (Map.Entry<Integer, Work> work : listWorks) {
			if (work.getValue().getCreator().toLowerCase().contains(_searchTerm.value().toLowerCase())
					|| work.getValue().getWorkTitle().toLowerCase().contains(_searchTerm.value().toLowerCase())) {
				_display.addLine(work.getValue().getWorkDescription());
			}
		}
		_display.display();
	}
}
