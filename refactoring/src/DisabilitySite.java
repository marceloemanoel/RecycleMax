import java.util.Date;

class DisabilitySite extends Site 
{
	private static final Dollars FUEL_TAX_CAP = new Dollars (0.10);
	private static final int CAP = 200;
	
	public DisabilitySite(Zone zone) {
		super(zone);
	}
	
	public void addReading(Reading newReading) {
		readings[lastReadingIndex()] = newReading;
	}

	private int lastReadingIndex() {
		int i = 0;
		while(readings[i] != null){
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
		
		int usage = readings[i-1].amount() - readings[i-2].amount();
		Date end = readings[i-1].date();
		Date start = readings[i-2].date();
		start.setDate(start.getDate() + 1); //set to begining of period
		return charge(usage, start, end);
	}
	
	private Dollars charge(int fullUsage, Date start, Date end) 
	{
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
