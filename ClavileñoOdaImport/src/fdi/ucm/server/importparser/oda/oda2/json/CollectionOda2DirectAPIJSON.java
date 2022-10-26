package fdi.ucm.server.importparser.oda.oda2.json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;

public class CollectionOda2DirectAPIJSON extends CollectionOda {
	
	private static final String COLECCION_OBTENIDA_A_PARTIR_DE_ODA = "Coleccion obtenida a partir de ODA en : ";
	private static final String COLECCION_ODA = "Coleccion ODA";
	private  CompleteCollection oda2;
	private HashMap<Integer, CompleteDocuments> ObjetoVirtual;
	private String FilePath;


	public CollectionOda2DirectAPIJSON(LoadCollectionOda loadCollectionOda2JSON) {
		oda2=new CompleteCollection(COLECCION_ODA, COLECCION_OBTENIDA_A_PARTIR_DE_ODA+ new Timestamp(new Date().getTime()));
		ObjetoVirtual=new HashMap<Integer, CompleteDocuments>();
	}

	@Override
	public void ProcessAttributes() {
		// TODO Auto-generated method stub
	}

	@Override
	public void ProcessInstances() {
		// TODO Auto-generated method stub

	}

	@Override
	public CompleteCollection getCollection() {
		return oda2;
	}

	@Override
	public HashMap<Integer, CompleteDocuments> getObjetoVirtual() {
		return ObjetoVirtual;
	}

	@Override
	public HashMap<Integer, ArrayList<String>> getVocabularies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<CompleteElementType, ArrayList<String>> getVocabularios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<CompleteElementType> getNOCompartidos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, CompleteDocuments> getFilesId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, CompleteDocuments> getFilesC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, CompleteDocuments> getURLC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, CompleteFile> getFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObjetoVirtual(HashMap<Integer, CompleteDocuments> objetoVirtual) {
		ObjetoVirtual = objetoVirtual;
	}

	public void setFileProcess(String filePath) {
		FilePath=filePath;		
	}

}
