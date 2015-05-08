package com.thesmartpuzzle.erdos.discovery;

import java.util.Map;

import com.thesmartpuzzle.erdos.model.DiscoveryProperty;
import com.thesmartpuzzle.erdos.model.DiscoveryTerm;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public interface Discovery {
	
	public final int MAX_ITER = 5;

	public int run();
	
	public Map<String, String> getPrefixes();

	public DiscoveryTerm[] getTerms();

	public String getEndpoint();

	public DiscoveryProperty[] getProperties();

	/**
	 * Whether the resource argument of the discovery is subject of the declared properties.
	 * 
	 * @return
	 */
	public boolean isSubject();

	public String getLabelProperty();
	
	public boolean useAsk();
		
}
