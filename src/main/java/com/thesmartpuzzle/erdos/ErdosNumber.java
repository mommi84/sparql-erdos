package com.thesmartpuzzle.erdos;

import com.thesmartpuzzle.erdos.discovery.ErdosNumberDiscovery;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class ErdosNumber {

	public static void main(String[] args) {
		
		new ErdosNumberDiscovery("Edsger W. Dijkstra", 
				"http://dblp.l3s.de/d2r/resource/authors/Paul_Erdös").run();
		
	}
}
