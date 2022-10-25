/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fdi.ucm.server.importparser.oda.MySQLConnectionOda;
import fdi.ucm.server.importparser.oda.coleccion.CollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda2.direct.cleanAPI.collection.CollectionOda2DirectAPI;
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;

/**
 * Clase que define la carga de una coleccion Oda1
 * @author Joaquin Gayoso-Cabada
 *
 */
public class LoadCollectionOda2JSON extends LoadCollectionOda{

	private boolean convert;
	private String BaseURLOda;
	private boolean CloneFiles = false;
	private MySQLConnectionOda SQL;
	private ArrayList<String> Log;
	private CollectionOda Odacollection;
	private String BaseURLOdaSimple;


public static void main(String[] args) {
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Server"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Database"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Number, "Port"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "User"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.EncriptedText, "Password"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Convert to UTF-8"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Oda base url for files (if need it, ej: http://<Server Name>/Oda)",true));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Clone local files to Clavy",true));
	
	
	// localhost text 3306 odauser contras3na true " " true
	ArrayList<String> DateEntrada=new ArrayList<String>();
	DateEntrada.add(args[0]);
	DateEntrada.add(args[1]);
	DateEntrada.add(args[2]);
	DateEntrada.add(args[3]);
	DateEntrada.add(args[4]);
	DateEntrada.add(args[5]);
	DateEntrada.add(args[6]);
	DateEntrada.add(args[7]);

	LoadCollectionOda LC=new LoadCollectionOda2JSON();
	CompleteCollectionAndLog Salida=LC.processCollecccion(DateEntrada);
	if (Salida!=null)
		{
		
		System.out.println("Correcto");
		
		for (String warning : Salida.getLogLines())
			System.err.println(warning);

		 try {
				String FileIO = System.getProperty("user.home")+File.separator+System.currentTimeMillis()+".clavy";
				
				System.out.println(FileIO);
				
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileIO));

				oos.writeObject(Salida.getCollection());

				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		System.exit(0);
		
		}
	else
		{
		System.err.println("Error");
		System.exit(-1);
		}
}



	@Override
	public CompleteCollectionAndLog processCollecccion(ArrayList<String> DateEntrada) {
		Log=new ArrayList<String>();
		 Odacollection=new CollectionOda2DirectAPI(this);
		
		if (DateEntrada!=null)
			
		{
			String Database = RemoveSpecialCharacters(DateEntrada.get(1));
			SQL = new MySQLConnectionOda(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));
			convert=Boolean.parseBoolean(DateEntrada.get(5));
			BaseURLOda=DateEntrada.get(6);
			
			if (!testURL(BaseURLOda))
			{
				throw new CompleteImportRuntimeException("Database is note empty and note look like a normal URL http://<Server Name>/Oda");
			}
		
		if (!BaseURLOda.endsWith("/"))
			BaseURLOda=BaseURLOda+"/";
		
		BaseURLOdaSimple= new String(BaseURLOda);
		
		BaseURLOda=BaseURLOda+"bo/download/";
			
			CloneFiles=Boolean.parseBoolean(DateEntrada.get(7));
			Odacollection.ProcessAttributes();
			Odacollection.ProcessInstances();
		}

		return new CompleteCollectionAndLog(Odacollection.getCollection(),Log);
	}


	@Override
	public String getName() {
		return "Oda 3.0 JSON";
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
	public String getBaseURLOdaSimple() {
		return BaseURLOdaSimple;
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
