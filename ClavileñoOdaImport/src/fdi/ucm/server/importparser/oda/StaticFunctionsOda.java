/**
 * 
 */
package fdi.ucm.server.importparser.oda;

//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
//import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.document.CompleteElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteLinkElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteResourceElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteLinkElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteResourceElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Clase que genera las funciones estaticas para el sistema Oda1.
 * @author Joaquin Gayoso-Cabada
 *
 */
public class StaticFunctionsOda {

	

	



	
	/**
	 * Inserta el valor del texto en la lista si no esta
	 * @param vocabulary
	 * @param valueclean
	 */
	public static void InsertaEnLista(ArrayList<String> vocabulary, String valueclean) {
		boolean Encontrado=false;
		for (String term : vocabulary) {
			if (term.equals(valueclean))
				{
				Encontrado=true;
				break;
				}
		}
		if (!Encontrado)
			vocabulary.add(valueclean);
		
	}
	
	/**
	 * Funcion que busca una descripcion en una lista de descripciones de un elemento
	 * @param list lista de descripciones
	 * @param text texto a buscar
	 * @return el Meta valu de esa descripcion o null en caso contrario.
	 */
	public static CompleteElement FindDescAtrib(List<CompleteElement> list,
			String text) {
		for (CompleteElement completeElement : list) {
			if(completeElement.getHastype().getName().equals(text))
				return completeElement;
		}
		return null;
	}

	/**
	 * Limpia el String de la base de datos si procede, sino se devuelve
	 * @param value
	 * @return string limpio o si mismo depenede del flag de conversion
	 */
	public static String CleanStringFromDatabase(String value,LoadCollectionOda L) {
		if (L.isConvert())
		{
			
			
			return parseUTF(value);
			
//			Charset utf8charset = Charset.forName("UTF-8");
//		    Charset iso88591charset = Charset.forName("ISO-8859-1");
//
//		    ByteBuffer inputBuffer = ByteBuffer.wrap(value.getBytes());
//		    CharBuffer data = utf8charset.decode(inputBuffer);
//
//		    ByteBuffer outputBuffer = iso88591charset.encode(data);
//		    byte[] outputData = outputBuffer.array();
//
//		    return new String(outputData);
			

		}
		else return value;
	}

	private static String parseUTF(String valor1) {
		valor1 = valor1.replace("Ã¡", "á");
		valor1 = valor1.replace("Ã©", "é");
		valor1 = valor1.replace("Ã*", "í");
		valor1 = valor1.replace("Ã³", "ó");
		valor1 = valor1.replace("Ãº", "ú");
		valor1 = valor1.replace("Ã", "Á");
		valor1 = valor1.replace("Ã‰", "É");
		valor1 = valor1.replace("Ã", "Í");
		valor1 = valor1.replace("Ã“", "Ó");
		valor1 = valor1.replace("Ãš", "Ú");
		valor1 = valor1.replace("Ã±", "ñ");
		valor1 = valor1.replace("Ã‘", "Ñ");
		return valor1;
	}
	
	/**
	 * Retorna si el atributo es controlado.
	 * @param atributoMeta
	 * @return
	 */
	public static boolean isControled(CompleteElementType attribute) {
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsOda.METATYPE))
			{

					if (show.getName().equals(NameConstantsOda.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsOda.CONTROLED)) 
										return true;
			}
		}
		return false;
	}

	
	/**
	 * Retorna si el atributo es controlado.
	 * @param atributoMeta
	 * @return
	 */
	public static boolean isDate(CompleteElementType attribute) {
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsOda.METATYPE))
			{
					if (show.getName().equals(NameConstantsOda.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsOda.DATE)) 
										return true;
			}
		}
		return false;
	}

	
	public static boolean isNumeric(CompleteElementType attribute) {
		ArrayList<CompleteOperationalValueType> Shows = attribute.getShows();
		for (CompleteOperationalValueType show : Shows) {	
			if (show.getView().equals(NameConstantsOda.METATYPE))
			{
					if (show.getName().equals(NameConstantsOda.METATYPETYPE))
							if (show.getDefault().equals(NameConstantsOda.NUMERIC)) 
										return true;
			}
		}
		return false;
	}

	public static CompleteElementType cloneElement(CompleteElementType aclonar) {
		aclonar.setMultivalued(true);
		
		CompleteElementType nuevo=null;
		if (aclonar instanceof CompleteTextElementType)
			nuevo=new CompleteTextElementType(aclonar.getName(),aclonar.getFather() , aclonar.getCollectionFather());
		if (aclonar instanceof CompleteResourceElementType)
			nuevo=new CompleteResourceElementType(aclonar.getName(),aclonar.getFather() , aclonar.getCollectionFather());
		if (aclonar instanceof CompleteLinkElementType)
			nuevo=new CompleteLinkElementType(aclonar.getName(),aclonar.getFather() , aclonar.getCollectionFather());
		if (nuevo==null)
			nuevo=new CompleteElementType(aclonar.getName(),aclonar.getFather() , aclonar.getCollectionFather());
		
		nuevo.setMultivalued(aclonar.isMultivalued());
		nuevo.setBrowseable(aclonar.isBrowseable());
		aclonar.getFather().getSons().add(nuevo);
		
		CompleteElementType ultimohermano=aclonar;
		while (ultimohermano.getBSon()!=null)
			ultimohermano=ultimohermano.getBSon();
		
		ultimohermano.setBSon(nuevo);
		nuevo.setBFather(ultimohermano);
		
		nuevo.setClassOfIterator(aclonar);

		for (CompleteElementType iterable_element : aclonar.getSons()) {
			cloneElementintern(iterable_element,nuevo);
		}
		
		
		return nuevo;
	}

	private static void cloneElementintern(CompleteElementType aclonar, CompleteElementType nuevopadre) {
		
		CompleteElementType nuevo=null;
		if (aclonar instanceof CompleteTextElementType)
			nuevo=new CompleteTextElementType(aclonar.getName(),nuevopadre , aclonar.getCollectionFather());
		if (aclonar instanceof CompleteResourceElementType)
			nuevo=new CompleteResourceElementType(aclonar.getName(),nuevopadre , aclonar.getCollectionFather());
		if (aclonar instanceof CompleteLinkElementType)
			nuevo=new CompleteLinkElementType(aclonar.getName(),nuevopadre , aclonar.getCollectionFather());
		if (nuevo==null)
			nuevo=new CompleteElementType(aclonar.getName(),nuevopadre , aclonar.getCollectionFather());
		
		nuevo.setMultivalued(aclonar.isMultivalued());
		nuevo.setBrowseable(aclonar.isBrowseable());
		nuevopadre.getSons().add(nuevo);
		
		neesito que se clonen los hijos internamente
		
		CompleteElementType ultimohermano=aclonar;
		while (ultimohermano.getBSon()!=null)
			ultimohermano=ultimohermano.getBSon();
		
		ultimohermano.setBSon(nuevo);
		nuevo.setBFather(ultimohermano);
		
		nuevo.setClassOfIterator(aclonar);

		for (CompleteElementType iterable_element : aclonar.getSons()) {
			cloneElementintern(iterable_element,nuevo);
		}
		
		
	}

	


	
}
