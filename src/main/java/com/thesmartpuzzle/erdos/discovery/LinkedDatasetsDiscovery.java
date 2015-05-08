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
public class LinkedDatasetsDiscovery implements Discovery {
	
	private static final Logger LOGGER = Logger.getLogger(LinkedDatasetsDiscovery.class); 
	private String term1, term2;
	
	public LinkedDatasetsDiscovery(String term1, String term2) {
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
		prefixes.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		prefixes.put("llont", "http://www.linklion.org/ontology#");
		return prefixes;
	}

	public DiscoveryTerm[] getTerms() {
		return new DiscoveryTerm[] {
				new DiscoveryTerm(term1, false),
				new DiscoveryTerm(term2, false)
		};
	}

	public String getEndpoint() {
		return "http://www.linklion.org:8890/sparql";
	}

	public DiscoveryProperty[] getProperties() {
		return new DiscoveryProperty[] {
				new DiscoveryProperty("llont:hasSource", true), 
				new DiscoveryProperty("llont:hasTarget", true)
		};
	}

	public boolean isSubject() {
		return false;
	}

	public String getLabelProperty() {
		return "rdfs:label";
	}
	
	public boolean useAsk() {
		// Virtuoso seems not bugged, so the ASK construct can be used.
		return true;
	}


}
