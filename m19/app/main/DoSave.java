package m19.app.main;

import m19.core.LibraryManager;
import m19.core.exception.MissingFileAssociationException;
import m19.app.exception.FileOpenFailedException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

import java.io.IOException;

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<LibraryManager> {

	private Input<String> _fileName;

	/**
	 * @param receiver
	 */
	public DoSave(LibraryManager receiver) {
		super(Label.SAVE, receiver);
		if (!_receiver.getFileAssociation()) {
			_fileName = _form.addStringInput(Message.newSaveAs());
		}
	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		try {
			if (_receiver.getFileAssociation()) {
				_receiver.save();
			} else {
				_form.parse();
				_receiver.saveAs(_fileName.value());
			}
		} catch (IOException | MissingFileAssociationException e) {
			if (_receiver.getFileAssociation()) {
				throw new FileOpenFailedException(_receiver.getFileName());
			} else {
				throw new FileOpenFailedException(_fileName.value());
			}
		}
	}
}
