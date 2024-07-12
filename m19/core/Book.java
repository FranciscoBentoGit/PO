package m19.core;

public class Book extends Work {

	private String _bookAuthor;
	private String _bookIsbn;

	public Book(String bookTitle, String bookAuthor, int bookPrice, Category bookCategory, String bookIsbn, int bookNumbers) {
		super(bookTitle, bookPrice, bookCategory, bookNumbers);
		_bookAuthor = bookAuthor;
		_bookIsbn = bookIsbn;
	}

	@Override
	public String getWorkDescription() {
		return super.getWorkDescription() + getCreator() + " - " + getBookIsbn();
	}

	@Override
	public String getCreator() {
		return _bookAuthor;
	}

	public String getBookIsbn() {
		return _bookIsbn;
	}

	public String getClassName() {
		return "Livro";
	}
}