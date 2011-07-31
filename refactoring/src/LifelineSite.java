public class LifelineSite extends Site {

	public LifelineSite() {
		super(null);
	}

	protected Dollars baseTaxes() {
		return baseCharge().minus(new Dollars(8)).max(new Dollars (0)).times(TAX_RATE);
	}

	protected Dollars baseCharge() {
		int lastUsage = usage();
		double base = Math.min(lastUsage, 100) * 0.03;
		
		if (lastUsage > 100) {
			base += (Math.min(lastUsage, 200) - 100) * 0.05;
		}

		if (lastUsage > 200) {
			base += (lastUsage - 200) * 0.07;
		}
		
		return new Dollars (base);
	}
}