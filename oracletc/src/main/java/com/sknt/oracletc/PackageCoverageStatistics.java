/**
 * 
 */
package com.sknt.oracletc;

import java.util.List;


/**
 * Contains the test coverage statistics for a single PL/SQL package
 * 
 * @author thigh
 */
public class PackageCoverageStatistics {
	private static final String TOTAL_SUM_NAME = "TOTAL";
	private String htmlFileName;
	private String packageName;
	private int numProcedures;
	private int linesCovered;
	private int totalLines;
	
	/**
	 * Creates a new stats object for the package indicated.
	 * @param fileName The name of the HTML file containing the full coverage report for this package
	 * @param packageName The name of the PL/SQL package contained in the report
	 * @param numProcedures The number of procedures defined in the package
	 * @param linesCovered The number of executable lines in the package which are covered by unit tests
	 * @param totalLines The total number of executable lines in the package
	 */
	public PackageCoverageStatistics(String fileName, String packageName, int numProcedures, int linesCovered, int totalLines) {
		this.htmlFileName = fileName;
		this.packageName = packageName;
		this.numProcedures = numProcedures;
		this.linesCovered = linesCovered;
		this.totalLines = totalLines;
	}
	
	/**
	 * Calculates the percentage (0-100) of lines covered by unit tests based on the number of lines covered
	 * versus the total number of lines. Rounds to the nearest integer.
	 * 
	 * If total number of lines is 0, returns 0 (the assumption then is that this package wasn't tested at
	 * all, causing it to be ignored in the Oracle profiling)
	 * 
	 * @return
	 */
	public int getPercentCovered() {
		if (totalLines == 0) {
			return 0;
		}
		return Math.round(((float)linesCovered  * 100) / (float)totalLines);
	}
	
	/**
	 * Calculates the ratio of lines covered by unit tests based on the number of lines covered
	 * versus the total number of lines. 
	 * 
	 * If total number of lines is 0, returns 0 (the assumption then is that this package wasn't tested at
	 * all, causing it to be ignored in the Oracle profiling)
	 * 
	 * @return
	 */
	public float getRatioCovered() {
		if (totalLines == 0) {
			return 0;
		}
		return (float)linesCovered / (float)totalLines;
	}
	
	/**
	 * Adds up the lines covered and total and creates a new stats object called "TOTAL"
	 * with the sum of values.
	 * 
	 * Any stat objects with negative values will be ignored for the sake of computation
	 * 
	 * @param stats
	 * @return
	 */
	public static PackageCoverageStatistics calculateTotal(List<PackageCoverageStatistics> stats) {
		if (stats == null) {
			throw new IllegalArgumentException("List of stats can't be null");
		}
		
		int numProcedures = 0;
		int totalCovered = 0;
		int totalLines = 0;
		
		for (PackageCoverageStatistics stat : stats) {
			if (stat.getLinesCovered() >= 0 
					&& stat.getTotalLines() >= 0
					&& stat.getNumProcedures() >= 0) {
				numProcedures += stat.getNumProcedures();
				totalCovered += stat.getLinesCovered();
				totalLines += stat.getTotalLines();
			}
		}
		
		PackageCoverageStatistics newStat = new PackageCoverageStatistics(null, TOTAL_SUM_NAME, numProcedures, totalCovered, totalLines);
		return newStat;
	}

	public String getHtmlFileName() {
		return htmlFileName;
	}

	public String getPackageName() {
		return packageName;
	}

	public int getNumProcedures() {
		return numProcedures;
	}

	public int getLinesCovered() {
		return linesCovered;
	}

	public int getTotalLines() {
		return totalLines;
	}
	
}
