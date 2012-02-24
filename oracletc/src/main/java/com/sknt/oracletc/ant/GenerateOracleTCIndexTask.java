/**
 * 
 */
package com.sknt.oracletc.ant;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.sknt.oracletc.IndexHtmlFileGenerator;

/**
 * Ant task for generating the Oracle Test Coverage index file
 * 
 * @author thigh
 *
 */
public class GenerateOracleTCIndexTask extends Task {
	private String srcdir;
	private String destfile;
	private String bestfile;
	private String groups;
	private boolean considernumprocs = true;
	
	/**
	 * Generates the index file using a {@link IndexHtmlFileGenerator}
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		List<String> groupsList = Arrays.asList(groups.split(",\\s*"));
		log("Using group list:");
		for (String group : groupsList) {
			log(String.format("\t\"%s\"", group));
		}

		IndexHtmlFileGenerator generator = 
			new IndexHtmlFileGenerator(srcdir, destfile, bestfile, groupsList, considernumprocs);

		try {
			if (bestfile == null) {
				log(String.format("Generating file %s from directory %s", destfile, srcdir));
			} else {
				log(String.format("Generating files %s and %s from directory %s", destfile, bestfile, srcdir));
			}
			generator.generateIndexFiles();
			log("Index file(s) generated");
		} catch (IOException e) {
			throw new BuildException("Failed to generate index file", e);
		}
	}

	public String getSrcdir() {
		return srcdir;
	}

	public void setSrcdir(String srcdir) {
		this.srcdir = srcdir;
	}

	public String getDestfile() {
		return destfile;
	}
	
	public void setDestfile(String destfile) {
		this.destfile = destfile;
	}

	/**
	 * @return the bestfile
	 */
	public String getBestfile() {
		return bestfile;
	}

	/**
	 * @param bestfile the bestfile to set
	 */
	public void setBestfile(String bestfile) {
		this.bestfile = bestfile;
	}

	/**
	 * @return the groups
	 */
	public String getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(String groups) {
		this.groups = groups;
	}

	/**
	 * @return the considernumprocs
	 */
	public boolean isConsidernumprocs() {
		return considernumprocs;
	}

	/**
	 * @param considernumprocs the considernumprocs to set
	 */
	public void setConsidernumprocs(boolean considernumprocs) {
		this.considernumprocs = considernumprocs;
	}
	
	
}
