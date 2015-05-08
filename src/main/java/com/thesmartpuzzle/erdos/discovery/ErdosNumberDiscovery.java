package com.thesmartpuzzle.erdos.discovery;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.thesmartpuzzle.erdos.SparqlController;
import com.thesmartpuzzle.erdos.model.DiscoveryProperty;
import com.thesmartpuzzle.erdos.model.DiscoveryTerm;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class ErdosNumberDiscovery implements Discovery {
	
	private static final Logger LOGGER = Logger.getLogger(ErdosNumberDiscovery.class); 
	private String term1, term2;
	
	public ErdosNumberDiscovery(String term1, String term2) {
		super();
		this.term1 = term1;
		this.term2 = term2;
	}

	public int run() {
		
		Boolean result = false;
		for(int iter=1; iter<=MAX_ITER && !result; iter++) {
			result = SparqlController.ask(this, iter);
			// invalid parameter
			if(result == null)
				return -1;
			if(result) {
				LOGGER.info("Path found! "+iter);
				return iter;
			}
		}
		
		// no path within Discovery.MAX_ITER
		return -1;
						
	}

	public Map<String, String> getPrefixes() {
		HashMap<String, String> prefixes = new HashMap<String, String>();
		prefixes.put("foaf", "http://xmlns.com/foaf/0.1/");
		return prefixes;
	}

	public DiscoveryTerm[] getTerms() {
		return new DiscoveryTerm[] {
				new DiscoveryTerm(term1, false),
				new DiscoveryTerm(term2, true)
		};
	}

	public String getEndpoint() {
		return "http://dblp.l3s.de/d2r/sparql";
	}

	public DiscoveryProperty[] getProperties() {
		return new DiscoveryProperty[] {
				new DiscoveryProperty("foaf:maker", false)
		};
	}

	public boolean isSubject() {
		return true;
	}

	public String getLabelProperty() {
		return "foaf:name";
	}

	public boolean useAsk() {
		// L3S service seems bugged, so the ASK is avoided in favor of SELECT + lookup.
		return false;
	}

}
