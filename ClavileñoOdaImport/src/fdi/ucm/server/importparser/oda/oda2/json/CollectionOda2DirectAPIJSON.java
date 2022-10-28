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
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Datos;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Metadatos;
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
		
		 
		
		ResourcveData=new Grammar_ObjetoVirtualDirectJSON(oda2,
				LocalPadre,OdaJSONCollection);
		((Grammar_ObjetoVirtualDirectJSON)ResourcveData).ProcessAttributes();
		((Grammar_ObjetoVirtualDirectJSON)ResourcveData).ProcessInstances();
		oda2.getMetamodelGrammar().add(ResourcveData.getAtributoMeta());
	}
	
	@Override
	protected void processDatos() {
		ElementType_Datos ResourcveData2=new ElementType_Datos_JSON(ResourcveData.getAtributoMeta(),
				LocalPadre,OdaJSONCollection);
		ResourcveData2.ProcessAttributes();
		ResourcveData2.ProcessInstances();
		ResourcveData.getAtributoMeta().getSons().add(ResourcveData2.getAtributoMeta());

	}
	
	@Override
	protected void processMetadatos() {
		ElementType_Metadatos ResourcveData2=new ElementType_Metadatos_JSON(ResourcveData.getAtributoMeta(),
				LocalPadre,OdaJSONCollection);
		ResourcveData2.ProcessAttributes();
		ResourcveData2.ProcessInstances();
		ResourcveData.getAtributoMeta().getSons().add(ResourcveData2.getAtributoMeta());
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
