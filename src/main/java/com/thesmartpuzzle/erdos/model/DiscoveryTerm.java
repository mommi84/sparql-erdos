package com.thesmartpuzzle.erdos.model;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class DiscoveryTerm {
	
	private String term;
	private boolean isURI;
	
	public DiscoveryTerm(String term, boolean isURI) {
		super();
		this.term = term;
		this.isURI = isURI;
	}

	public String getTerm() {
		return term;
	}

	public boolean isURI() {
		return isURI;
	}

}
