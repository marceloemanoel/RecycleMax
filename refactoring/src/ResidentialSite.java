import java.util.Date;

class ResidentialSite extends Site {

	ResidentialSite(Zone zone) {
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
	
	private Dollars charge(int usage, Date start, Date end) {
		
		double summerFraction = zone.summerFraction(start, end);
		
		Dollars result = new Dollars ((usage * zone.summerRate() * summerFraction) + (usage * zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (result.times(TAX_RATE)));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars (result.plus(fuel.times(TAX_RATE)));
		return result;
	}
}