package m19.core;

import m19.core.exception.BadEntrySpecificationException;
import m19.app.exception.UserRegistrationFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;

public class Parser {

	private Library _library;

	public Parser(Library lib) {
		_library = lib;
	}

	void parseFile(String filename) throws IOException, BadEntrySpecificationException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;

			while ((line = reader.readLine()) != null)
				parseLine(line);
		}
	}

	private void parseLine(String line) throws BadEntrySpecificationException {
		String[] components = line.split(":");
		
		switch (components[0]) {
		case "DVD":
			parseDVD(components, line);
			break;
		case "BOOK":
			parseBook(components, line);
			break;
		case "USER":
			parseUser(components, line);
			break;

		default:
			throw new BadEntrySpecificationException("Invalid type " + components[0] + " in line " + line);
		}
	}

	private void parseDVD(String[] components, String line) throws BadEntrySpecificationException {
		if (components.length != 7) {
			throw new BadEntrySpecificationException("Wrong number of fields (6) in " + line);
		}
		_library.registerDvd(components[1], components[2], Integer.parseInt(components[3]),
				Category.valueOf(components[4]), components[5], Integer.parseInt(components[6]));

		// add dvd to _library
	}

	private void parseBook(String[] components, String line) throws BadEntrySpecificationException {
		if (components.length != 7) {
			throw new BadEntrySpecificationException("Wrong number of fields (6) in " + line);
		}

		_library.registerBook(components[1], components[2], Integer.parseInt(components[3]),
				Category.valueOf(components[4]), components[5], Integer.parseInt(components[6]));
		// add book to _library
	}

	private void parseUser(String[] components, String line) throws BadEntrySpecificationException {
		if (components.length != 3) {
			throw new BadEntrySpecificationException("Wrong number of fields (2) in " + line);
		}
		_library.registerUser(components[1], components[2]);
		// add user to _library
	}
}