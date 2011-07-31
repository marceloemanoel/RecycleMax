import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Site {

	protected static final double TAX_RATE = 0.05;
	protected static final double FUEL_TAX = 0.0175;

	protected List<Reading> readings;
	protected Zone zone;

	public Site(Zone zone) {
		readings = new ArrayList<Reading>();
		this.zone = zone;
	}

	public void addReading(Reading newReading) {
		readings.add(newReading);
	}

	protected Reading lastReading() {
		if (readings.isEmpty()) {
			return null;
		}
		return readings.get(lastReadingIndex());
	}

	private int lastReadingIndex() {
		return readings.size() - 1;
	}

	protected Reading previousReading() {
		return readings.get(lastReadingIndex() - 1);
	}

	protected Dollars baseTaxes() {
		return baseCharge().times(TAX_RATE);
	}

	protected Dollars fuelCharge() {
		return new Dollars(usage() * FUEL_TAX);
	}

	protected Dollars charge() {
		return baseCharge().plus(baseTaxes()).plus(taxes());
	}

	protected Dollars taxes() {
		return fuelCharge().plus(fuelChargeTaxes());
	}

	protected abstract Dollars baseCharge();

	protected int usage() {
		return lastReading().amount() - previousReading().amount();
	}

	protected DateInterval lastPeriod() {
		Date start = previousReading().date();
		start.setDate(start.getDate() + 1); // set to begining of period

		Date end = lastReading().date();

		DateInterval lastPeriod = new DateInterval(start, end);
		return lastPeriod;
	}

	protected Dollars fuelChargeTaxes() {
		return fuelCharge().times(TAX_RATE);
	}

}
