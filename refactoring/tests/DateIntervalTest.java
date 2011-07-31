import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DateIntervalTest {

	@Test
	public void startShouldBeFirstThanEnd() throws Exception {
		Date start = new Date();
		Date end = new Date(start.getTime());
		end.setDate(end.getDate() + 1);

		Assert.assertTrue(start.before(end));
		new DateInterval(start, end);
	}

	@Test(expected = DateInterval.WrongInterval.class)
	public void whenStartComesLaterThanEnd_TheIntervalIsWrong() {
		Date start = new Date();
		Date end = new Date(start.getTime());
		end.setDate(end.getDate() - 1);

		new DateInterval(start, end);
	}

	@Test
	public void intervalShouldKnowHowManyDaysItHas() {
		Date start = new Date();
		Date end = new Date();

		start.setDate(start.getDate() - 1);

		DateInterval interval = new DateInterval(start, end);

		Assert.assertEquals(1, interval.howManyDays());
	}

	@Test
	public void intervalShouldKnowIfItContainsADate() {
		Date start = new Date();
		Date end = new Date();
		end.setDate(start.getDate() + 10);

		Date mid = new Date();
		mid.setDate(start.getDate() + 5);

		DateInterval interval = new DateInterval(start, end);
		Assert.assertTrue(interval.contains(mid));
	}

	@Test
	public void shouldKnowItDoesNotContainsADate() {
		Date start = new Date();
		Date end = new Date();
		end.setDate(start.getDate() + 10);

		Date later = new Date();
		later.setDate(start.getDate() + 15);

		DateInterval interval = new DateInterval(start, end);
		Assert.assertFalse(interval.contains(later));
	}

	@Test
	public void shouldKnowIfItContainsAnotherInterval() {
		Date start = new Date();

		Date end = new Date();
		end.setDate(start.getDate() + 30);

		Date start2 = new Date();
		start2.setDate(start.getDate() + 2);

		Date end2 = new Date();
		end2.setDate(start.getDate() + 15);

		DateInterval biggerInterval = new DateInterval(start, end);
		DateInterval interval2 = new DateInterval(start2, end2);

		Assert.assertTrue(biggerInterval.contains(interval2));
	}

	@Test
	public void shouldKnowWhenItDoesNotContainAnotherInterval() {
		Date start = new Date();

		Date end = new Date();
		end.setDate(start.getDate() + 30);

		Date start2 = new Date();
		start2.setDate(start.getDate() + 40);

		Date end2 = new Date();
		end2.setDate(start.getDate() + 45);

		DateInterval biggerInterval = new DateInterval(start, end);
		DateInterval interval2 = new DateInterval(start2, end2);

		Assert.assertFalse(biggerInterval.contains(interval2));
	}

}
