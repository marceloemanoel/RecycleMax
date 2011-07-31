
class ResidentialSite extends Site {

	ResidentialSite(Zone zone) {
		super(zone);
	}

	protected Dollars baseCharge() {
		if(readings.isEmpty()){
			return new Dollars(0);
		}
		
		double summerFraction = zone.summerFraction(lastPeriod());
		
		Dollars result = new Dollars ((usage() * zone.summerRate() * summerFraction) + (usage() * zone.winterRate() * (1 - summerFraction)));
		return result;
	}
}