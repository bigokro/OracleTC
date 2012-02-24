package com.sknt.oracletc;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class PackageCoverageStatisticsTest extends TestCase {

	public void testGetPercentCoveredZeroTotalLines() {
		PackageCoverageStatistics stats = createStats(0, 0);
		assertEquals("Zero lines total should report zero coverage", 0, stats.getPercentCovered());
	}

	public void testGetPercentCoveredZeroCovered() {
		PackageCoverageStatistics stats = createStats(0, 20);
		assertEquals("No coverage", 0, stats.getPercentCovered());
	}

	public void testGetPercentCoveredFullCoverage() {
		PackageCoverageStatistics stats = createStats(10, 10);
		assertEquals(100, stats.getPercentCovered());
	}

	public void testGetPercentCoveredHalf() {
		PackageCoverageStatistics stats = createStats(10, 20);
		assertEquals(50, stats.getPercentCovered());
	}

	public void testGetPercentCoveredFractionRoundDown() {
		PackageCoverageStatistics stats = createStats(103, 1000);
		assertEquals(10, stats.getPercentCovered());
	}

	public void testGetPercentCoveredFractionRoundUp() {
		PackageCoverageStatistics stats = createStats(108, 1000);
		assertEquals(11, stats.getPercentCovered());
	}

	public void testGetPercentCoveredFractionHalfRoundUp() {
		PackageCoverageStatistics stats = createStats(105, 1000);
		assertEquals(11, stats.getPercentCovered());
	}

	/*
	 * Test method getRatioCovered()
	 */
	public void testGetRatioCoveredZeroTotalLines() {
		PackageCoverageStatistics stats = createStats(0, 0);
		assertEquals("Zero lines total should report zero coverage", 0.0, stats.getRatioCovered(), 0.01);
	}

	public void testGetRatioCoveredZeroCovered() {
		PackageCoverageStatistics stats = createStats(0, 20);
		assertEquals("No coverage", 0.0, stats.getRatioCovered(), 0.01);
	}

	public void testGetRatioCoveredFullCoverage() {
		PackageCoverageStatistics stats = createStats(10, 10);
		assertEquals(1.0, stats.getRatioCovered(), 0.01);
	}

	public void testGetRatioCoveredHalf() {
		PackageCoverageStatistics stats = createStats(10, 20);
		assertEquals(0.5, stats.getRatioCovered(), 0.01);
	}

	public void testGetRatioCoveredThreeQuarters() {
		PackageCoverageStatistics stats = createStats(15, 20);
		assertEquals(0.75, stats.getRatioCovered(), 0.001);
	}

	/*
	 * Test static method calculateTotal()
	 */
	public void testCalculateTotalEmptyList() {
		List<PackageCoverageStatistics> stats = new ArrayList<PackageCoverageStatistics>();
		PackageCoverageStatistics stat = PackageCoverageStatistics.calculateTotal(stats);
		assertEquals(0, stat.getLinesCovered());
		assertEquals(0, stat.getTotalLines());
		assertNull(stat.getHtmlFileName());
		assertEquals("TOTAL", stat.getPackageName());
	}

	public void testCalculateTotalOneStat() {
		List<PackageCoverageStatistics> stats = new ArrayList<PackageCoverageStatistics>();
		stats.add(createStats(10, 10));
		PackageCoverageStatistics stat = PackageCoverageStatistics.calculateTotal(stats);
		assertEquals(1, stat.getNumProcedures());
		assertEquals(10, stat.getLinesCovered());
		assertEquals(10, stat.getTotalLines());
	}

	public void testCalculateTotalManyStats() {
		List<PackageCoverageStatistics> stats = new ArrayList<PackageCoverageStatistics>();
		stats.add(createStats(0, 10));
		stats.add(createStats(2, 10));
		stats.add(createStats(8, 10));
		stats.add(createStats(10, 10));
		PackageCoverageStatistics stat = PackageCoverageStatistics.calculateTotal(stats);
		assertEquals(4, stat.getNumProcedures());
		assertEquals(20, stat.getLinesCovered());
		assertEquals(40, stat.getTotalLines());
	}

	public void testCalculateTotalNullList() {
		try {
			PackageCoverageStatistics stat = PackageCoverageStatistics.calculateTotal(null);
			fail("Null List should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			//OK
		}
	}

	public void testCalculateTotalBadStats() {
		List<PackageCoverageStatistics> stats = new ArrayList<PackageCoverageStatistics>();
		stats.add(createStats(0, 10));
		stats.add(createStats(5, 10));
		stats.add(createStats(-1, -1)); // Should be ignored
		stats.add(createStats(-1, 10)); // Should be ignored
		stats.add(createStats(10, -1)); // Should be ignored
		stats.add(createStats(10, 10));
		stats.add(new PackageCoverageStatistics("", "", -1, 10, 10)); // Should be ignored
		PackageCoverageStatistics stat = PackageCoverageStatistics.calculateTotal(stats);
		assertEquals(3, stat.getNumProcedures());
		assertEquals(15, stat.getLinesCovered());
		assertEquals(30, stat.getTotalLines());
	}

	
	/**
	 * Creates a stats object with dummy file and package
	 */
	private PackageCoverageStatistics createStats(int covered, int total) {
		return new PackageCoverageStatistics("yoMama.html", "PKG_YO_MAMA", 1, covered, total);
	}

}
