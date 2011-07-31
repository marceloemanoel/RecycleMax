
public class Site {

	protected static final double TAX_RATE = 0.05;
	protected Reading[] readings = new Reading[1000];
	protected Zone zone;

	public Site() {

	}

	public Site(Zone zone) {
		this.zone = zone;
	}

	public void addReading(Reading newReading) {
		readings[lastReadingIndex()] = newReading;
	}

	protected int lastReadingIndex() {
		int i = 0;
		while(readings[i] != null){
			i++;
		}
		return i;
	}

	protected Reading previousReading() {
		return readings[lastReadingIndex() - 2];
	}

	protected Reading lastReading() {
		if(lastReadingIndex() > 0) {
			return readings[lastReadingIndex() - 1];
		}
		return null;
	}

}
