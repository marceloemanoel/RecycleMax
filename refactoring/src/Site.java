
public class Site {

	protected static final double TAX_RATE = 0.05;
	protected Reading[] readings = new Reading[1000];
	protected Zone zone;

	public Site() {

	}

	public Site(Zone zone) {
		this.zone = zone;
	}

}
