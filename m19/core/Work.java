package m19.core;

import java.io.Serializable;

public abstract class Work implements Serializable {

	private static final long serialVersionUID = 201901101344L;
	
	private int _workId;
	private int _workPrice;
	private int _workNumbers;
	private int _availableWorks;
	private String _workTitle;
	private Category _workCategory;

	public Work(String workTitle, int workPrice, Category workCategory, int workNumbers) {
		_workTitle = workTitle;
		_workPrice = workPrice;
		_workCategory = workCategory;
		_workNumbers = workNumbers;
		_availableWorks = _workNumbers;
	}

	public void incrementWorkNumber() {
		_availableWorks++;
	}

	public void decrementWorkNumber() {
		_availableWorks--;
	}

	public void setWorkId(int workId) {
		_workId = workId;
	}

	public String getWorkDescription() {
		String printFormat = "";
		
		printFormat = (getId() + " - " + getAvailableWorks() + " de  ");
		printFormat += (getWorkNumbers() + " - " + getClassName() + " - ");
		printFormat += (getWorkTitle() + " - " + getWorkPrice() + " - " + getWorkCategory().getCategory() + " - ");
		
		return printFormat;
	}

	public Integer getId() {
		return _workId;
	}

	public Integer getAvailableWorks() {
		return _availableWorks;
	}

	public String getWorkTitle() {
		return _workTitle;
	}

	public Integer getWorkPrice() {
		return _workPrice;
	}

	public Category getWorkCategory() {
		return _workCategory;
	}

	public Integer getWorkNumbers() {
		return _workNumbers;
	}

	public String getClassName() {
		return "Obra";
	}

	public abstract String getCreator();

}