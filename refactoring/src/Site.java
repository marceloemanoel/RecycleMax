
public class Site {

	protected Reading[] _readings = new Reading[1000];
	protected Zone _zone;
	
	public Site(){
		
	}
	
	public Site(Zone zone) {
		this._zone = zone;
	}

}
