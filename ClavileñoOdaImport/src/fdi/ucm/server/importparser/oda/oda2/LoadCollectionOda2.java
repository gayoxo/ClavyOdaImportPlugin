/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2;

import java.util.ArrayList;

import fdi.ucm.server.importparser.oda.MySQLConnectionOda;
import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda2.coleccion.CollectionOda2;
import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;

/**
 * Clase que define la carga de una coleccion Oda1
 * @author Joaquin Gayoso-Cabada
 *
 */
public class LoadCollectionOda2 extends LoadCollectionOda{

	private static ArrayList<ImportExportPair> Parametros;
	private boolean convert;
	private String BaseURLOda;
	private boolean CloneFiles = false;
	private MySQLConnectionOda SQL;
	private ArrayList<String> Log;
	private CollectionOda Odacollection;




	@Override
	public CompleteCollectionAndLog processCollecccion(ArrayList<String> DateEntrada) {
		Log=new ArrayList<String>();
		 Odacollection=new CollectionOda2(this);
		
		if (DateEntrada!=null)
			
		{
			String Database = RemoveSpecialCharacters(DateEntrada.get(1));
			SQL = new MySQLConnectionOda(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));
			convert=Boolean.parseBoolean(DateEntrada.get(5));
			BaseURLOda=DateEntrada.get(6);
			CloneFiles=Boolean.parseBoolean(DateEntrada.get(7));
			Odacollection.ProcessAttributes();
			Odacollection.ProcessInstances();
		}

		return new CompleteCollectionAndLog(Odacollection.getCollection(),Log);
	}

	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Server"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Database"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "Port"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "User"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "Password"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Convert to UTF-8"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Base url for files (if need it)",true));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Clone local files",true));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}

	@Override
	public String getName() {
		return "Oda 2.0";
	}
	
	/**
	 * QUitar caracteres especiales.
	 * @param str texto de entrada.
	 * @return texto sin caracteres especiales.
	 */
	public String RemoveSpecialCharacters(String str) {
		   StringBuilder sb = new StringBuilder();
		   for (int i = 0; i < str.length(); i++) {
			   char c = str.charAt(i);
			   if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_') {
			         sb.append(c);
			      }
		}
		   return sb.toString();
		}

	/**
	 * @return the convert
	 */
	@Override
	public boolean isConvert() {
		return convert;
	}

	/**
	 * @param convert the convert to set
	 */
	public void setConvert(boolean convert) {
		this.convert = convert;
	}

	/**
	 * @return the baseURLOda
	 */
	public String getBaseURLOda() {
		return BaseURLOda;
	}

	/**
	 * @param baseURLOda the baseURLOda to set
	 */
	public void setBaseURLOda(String baseURLOda) {
		BaseURLOda = baseURLOda;
	}

	@Override
	public boolean getCloneLocalFiles() {
		return CloneFiles;
	}

	@Override
	public MySQLConnectionOda getSQL() {
		return SQL;
	}

	@Override
	public ArrayList<String> getLog() {
		return Log;
	}

	@Override
	public CollectionOda getCollection() {
		return Odacollection;
	}
	
}
