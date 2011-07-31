public class LifelineSite extends Site {

	public Dollars charge() {
		return charge(lastReading().amount() - previousReading().amount());
	}
	
	private Dollars charge(int usage) {
		
		double base = Math.min(usage, 100) * 0.03;
		
		if (usage > 100) {
			base += (Math.min(usage, 200) - 100) * 0.05;
		}

		if (usage > 200) {
			base += (usage - 200) * 0.07;
		}
		
		Dollars result = new Dollars (base);
		Dollars tax = new Dollars (result.minus(new Dollars(8)).max(new Dollars (0)).times(TAX_RATE));
		result = result.plus(tax);
		Dollars fuelCharge = new Dollars (usage * 0.0175);
		result = result.plus (fuelCharge);
		return result.plus (new Dollars (fuelCharge.times(TAX_RATE)));
	}
}