/**
 * 
 */
package fdi.ucm.server.importparser.oda.coleccion;

import java.util.ArrayList;

import fdi.ucm.server.importparser.oda.MySQLConnectionOda;
import fdi.ucm.server.modelComplete.LoadCollection;

/**
 * @author Joaquin Gayoso-Cabada
 *
 */
public abstract class LoadCollectionOda extends LoadCollection {

	public abstract boolean isConvert();

	public abstract MySQLConnectionOda getSQL();

	public abstract ArrayList<String> getLog();

	public abstract CollectionOda getCollection();

	public abstract String getBaseURLOda();




}
