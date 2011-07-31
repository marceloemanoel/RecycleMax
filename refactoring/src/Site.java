import java.util.ArrayList;
import java.util.List;

public class Site {

	protected static final double TAX_RATE = 0.05;

	protected List<Reading> readings;
	protected Zone zone;

	public Site() {
		readings = new ArrayList<Reading>();
	}

	public Site(Zone zone) {
		this();
		this.zone = zone;
	}

	public void addReading(Reading newReading) {
		readings.add(newReading);
	}

	protected int lastReadingIndex() {
		return readings.size();
	}

	protected Reading previousReading() {
		return readings.get(lastReadingIndex() - 2);
	}

	protected Reading lastReading() {
		if (readings.isEmpty()) {
			return null;
		}
		return readings.get(lastReadingIndex() - 1);
	}

}
