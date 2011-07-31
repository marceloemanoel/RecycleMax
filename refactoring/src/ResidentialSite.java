import java.util.Date;

class ResidentialSite extends Site
{

	private static final double TAX_RATE = 0.05;
	
	ResidentialSite (Zone zone) {
		super(zone);
	}
	
	public void addReading(Reading newReading) {
		// add reading to end of array
		_readings[lastReadingIndex()] = newReading;
	}

	private int lastReadingIndex() {
		int i = 0;
		while (_readings[i] != null) i++;
		return i;
	}
	
	public Dollars charge() {
		int i = lastReadingIndex();
		
		if( i < 2 ) {
			throw new NullPointerException();
		}
		
		int usage = _readings[i-1].amount() - _readings[i-2].amount();
		Date end = _readings[i-1].date();
		Date start = _readings[i-2].date();
		start.setDate(start.getDate() + 1); //set to begining of period
		return charge(usage, start, end);
	}
	
	private Dollars charge(int usage, Date start, Date end) {
		Dollars result;
		
		double summerFraction = _zone.summerFraction(this, start, end);
		
		result = new Dollars ((usage * _zone.summerRate() * summerFraction) + (usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (result.times(TAX_RATE)));
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars (result.plus(fuel.times(TAX_RATE)));
		return result;
	}
}