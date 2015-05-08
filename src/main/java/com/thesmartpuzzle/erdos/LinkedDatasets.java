package com.thesmartpuzzle.erdos;

import com.thesmartpuzzle.erdos.discovery.LinkedDatasetsDiscovery;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class LinkedDatasets {

	public static void main(String[] args) {
		
		new LinkedDatasetsDiscovery("dbpedia.org", "opus.bath.ac.uk").run();
				
	}
}
