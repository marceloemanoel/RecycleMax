import java.util.Date;

class DisabilitySite extends Site 
{
	private static final Dollars FUEL_TAX_CAP = new Dollars (0.10);
	private static final double TAX_RATE = 0.05;
	private static final int CAP = 200;
	
	public DisabilitySite(Zone zone) {
		super(zone);
	}
	
	public void addReading(Reading newReading) {
		_readings[lastReadingIndex()] = newReading;
	}

	private int lastReadingIndex() {
		int i = 0;
		while(_readings[i] != null){
			i++;
		}
		return i;
	}
	
	public Dollars charge()
	{
		int i = lastReadingIndex();
		
		if(i < 2) {
			throw new NullPointerException();
		}
		
		int usage = _readings[i-1].amount() - _readings[i-2].amount();
		Date end = _readings[i-1].date();
		Date start = _readings[i-2].date();
		start.setDate(start.getDate() + 1); //set to begining of period
		return charge(usage, start, end);
	}
	
	private Dollars charge(int fullUsage, Date start, Date end) 
	{
		Dollars result;
		int usage = Math.min(fullUsage, CAP);

		double summerFraction = _zone.summerFraction(this, start, end);
		
		result = new Dollars ((usage * _zone.summerRate() * summerFraction) + (usage * _zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (Math.max(fullUsage - usage, 0) * 0.062));
		result = result.plus(new Dollars (result.times(TAX_RATE)));
		Dollars fuel = new Dollars(fullUsage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars (result.plus(fuel.times(TAX_RATE).min(FUEL_TAX_CAP)));
		return result;
	}
}
