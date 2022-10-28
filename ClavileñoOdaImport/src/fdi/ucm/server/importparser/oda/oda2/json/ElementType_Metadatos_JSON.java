package fdi.ucm.server.importparser.oda.oda2.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_Metadatos;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_NODE;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;

public class ElementType_Metadatos_JSON extends ElementType_Metadatos {

	private JSONObject JSONGeneral;

	public ElementType_Metadatos_JSON(CompleteGrammar Padre, 
			LoadCollectionOda L, JSONObject odaJSONCollection) {
		super(Padre, L);
		JSONGeneral=odaJSONCollection;
	}
	
	@Override
	public void ProcessAttributes() {
		JSONArray section_data = (JSONArray) JSONGeneral.get("section_data");


		HashMap<String, LinkedList<JSONObject>> listaXPadre=new HashMap<String, LinkedList<JSONObject>>();
		
		for (int i = 0; i < section_data.size(); i++) {
			JSONObject section_dataInst = 
					(JSONObject) section_data.get(i);

			String idpadre=null;
			if(section_dataInst.get("idpadre")!=null)
				idpadre = section_dataInst.
				 get("idpadre").toString();
		
		
		
		if (idpadre!=null&&idpadre.equals("2"))
			{
			LinkedList<JSONObject> listaAct = listaXPadre.get(idpadre);
			if (listaAct==null)
				listaAct=new LinkedList<JSONObject>();
			
			listaAct.add(section_dataInst);
			
			listaXPadre.put(idpadre, listaAct);
			}
		}
		
		for (Entry<String, LinkedList<JSONObject>> id_lista : listaXPadre.entrySet()) {
//			String idpadreE = id_lista.getKey();
			LinkedList<JSONObject> listaE = id_lista.getValue();
			
			Collections.sort(listaE,new Comparator<JSONObject>() {

				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					
					String ordeno1="0";
					if(o1.get("orden")!=null)
						ordeno1=o1.get("orden").toString();
					
					String ordeno2="0";
					if(o2.get("orden")!=null)
						ordeno2=o2.get("orden").toString();
					// TODO Auto-generated method stub
					return ordeno1.compareTo(ordeno2);
				}
			});
			
			
			for (JSONObject section_dataInst : listaE) {
//				String codigo="";
//				if (section_dataInst.get("codigo")!=null)
//					codigo = section_dataInst.
//						 get("codigo").toString();
				 
				String tipo_valores=null;
				if (section_dataInst.get("tipo_valores")!=null)
					tipo_valores = section_dataInst.
						 get("tipo_valores").toString();
				 
				 String visible="N";
					if (section_dataInst.get("visible")!=null)
						visible = section_dataInst.
						 get("visible").toString();
				 
//					String tooltip="";
//					if (section_dataInst.get("tooltip")!=null)
//						tooltip = section_dataInst.
//						 get("tooltip").toString();
//				 
//					String decimales="";
//					if (section_dataInst.get("decimales")!=null)
//						decimales = section_dataInst.
//						 get("decimales").toString();
				 

				 String id = section_dataInst.
						 get("id").toString();

				 String browseable="N";
					if (section_dataInst.get("browseable")!=null)
						browseable = section_dataInst.
						 get("browseable").toString();
					
					String nombre= section_dataInst.
							 get("nombre").toString();
					
					
//					String extensible="N";
//					if (section_dataInst.get("extensible")!=null)
//						extensible = section_dataInst.
//						 get("extensible").toString();
					
					String vocabulario=null;
					if (section_dataInst.get("vocabulario")!=null)
						vocabulario = section_dataInst.
						 get("vocabulario").toString();
					
					
					if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&!tipo_valores.isEmpty()
							&&((tipo_valores.equals("C")&&vocabulario!=null)||(!(tipo_valores.equals("C")))))
					{
					
					nombre=nombre.trim();
					nombre = StaticFunctionsOda.CleanStringFromDatabase(nombre,LColec);
					
					ElementType_NODE Nodo=new ElementType_NODE_JSON(id,nombre,browseable,visible,tipo_valores,vocabulario,
							AtributoMeta,false,LColec,Padre,new HashMap<Long, CompleteElementType>(),
							new HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>>(),
							new ArrayList<CompleteElementType>(),new HashMap<Long, Integer>(),JSONGeneral);
					Nodo.ProcessAttributes();
					Nodo.ProcessInstances();
					AtributoMeta.getSons().add(Nodo.getAtributoMeta());
					}
				else
				{
				if (tipo_valores==null||tipo_valores.isEmpty())
					LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '2' (ignorado)");
				if (nombre==null||nombre.isEmpty())
					LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '2' (ignorado)");
				if ((tipo_valores.equals("C")&&vocabulario==null))
					LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '2' (ignorado)");
					
				}
					
			}
			
		}
	}

}
