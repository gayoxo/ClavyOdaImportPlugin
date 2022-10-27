package fdi.ucm.server.importparser.oda.oda2.json;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_NODE;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;

public class ElementType_NODE_JSON extends ElementType_NODE {

	private JSONObject JSONGeneral;

	public ElementType_NODE_JSON(String id, String nombre, String navegable, String visible, String tipo_valores,
			String vocabulario, CompleteElementType tpadre, boolean summary, LoadCollectionOda L, CompleteGrammar Cm,
			HashMap<Long, CompleteElementType> completeAsociado,
			HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>> completeAsociadoTabla,
			ArrayList<CompleteElementType> hermanos, HashMap<Long, Integer> completeAsociadoID_IDOV, JSONObject jSONGeneral) {
		super(id, nombre, navegable, visible, tipo_valores, vocabulario, tpadre, summary, L, Cm, completeAsociado,
				completeAsociadoTabla, hermanos, completeAsociadoID_IDOV);
		JSONGeneral=jSONGeneral;
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
		
		
		
		if (idpadre!=null&&idpadre.equals(Id))
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
//			String codigo="";
//			if (section_dataInst.get("codigo")!=null)
//				codigo = section_dataInst.
//					 get("codigo").toString();
			 
			String tipo_valores=null;
			if (section_dataInst.get("tipo_valores")!=null)
				tipo_valores = section_dataInst.
					 get("tipo_valores").toString();
			 
			 String visible="N";
				if (section_dataInst.get("visible")!=null)
					visible = section_dataInst.
					 get("visible").toString();
			 
//				String tooltip="";
//				if (section_dataInst.get("tooltip")!=null)
//					tooltip = section_dataInst.
//					 get("tooltip").toString();
//			 
//				String decimales="";
//				if (section_dataInst.get("decimales")!=null)
//					decimales = section_dataInst.
//					 get("decimales").toString();
			 

			 String id = section_dataInst.
					 get("id").toString();

				String browseable=null;
				if (section_dataInst.get("browseable")!=null)
					browseable = section_dataInst.
					 get("browseable").toString();
				
				String nombre= section_dataInst.
						 get("nombre").toString();
				
				
//				String extensible="N";
//				if (section_dataInst.get("extensible")!=null)
//					extensible = section_dataInst.
//					 get("extensible").toString();
				
				String vocabulario=null;
				if (section_dataInst.get("vocabulario")!=null)
					vocabulario = section_dataInst.
					 get("vocabulario").toString();
		}
		}
		
//		try {
//			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM section_data where idpadre="+Id+" order by orden;");
//			if (rs!=null) 
//			{
//				while (rs.next()) {
//					String id=rs.getObject("id").toString();
//					
//					String nombre=rs.getObject("nombre").toString();
//					
//					String navegable="N";
//					if(rs.getObject("browseable")!=null)
//						navegable=rs.getObject("browseable").toString();
//					
//					String visible="N";
//					if(rs.getObject("visible")!=null)
//						visible=rs.getObject("visible").toString();
//					
//					String tipo_valores=rs.getObject("tipo_valores").toString();
//					
//					String vocabulario=null;
//					if(rs.getObject("vocabulario")!=null)
//						vocabulario=rs.getObject("vocabulario").toString();
//					
//					if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&!tipo_valores.isEmpty()&&((tipo_valores.equals("C")&&vocabulario!=null)||(!(tipo_valores.equals("C")))))
//						{
//						
//						ArrayList<CompleteElementType> parsear = new ArrayList<CompleteElementType>(Hermanos);
//						parsear.remove(AtributoMeta);
//						
//						ArrayList<CompleteElementType> Hermanosint=new ArrayList<CompleteElementType>();
//						
//						
//						ElementType_NODE Nodo=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta,
//								false,LColec,CM,CompleteAsociado,CompleteAsociadoTabla,Hermanosint,CompleteAsociadoID_IDOV);
//						CompleteElementType nodeattr = Nodo.getAtributoMeta();
//						Hermanosint.add(nodeattr);
//						AtributoMeta.getSons().add(nodeattr);
//						
//						HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
//						if (noexiste==null)
//							noexiste=new HashMap<CompleteElementType, CompleteElementType>();
//						noexiste.put(nodeattr, nodeattr);
//						CompleteAsociadoTabla.put(AtributoMeta, noexiste);
//						
//						for (CompleteElementType AtributoMeta2 : parsear) {
//							ElementType_NODE Nodo2=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta2,false,
//									LColec,CM,CompleteAsociado,CompleteAsociadoTabla,Hermanosint,CompleteAsociadoID_IDOV);
//							CompleteElementType nodeattr2 = Nodo2.getAtributoMeta();
//							nodeattr2.setClassOfIterator(nodeattr);
//							AtributoMeta2.getSons().add(nodeattr2);
//							Hermanosint.add(nodeattr2);
//							
//							HashMap<CompleteElementType, CompleteElementType> noexiste2 = CompleteAsociadoTabla.get(AtributoMeta2);
//							if (noexiste2==null)
//								noexiste2=new HashMap<CompleteElementType, CompleteElementType>();
//							noexiste2.put(nodeattr, nodeattr2);
//							CompleteAsociadoTabla.put(AtributoMeta, noexiste2);
//						}
//						
//						Nodo.ProcessAttributes();
//						Nodo.ProcessInstances();
//						
//						}
//					else
//					{
//					if (tipo_valores==null||tipo_valores.isEmpty())
//						LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '"+Id+"' (ignorado)");
//					if (nombre==null||nombre.isEmpty())
//						LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '"+Id+"' (ignorado)");
//					if ((tipo_valores.equals("C")&&vocabulario==null))
//						LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '"+Id+"' (ignorado)");
//						
//					}
//					
//				}
//			rs.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		if (StaticFunctionsOda.isControled(AtributoMeta))
//			ProcessVocabulary();
		
	}

}
