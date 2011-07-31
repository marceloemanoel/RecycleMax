import java.util.Date;


public class DateInterval {

	@SuppressWarnings("serial")
	public static class WrongInterval extends RuntimeException {
	}
	
	private Date start;
	private Date end;

	public DateInterval(Date start, Date end) {
		if(start.after(end)){
			throw new WrongInterval();
		}
		
		this.start = start;
		this.end = end;
	}

	public int howManyDays() {
		return dayOfYear(start) - dayOfYear(start) + 1;
	}

	public boolean contains(Date date) {
		return start.before(date) && end.after(date);
	}

	public boolean contains(DateInterval other) {
		return other.getStart().after(getStart()) && other.getEnd().before(getEnd());
	}
	
	public Date getStart() {
		return start;
	}
	
	public Date getEnd() {
		return end;
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
		return (arg.getYear() % 4 == 0)	&& ((arg.getYear() % 100 != 0) || ((arg.getYear() + 1900) % 400 == 0));
	}

	public boolean outside(DateInterval lastPeriod) {
		return lastPeriod.getStart().after(getEnd()) || lastPeriod.getEnd().before(getStart());
	}
}
