package com.thesmartpuzzle.erdos.model;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class DiscoveryProperty {
	
	private String uri;
	private boolean isForward;
	
	public DiscoveryProperty(String uri, boolean isForward) {
		super();
		this.setUri(uri);
		this.setForward(isForward);
	}

	public boolean isForward() {
		return isForward;
	}

	public void setForward(boolean isForward) {
		this.isForward = isForward;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
