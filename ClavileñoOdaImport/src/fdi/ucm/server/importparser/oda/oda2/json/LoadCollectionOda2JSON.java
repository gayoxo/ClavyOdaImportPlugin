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
import fdi.ucm.server.modelComplete.CompleteImportRuntimeException;
import fdi.ucm.server.modelComplete.ImportExportDataEnum;
import fdi.ucm.server.modelComplete.ImportExportPair;
import fdi.ucm.server.modelComplete.collection.CompleteCollectionAndLog;

/**
 * Clase que define la carga de una coleccion Oda1
 * @author Joaquin Gayoso-Cabada
 *
 */
public class LoadCollectionOda2JSON extends LoadCollectionOda{

	private String BaseURLOda;
	private boolean CloneFiles = false;
	private ArrayList<String> Log;
	private CollectionOda Odacollection;
	private String BaseURLOdaSimple;
	private String FilePath;
	private boolean convert;



public static void main(String[] args) {


//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.File, "JsonFile"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Convert to UTF-8"));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Oda base url for files (if need it, ej: http://<Server Name>/Oda)",true));
//	ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Clone local files to Clavy",true));
	
	
	// response.json true "http://repositorios.fdi.ucm.es/DiccionarioDidacticoLatin" true
	ArrayList<String> DateEntrada=new ArrayList<String>();
	DateEntrada.add(args[0]);
	DateEntrada.add(args[1]);
	DateEntrada.add(args[2]);
	DateEntrada.add(args[3]);

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
//		 Odacollection=new CollectionOda2DirectAPI(this);

		 Odacollection=new CollectionOda2DirectAPIJSON(this);

		if (DateEntrada!=null)
	
		{
			FilePath = DateEntrada.get(0);
			convert=Boolean.parseBoolean(DateEntrada.get(1));
			BaseURLOda=DateEntrada.get(2);
			
			if (!testURL(BaseURLOda))
			{
				throw new CompleteImportRuntimeException("Database is note empty and note look like a normal URL http://<Server Name>/Oda");
			}
		
		if (!BaseURLOda.endsWith("/"))
			BaseURLOda=BaseURLOda+"/";
		
		BaseURLOdaSimple= new String(BaseURLOda);
		
		BaseURLOda=BaseURLOda+"bo/download/";
			
		((CollectionOda2DirectAPIJSON)Odacollection).setFileProcess(FilePath);
		
			CloneFiles=Boolean.parseBoolean(DateEntrada.get(3));
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
		return null;
	}

	@Override
	public ArrayList<String> getLog() {
		return Log;
	}

	@Override
	public CollectionOda getCollection() {
		return Odacollection;
	}
	
	@Override
	public ArrayList<ImportExportPair> getConfiguracion() {
		if (Parametros==null)
		{
			ArrayList<ImportExportPair> ListaCampos=new ArrayList<ImportExportPair>();
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.File, "JsonFile"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Convert to UTF-8"));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Text, "Oda base url for files (if need it, ej: http://<Server Name>/Oda)",true));
			ListaCampos.add(new ImportExportPair(ImportExportDataEnum.Boolean, "Clone local files to Clavy",true));
			Parametros=ListaCampos;
			return ListaCampos;
		}
		else return Parametros;
	}
}
