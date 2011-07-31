import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResidentialTester {
	ResidentialSite site;

	@Before
	public void setUp() {
		new Zone("A", 0.06, 0.07, new Date("15 May 1997"), new Date("10 Sep 1997")).persist();
		new Zone("B", 0.07, 0.06, new Date("5 Jun 1997"), new Date("31 Aug 1997")).persist();
		new Zone("C", 0.065, 0.065, new Date("5 Jun 1997"), new Date("31 Aug 1997")).persist();
		site = new ResidentialSite((Zone) Registry.get("A"));
	}

	@Test
	public void testZero() {
		site.addReading(new Reading(10, new Date("1 Jan 1997")));
		site.addReading(new Reading(10, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(0), site.charge());
	}

	@Test
	public void test100() {
		site.addReading(new Reading(10, new Date("1 Jan 1997")));
		site.addReading(new Reading(110, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(4.84), site.charge());
	}

	@Test
	public void test99() {
		site.addReading(new Reading(100, new Date("1 Jan 1997")));
		site.addReading(new Reading(199, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(4.79), site.charge());
	}

	@Test
	public void test101() {
		site.addReading(new Reading(1000, new Date("1 Jan 1997")));
		site.addReading(new Reading(1101, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(4.91), site.charge());
	}

	@Test
	public void test199() {
		site.addReading(new Reading(10000, new Date("1 Jan 1997")));
		site.addReading(new Reading(10199, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(11.6), site.charge());
	}

	@Test
	public void test200() {
		site.addReading(new Reading(0, new Date("1 Jan 1997")));
		site.addReading(new Reading(200, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(11.68), site.charge());
	}

	@Test
	public void test201() {
		site.addReading(new Reading(50, new Date("1 Jan 1997")));
		site.addReading(new Reading(251, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(11.77), site.charge());
	}

	@Test
	public void testMax() {
		site.addReading(new Reading(0, new Date("1 Jan 1997")));
		site.addReading(new Reading(Integer.MAX_VALUE, new Date("1 Feb 1997")));
		Assert.assertEquals(new Dollars(1.9730005336E8), site.charge());
	}

	@Test
	public void testNoReadings() {
		try {
			site.charge();
			Assert.fail();
		} catch (NullPointerException e) {
		}
	}
}