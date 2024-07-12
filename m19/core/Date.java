package m19.core;

import java.io.Serializable;

public class Date implements Serializable {

	private static final long serialVersionUID = 201901101344L;
	
	private int _currentDate;

	public Date() {
		_currentDate = 0;
	}

	public void advanceDate(int daysToAdvance) {
		if (daysToAdvance > 0) {
			_currentDate += daysToAdvance;
		}
	}

	public int getDate() {
		return _currentDate;
	}
}