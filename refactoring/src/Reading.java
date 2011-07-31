import java.util.Date;

class Reading {
	
	private Date date;
	private int amount;

	public Reading(int amount, Date date) {
		this.amount = amount;
		this.date = date;
	}

	public Date date() {
		return date;
	}

	public int amount() {
		return amount;
	}

}