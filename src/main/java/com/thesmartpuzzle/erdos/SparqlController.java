package com.thesmartpuzzle.erdos;

import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.thesmartpuzzle.erdos.discovery.Discovery;
import com.thesmartpuzzle.erdos.model.DiscoveryProperty;
import com.thesmartpuzzle.erdos.model.DiscoveryTerm;

/**
 * @author Tommaso Soru <tsoru@informatik.uni-leipzig.de>
 *
 */
public class SparqlController {
	
	private static final Logger LOGGER = Logger.getLogger(SparqlController.class); 
	
	public static Boolean ask(Discovery disc, int number) {
		
		if(number <= 0) {
			LOGGER.fatal("Input number cannot be <= 0.");
			return null;
		}
		
		DiscoveryProperty[] props = disc.getProperties();
		if(props.length == 0) {
			LOGGER.fatal("Missing properties.");
			return null;
		}
		
		String part1 = "", part2 = "";
		for(DiscoveryProperty prop : props) {
			if(prop.isForward()) {
				part1 += prop.getUri() + "|";
				part2 += "^" + prop.getUri() + "|";
			} else {
				part1 += "^" + prop.getUri() + "|";
				part2 += prop.getUri() + "|";
			}
		}
		part1 = part1.substring(0, part1.length() - 1);
		part2 = part2.substring(0, part2.length() - 1);
		
		String pLeft = part2, pRight = part1;
		if(disc.isSubject()) {
			pLeft = part1;
			pRight = part2;
		}
		
		String steps = "";
		for(int i=0; i<number; i++)
			steps += "/(("+pLeft+")/("+pRight+"))";
		steps = steps.substring(1);
		
		String label = disc.getLabelProperty();
		
		String sparqlQueryString = "";
		DiscoveryTerm[] terms = disc.getTerms();
		if(disc.useAsk()) {
			if(terms[0].isURI())
				sparqlQueryString += "ASK { ?t1 ";
			else
				sparqlQueryString += "ASK { ?t1 ^" + label + "/";
			if(terms[1].isURI())
				sparqlQueryString += steps + " ?t2 }";
			else
				sparqlQueryString += steps + "/" + label + " ?t2 }";
		} else {
			if(terms[0].isURI())
				sparqlQueryString += "SELECT DISTINCT ?s WHERE { ?t1 ";
			else
				sparqlQueryString += "SELECT DISTINCT ?s WHERE { ?t1 ^" + label + "/";
			if(terms[1].isURI())
				sparqlQueryString += steps + " ?s }";
			else
				sparqlQueryString += steps + "/" + label + " ?s }";
		}
		
		String term1 = terms[0].getTerm(), term2 = terms[1].getTerm();
		LOGGER.info("Query:\n" + sparqlQueryString);
		LOGGER.info("where t1="+term1+", t2="+term2);
		
		ParameterizedSparqlString pss = new ParameterizedSparqlString();
		pss.setCommandText(sparqlQueryString);
		
		Map<String, String> prefixes = disc.getPrefixes();
		for(String key : prefixes.keySet())
			pss.setNsPrefix(key, prefixes.get(key));
		
		for(int i=0; i<terms.length; i++)
			if(terms[i].isURI())
				pss.setIri("t"+(i+1), terms[i].getTerm());
			else
				pss.setLiteral("t"+(i+1), terms[i].getTerm());
		
		Query query = pss.asQuery();
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				disc.getEndpoint(), query);
		
		boolean result = false;
		
		if(disc.useAsk()) {
			result = qexec.execAsk();
		} else {
			
			ResultSet rs = qexec.execSelect();
			while(rs.hasNext()) {
				String res;
				if(terms[1].isURI())
					res = rs.next().get("s").asResource().getURI();
				else
					res = rs.next().get("s").asLiteral().getString();
				LOGGER.debug("Is "+res+" equal to "+term2+"?");
				if(res.equals(term2)) {
					result = true;
					break;
				}
			}
			
		}
		
		LOGGER.info(query.toString());
		LOGGER.info(result);
		qexec.close();
		return result;
		
	}

}
