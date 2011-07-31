import java.util.Date;

class ResidentialSite extends Site
 {

	ResidentialSite(Zone zone) {
		super(zone);
	}

	public void addReading(Reading newReading) {
		// add reading to end of array
		readings[lastReadingIndex()] = newReading;
	}

	private int lastReadingIndex() {
		int i = 0;
		while (readings[i] != null)
			i++;
		return i;
	}
	
	public Dollars charge() {
		int i = lastReadingIndex();

		if (i < 2) {
			throw new NullPointerException();
		}

		int usage = readings[i - 1].amount() - readings[i - 2].amount();
		Date end = readings[i - 1].date();
		Date start = readings[i - 2].date();
		start.setDate(start.getDate() + 1); // set to begining of period
		return charge(usage, start, end);
	}
	
	private Dollars charge(int usage, Date start, Date end) {
		Dollars result;
		
		double summerFraction = zone.summerFraction(start, end);
		
		result = new Dollars ((usage * zone.summerRate() * summerFraction) + (usage * zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (result.times(TAX_RATE)));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars (result.plus(fuel.times(TAX_RATE)));
		return result;
	}
}