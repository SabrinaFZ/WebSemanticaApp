package webSemantica.app.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;


public class JenaAPI {
	
	public static String schema = "http://schema.org/";
	public static String ont = "http://www.semanticweb.org/Group04/ontology#";
	public static String rdf = "http://www.semanticweb.org/Group04/ontology#";
	public static String owl = "http://www.w3.org/2002/07/owl#";
	public static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	
	
	public static Model read(){
		String filename = "aparcamientos-publicos-with-links.ttl";
		Model model = ModelFactory.createDefaultModel();
		FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.read(in,null,"TTL");	
		return model;
	}
	
	public static ArrayList<ArrayList<String>> getParkingByDistrict(Model model, String uri){
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
	    final String query = 
        "prefix ont: <http://www.semanticweb.org/Group04/ontology#>\n" +
        "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
        "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
        "\n" +
        "select ?parking ?name ?lat ?lon where {\n" +
        "  ?parking a ont:Parking;\n"+
        "            ont:hasLatitude ?lat ;\n" +
        "            ont:hasLongitude ?lon ;\n" +
        "            ont:hasName ?name ;\n" +  
        "            ont:isInDistrict "+'"'+uri+'"'+"^^<http://es.dbpedia.org/page/Barrios_de_Madrid>;\n" +  
        "}"; 
    
	    final QueryExecution exec = QueryExecutionFactory.create( query, model );
		final ResultSet rs = exec.execSelect();
		while ( rs.hasNext() ) {
			ArrayList<String> parking = new ArrayList<String>();
		    final QuerySolution qs = rs.next();
		    String info = getInfoParking(model, qs.get( "name" ).toString());
		    parking.add(qs.get( "name" ).toString());
		    parking.add(qs.get( "lat" ).toString());
		    parking.add(qs.get( "lon" ).toString());
		    parking.add(info);
		    result.add(parking);
		}
		
		return result;
	}
	
	public static String getInfoParking(Model model, String parkingName){
		String result = "";
		
		Property isAccesible = model.createProperty(ont+"isAccesible");
		Property hasName = model.createProperty(ont+"hasName");
		Property hasType = model.createProperty(ont+"hasType");
		
		StmtIterator stIter = model.listStatements(null, hasName, parkingName);
		
		while (stIter.hasNext()) {
			Statement st = stIter.next();
			Resource subj = st.getSubject();
			
			Statement description = subj.getProperty(hasType);
			Statement accesibility = subj.getProperty(isAccesible);
			
			String acc = "";
			
			if(accesibility.getObject().toString().equals("0")){
				acc = "Accesible: No";
			} else {
				acc = "Accesible: Si";
			}
			result = description.getObject().toString() + ".\n" + acc;
		}
		return result;
	}

}
