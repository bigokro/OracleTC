/**
 * 
 */
package com.sknt.oracletc;


/**
 * Serves as a Map Entry key for {@link PackageCoverageStatistics} objects
 * @author thigh
 *
 */
public class PackageCoverageStatisticsKey {

	private static final char FILENAME_SEPARATOR = '_';
	
	private String keyName;
	private int numProcedures;

	/**
	 * Creates a new key based on the values provided
	 * 
	 * @param name
	 * @param numProcs
	 */
	public PackageCoverageStatisticsKey(String name, int numProcs) {
		keyName = name;
		numProcedures = numProcs;
	}
	

	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyName == null) ? 0 : keyName.hashCode());
		result = prime * result + numProcedures;
		return result;
	}
	
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PackageCoverageStatisticsKey other = (PackageCoverageStatisticsKey) obj;
		if (keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!keyName.equals(other.keyName))
			return false;
		if (numProcedures != other.numProcedures)
			return false;
		return true;
	}

	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @return the numProcedures
	 */
	public int getNumProcedures() {
		return numProcedures;
	}
	
	

}
