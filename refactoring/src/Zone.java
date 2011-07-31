import java.util.Date;

class Zone {
	private Date summerEnd;
	private Date summerStart;
	private double winterRate;
	private double summerRate;
	private String name;
	
	Zone(String name, double summerRate, double winterRate, Date summerStart, Date summerEnd) {
		this.name = name;
		this.summerRate = summerRate;
		this.winterRate = winterRate;
		this.summerStart = summerStart;
		this.summerEnd = summerEnd;
	}
	
	public Zone persist() {
		Registry.add(this);
		return this;
	}

	public static Zone get(String name) {
		return (Zone) Registry.get(name);
	}

	public Date summerEnd() {
		return summerEnd;
	}

	public Date summerStart() {
		return summerStart;
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

	double summerFraction(Date start, Date end) {
		double summerFraction;
		if (start.after(summerEnd()) || end.before(summerStart()))
			summerFraction = 0;
		else if (!start.before(summerStart()) && !start.after(summerEnd()) 	&& !end.before(summerStart()) && !end.after(summerEnd()))
			summerFraction = 1;
		else {
			double summerDays;
			if (start.before(summerStart()) || start.after(summerEnd())) {
				// end is in the summer
				summerDays = dayOfYear(end) - dayOfYear(summerStart()) + 1;
			} else {
				// start is in summer
				summerDays = dayOfYear(summerEnd()) - dayOfYear(start) + 1;
			}
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		}
		return summerFraction;
	}

	int dayOfYear(Date arg) {
		int result;
		switch (arg.getMonth()) {
		case 0:
			result = 0;
			break;
		case 1:
			result = 31;
			break;
		case 2:
			result = 59;
			break;
		case 3:
			result = 90;
			break;
		case 4:
			result = 120;
			break;
		case 5:
			result = 151;
			break;
		case 6:
			result = 181;
			break;
		case 7:
			result = 212;
			break;
		case 8:
			result = 243;
			break;
		case 9:
			result = 273;
			break;
		case 10:
			result = 304;
			break;
		case 11:
			result = 334;
			break;
		default:
			throw new IllegalArgumentException();
		}
	
		result += arg.getDate();
	
		if (isLeapYear(arg)) {
			result++;
		}
		return result;
	}

	boolean isLeapYear(Date arg) {
		return (arg.getYear() % 4 == 0)	&& ((arg.getYear() % 100 != 0) || ((arg.getYear() + 1900) % 400 == 0));
	}

}