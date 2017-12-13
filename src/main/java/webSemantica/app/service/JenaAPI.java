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


public class JenaAPI {
	
	
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
		    String info = getInfoParking(model, qs.get( "parking" ).toString());
		    parking.add(qs.get( "name" ).toString());
		    parking.add(qs.get( "lat" ).toString());
		    parking.add(qs.get( "lon" ).toString());
		    parking.add(info);
		    result.add(parking);
		}
		
		return result;
	}
	
	public static String getInfoParking(Model model, String uri){
		String result = "";
	    final String query = 
        "prefix ont: <http://www.semanticweb.org/Group04/ontology#>\n" +
        "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "prefix owl: <http://www.w3.org/2002/07/owl#>\n" +
        "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
        "\n" +
        "select ?parking ?name ?type ?accesible where {\n" +
        "  <"+uri+"> ?parking ont:Parking;\n"+
        "             ont:hasType ?type ;\n" +
        "             ont:isAccesible ?accesible;\n" +
        
        "}"; 
    
	    final QueryExecution exec = QueryExecutionFactory.create( query, model );
		final ResultSet rs = exec.execSelect();
		while ( rs.hasNext() ) {
		    final QuerySolution qs = rs.next();
		    String accesible = "";
		    if(qs.get("accesible").toString() == "0"){
		    	accesible = "Accesible: No";
		    } else {
		    	accesible = "Accesible: Si";
		    }
		    result = qs.get( "type" ).toString() +
		    		".\n" + accesible;		            
		}
		return result;
	}

}
