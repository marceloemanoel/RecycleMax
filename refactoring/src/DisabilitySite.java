import java.util.Date;

class DisabilitySite extends Site {
	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		super(zone);
	}

	public Dollars charge() {
		int i = lastReadingIndex();

		if (i < 2) {
			throw new NullPointerException();
		}

		int usage = lastReading().amount() - previousReading().amount();
		Date end = lastReading().date();
		Date start = previousReading().date();
		start.setDate(start.getDate() + 1); // set to begining of period
		return charge(usage, start, end);
	}
	
	private Dollars charge(int fullUsage, Date start, Date end) {
		Dollars result;
		int usage = Math.min(fullUsage, CAP);

		double summerFraction = zone.summerFraction(start, end);
		
		result = new Dollars ((usage * zone.summerRate() * summerFraction) + (usage * zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (Math.max(fullUsage - usage, 0) * 0.062));
		result = result.plus(new Dollars (result.times(TAX_RATE)));
		Dollars fuel = new Dollars(fullUsage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars (result.plus(fuel.times(TAX_RATE).min(FUEL_TAX_CAP)));
		return result;
	}
}
