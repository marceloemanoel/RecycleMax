
class DisabilitySite extends Site {
	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		super(zone);
	}

	protected Dollars baseCharge() {
		int fullUsage = usage();
		int usage = Math.min(fullUsage, CAP);

		double summerFraction = zone.summerFraction(lastPeriod());
		
		Dollars result = new Dollars ((usage * zone.summerRate() * summerFraction) + (usage * zone.winterRate() * (1 - summerFraction)));
		result = result.plus(new Dollars (Math.max(fullUsage - usage, 0) * 0.062));
		return result;
	}

	protected Dollars fuelChargeWithTaxes() {
		return fuelCharge().times(TAX_RATE).min(FUEL_TAX_CAP);
	}
}
