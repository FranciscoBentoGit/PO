package m19.core;

public class Dvd extends Work {

	private String _dvdDirector;
	private String _dvdIgac;

	public Dvd(String dvdTitle, String dvdDirector, int dvdPrice, Category dvdCategory, String dvdIgac, int dvdNumbers) {
		super(dvdTitle, dvdPrice, dvdCategory, dvdNumbers);
		_dvdDirector = dvdDirector;
		_dvdIgac = dvdIgac;
	}

	@Override
	public String getWorkDescription() {
		return super.getWorkDescription() + getCreator() + " - " + getDvdIgac();
	}

	@Override
	public String getCreator() {
		return _dvdDirector;
	}

	public String getDvdIgac() {
		return _dvdIgac;
	}

	public String getClassName() {
		return "DVD";
	}
}