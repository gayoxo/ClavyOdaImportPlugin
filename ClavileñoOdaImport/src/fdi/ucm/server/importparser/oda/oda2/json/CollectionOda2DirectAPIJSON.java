package fdi.ucm.server.importparser.oda.oda2.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.CollectionOda2Direct;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.Grammar_ObjetoVirtualDirect;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;

public class CollectionOda2DirectAPIJSON extends CollectionOda2Direct {
	
	
	private JSONObject OdaJSONCollection;


	public CollectionOda2DirectAPIJSON(LoadCollectionOda loadCollectionOda2JSON) {
		super(loadCollectionOda2JSON);
	}

	@Override
	protected void procesOV() {
		
		 JSONArray virtual_object = (JSONArray) OdaJSONCollection.get("virtual_object");
		
		ResourcveData=new Grammar_ObjetoVirtualDirectJSON(oda2,
				LocalPadre,virtual_object);
		((Grammar_ObjetoVirtualDirectJSON)ResourcveData).ProcessAttributes();
		((Grammar_ObjetoVirtualDirectJSON)ResourcveData).ProcessInstances();
		oda2.getMetamodelGrammar().add(ResourcveData.getAtributoMeta());
	}
	
	@Override
	protected void processDatos() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void processMetadatos() {
		// TODO Auto-generated method stub

	}

	public void setFileProcess(String filePath) {	
		
		 JSONParser jsonParser = new JSONParser();
		 
		 try (FileReader reader = new FileReader(filePath))
	        {
	            //Read JSON file
	            Object obj = jsonParser.parse(reader);
	 
	             OdaJSONCollection = (JSONObject) obj;

	 
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		
	}

}
