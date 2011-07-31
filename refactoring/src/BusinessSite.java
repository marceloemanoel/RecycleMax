class BusinessSite extends Site{
	

	private static final double START_RATE = 0.09;
	static final double END_RATE = 0.05;
	static final int END_AMOUNT = 1000;

	public BusinessSite() {
		super(null);
	}
 
	protected Dollars baseTaxes() {
		Dollars taxable = baseCharge().plus(fuelCharge());
		
		Dollars base = (taxable.min(new Dollars (50)).times(0.07));
		
		if (taxable.isGreaterThan(new Dollars (50))) {
			base = new Dollars (base.plus(taxable.min(new Dollars(75)).minus(new Dollars(50)).times(0.06)));
		}
		
		if (taxable.isGreaterThan(new Dollars (75))) {
			base = new Dollars (base.plus(taxable.minus(new Dollars(75)).times(0.05)));
		}
		
		taxable = taxable.plus(base);
		return taxable;
	}

	protected Dollars baseCharge() {
		double t1 = START_RATE - ((END_RATE * END_AMOUNT) - START_RATE) / (END_AMOUNT - 1);
		double t2 = ((END_RATE * END_AMOUNT) - START_RATE) * Math.min(END_AMOUNT, usage()) / (END_AMOUNT - 1);
		double t3 = Math.max(usage() - END_AMOUNT, 0) * END_RATE;
		
		Dollars result = new Dollars (t1 + t2 + t3);
		return result;
	}

	@Override
	protected Dollars fuelChargeTaxes() {
		return new Dollars(0);
	}
}