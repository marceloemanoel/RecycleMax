import java.util.Date;

class Zone {

	private DateInterval summer;
	private double winterRate;
	private double summerRate;
	private String name;

	Zone(String name, double summerRate, double winterRate, Date summerStart,
			Date summerEnd) {
		this.name = name;
		this.summerRate = summerRate;
		this.winterRate = winterRate;
		this.summer = new DateInterval(summerStart, summerEnd);
	}

	public Zone persist() {
		Registry.add(this);
		return this;
	}

	public static Zone get(String name) {
		return (Zone) Registry.get(name);
	}

	public double winterRate() {
		return winterRate;
	}

	public double summerRate() {
		return summerRate;
	}

	public String name() {
		return name;
	}

	double summerFraction(DateInterval lastPeriod) {
		double summerFraction;
		if (summer.outside(lastPeriod))
			summerFraction = 0;
		else if (summer.contains(lastPeriod))
			summerFraction = 1;
		else { 
			double summerDays = summerDays(lastPeriod);
			summerFraction = summerDays	/ (dayOfYear(lastPeriod.getEnd()) - dayOfYear(lastPeriod.getStart()) + 1);
		}
		return summerFraction;
	}

	private double summerDays(DateInterval lastPeriod) {
		// part in summer part in winter
		double summerDays;
		if (lastPeriod.getStart().before(summer.getStart()) || lastPeriod.getStart().after(summer.getEnd())) {
			// end is in the summer
			summerDays = dayOfYear(lastPeriod.getEnd()) - dayOfYear(summer.getStart()) + 1;
		} 
		else {
			// start is in summer
			summerDays = dayOfYear(summer.getEnd()) - dayOfYear(lastPeriod.getStart()) + 1;
		}
		return summerDays;
	}

	int dayOfYear(Date arg) {
		int dayOfTheYear = 0;
		switch (arg.getMonth()) {
		case 1:
			dayOfTheYear = 31;
			break;
		case 2:
			dayOfTheYear = 59;
			break;
		case 3:
			dayOfTheYear = 90;
			break;
		case 4:
			dayOfTheYear = 120;
			break;
		case 5:
			dayOfTheYear = 151;
			break;
		case 6:
			dayOfTheYear = 181;
			break;
		case 7:
			dayOfTheYear = 212;
			break;
		case 8:
			dayOfTheYear = 243;
			break;
		case 9:
			dayOfTheYear = 273;
			break;
		case 10:
			dayOfTheYear = 304;
			break;
		case 11:
			dayOfTheYear = 334;
			break;
		}

		dayOfTheYear += arg.getDate();

		if (isLeapYear(arg)) {
			dayOfTheYear++;
		}
		return dayOfTheYear;
	}

	boolean isLeapYear(Date arg) {
		return (arg.getYear() % 4 == 0)
				&& ((arg.getYear() % 100 != 0) || ((arg.getYear() + 1900) % 400 == 0));
	}

}