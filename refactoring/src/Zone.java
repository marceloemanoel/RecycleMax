import java.util.Date;

class Zone {
	private Date _summerEnd;
	private Date _summerStart;
	private double _winterRate;
	private double _summerRate;
	private String _name;
	
	Zone(String name, double summerRate, double winterRate, Date summerStart, Date summerEnd) {
		_name = name;
		_summerRate = summerRate;
		_winterRate = winterRate;
		_summerStart = summerStart;
		_summerEnd = summerEnd;
	}
	
	public Zone persist() {
		Registry.add(this);
		return this;
	}

	public static Zone get(String name) {
		return (Zone) Registry.get(name);
	}

	public Date summerEnd() {
		return _summerEnd;
	}

	public Date summerStart() {
		return _summerStart;
	}

	public double winterRate() {
		return _winterRate;
	}

	public double summerRate() {
		return _summerRate;
	}

	public String name() {
		return _name;
	}

	double summerFraction(DisabilitySite site, Date start, Date end) {
		double summerFraction;
		if (start.after(summerEnd()) || end.before(summerStart()))
			summerFraction = 0;
		else if (!start.before(summerStart()) && !start.after(summerEnd()) 	&& !end.before(summerStart()) && !end.after(summerEnd()))
			summerFraction = 1;
		else {
			double summerDays;
			if (start.before(summerStart()) || start.after(summerEnd())) {
				// end is in the summer
				summerDays = site.dayOfYear(end) - site.dayOfYear(summerStart()) + 1;
			} else {
				// start is in summer
				summerDays = site.dayOfYear(summerEnd()) - site.dayOfYear(start) + 1;
			}
			summerFraction = summerDays / (site.dayOfYear(end) - site.dayOfYear(start) + 1);
		}
		return summerFraction;
	}

}