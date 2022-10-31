package fdi.ucm.server.importparser.oda.oda2.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_NODE;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.ElementType_ObjetoVirtual_Resource_Direct_FILES_URL;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteResourceElementFile;
import fdi.ucm.server.modelComplete.collection.document.CompleteResourceElementURL;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteResourceElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

public class ElementType_ObjetoVirtual_Resource_Direct_FILES_URL_JSON
		extends ElementType_ObjetoVirtual_Resource_Direct_FILES_URL {

	private JSONObject JSONGeneral;


	public ElementType_ObjetoVirtual_Resource_Direct_FILES_URL_JSON(CompleteGrammar gramaPa,
			LoadCollectionOda L, JSONObject jSONGeneral) {
		super(gramaPa, L);
		JSONGeneral=jSONGeneral;
	}
	
	@Override
	public void ProcessInstances() {
		OwnInstances();
	}
	
	/**
	 * Procesa las instancias propias
	 */
	private void OwnInstances() {
		OwnInstancesPropias();
		OwnInstancesURL();
		InstancesAjenas();
		
		atributes_Recursos();
		
		
		
	}
	
	
	/**
	 * Procesa las instancias que corresponden a mis elementos
	 */
	private void OwnInstancesPropias() {
		
		JSONArray resources = (JSONArray) JSONGeneral.get("resources");

		
		for (int i = 0; i < resources.size(); i++) {
			JSONObject resourcesInst = 
					(JSONObject) resources.get(i);
			

	
			String iconoov="";
			if (resourcesInst.get("iconoov")!=null)
				 iconoov = resourcesInst.
					 get("iconoov").toString();
			 
//			String idov_refered="";
//			if (resourcesInst.get("idov_refered")!=null)
//				idov_refered = resourcesInst.
//					 get("idov_refered").toString();
			 
			 String idov = resourcesInst.
					 get("idov").toString();
			 
			 String visible="N";
				if (resourcesInst.get("visible")!=null)
					visible = resourcesInst.
					 get("visible").toString();
			 
				String name="";
				if (resourcesInst.get("name")!=null)
					name = resourcesInst.
					 get("name").toString();
			 
			 String id = resourcesInst.
					 get("id").toString();
			 

//			 String idresource_refered="";
//				if (resourcesInst.get("idresource_refered")!=null)
//					idresource_refered = resourcesInst.
//					 get("idresource_refered").toString();
			 
				String type="";
				if (resourcesInst.get("type")!=null)
					type = resourcesInst.
					 get("type").toString();
			 
//				String ordinal="";
//				if (resourcesInst.get("ordinal")!=null)
//					ordinal = resourcesInst.
//					 get("ordinal").toString();

			
			 if (type.equals("P"))
			 {
					if (idov!=null&&!idov.isEmpty()&&!name.isEmpty())
					{
					Integer Idov=Integer.parseInt(idov);
					
					name=name.trim();
					name=StaticFunctionsOda.CleanStringFromDatabase(name,LColec);
					CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
					StringBuffer SB=new StringBuffer();
					
					if (LColec.getBaseURLOda().isEmpty()||
							(!LColec.getBaseURLOda().startsWith("http://")
									&&!LColec.getBaseURLOda().startsWith("https://")
									&&!LColec.getBaseURLOda().startsWith("ftp://")))
						SB.append("http://");
					
					SB.append(LColec.getBaseURLOda());
					if (!LColec.getBaseURLOda().isEmpty()&&!LColec.getBaseURLOda().endsWith("//"))
						SB.append("/");
					SB.append(idov+"/"+name);
					
					String Path=SB.toString();
					
					CompleteFile FileC=LColec.getCollection().getFiles().get(Path);
					
					if (FileC==null) 
					{
					FileC=new CompleteFile(Path, LColec.getCollection().getCollection());
					LColec.getCollection().getFiles().put(Path, FileC);
					LColec.getCollection().getCollection().getSectionValues().add(FileC);
					}
					
					if (OVirtual!=null)
					{
						
						List<CompleteResourceElementType> Actuales = numActivos.get(Idov);
						
						if (Actuales==null)
							Actuales=new ArrayList<CompleteResourceElementType>();
						
						while(Actuales.size()>=numTotales.size())
							clonereso();
						
						
						CompleteResourceElementType mio = numTotales.get(Actuales.size());
						
						Actuales.add(mio);
						
						numActivos.put(Idov, Actuales);
						
						boolean Visiblebool=true;
						if (visible.equals("N"))
							Visiblebool=false;
						
						
						if (mio.getSons().get(0) instanceof CompleteTextElementType && mio.getSons().get(0).getName().equals(NameConstantsOda.IDNAME))
						{
						CompleteTextElement E=new CompleteTextElement((CompleteTextElementType) mio.getSons().get(0), id);
						OVirtual.getDescription().add(E);
						}
						
						
						CompleteResourceElementFile E3=new CompleteResourceElementFile(mio,FileC);
						OVirtual.getDescription().add(E3);			
						CompleteOperationalValue Valor=new CompleteOperationalValue(miofindValor2(mio),Boolean.toString(Visiblebool));
						E3.getShows().add(Valor);
						
						Long Idl=Long.parseLong(id);
						idsOV.add(Idl);
					
						CompleteAsociado.put(Idl, mio);
						
					if (iconoov.equals("S"))
						OVirtual.setIcon(FileC.getPath());

					}
					else {
						if (OVirtual==null)
							LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");

					}
					}
				else {
					//En la creacion de los objetos archivos aparece esta captura de errores
					if (idov==null||idov.isEmpty())
						LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
					if (name==null||name.isEmpty())
						LColec.getLog().add("Warning: Nombre recurso propio asociado vacio, Idrecurso: '"+id+"' (ignorado)");

				}
				 
			 }
				 
			 
		}
		
		
		
	}
	
	
	/**
	 * 
	 */
	private void OwnInstancesURL() {
		
		
	JSONArray resources = (JSONArray) JSONGeneral.get("resources");

		
		for (int i = 0; i < resources.size(); i++) {
			JSONObject resourcesInst = 
					(JSONObject) resources.get(i);
			
			
//			String iconoov="";
//			if (resourcesInst.get("iconoov")!=null)
//				 iconoov = resourcesInst.
//					 get("iconoov").toString();
//			 
//			String idov_refered="";
//			if (resourcesInst.get("idov_refered")!=null)
//				idov_refered = resourcesInst.
//					 get("idov_refered").toString();
			 
			 String idov = resourcesInst.
					 get("idov").toString();
			 
			 String visible="N";
				if (resourcesInst.get("visible")!=null)
					visible = resourcesInst.
					 get("visible").toString();
			 
				String name="";
				if (resourcesInst.get("name")!=null)
					name = resourcesInst.
					 get("name").toString();
			 
			 String id = resourcesInst.
					 get("id").toString();
			 

//			 String idresource_refered="";
//				if (resourcesInst.get("idresource_refered")!=null)
//					idresource_refered = resourcesInst.
//					 get("idresource_refered").toString();
			 
				String type="";
				if (resourcesInst.get("type")!=null)
					type = resourcesInst.
					 get("type").toString();
			 
//				String ordinal="";
//				if (resourcesInst.get("ordinal")!=null)
//					ordinal = resourcesInst.
//					 get("ordinal").toString();
			
			 
			 
			 
			 if (type.equals("U"))
			 {
				
				if (idov!=null&&!idov.isEmpty()&&!name.isEmpty())
				{
				Integer Idov=Integer.parseInt(idov);
				
				name=name.trim();
				name=StaticFunctionsOda.CleanStringFromDatabase(name,LColec);
				CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
				
				
				if (OVirtual!=null)
				{
					
					
					List<CompleteResourceElementType> Actuales = numActivos.get(Idov);
					
					if (Actuales==null)
						Actuales=new ArrayList<CompleteResourceElementType>();
					
					while(Actuales.size()>=numTotales.size())
						clonereso();
					
					
					CompleteResourceElementType mio = numTotales.get(Actuales.size());
					
					Actuales.add(mio);
					
					numActivos.put(Idov, Actuales);
					
					Long Idl=Long.parseLong(id);
					idsOV.add(Idl);
					
					CompleteAsociado.put(Idl, mio);
					
					boolean Visiblebool=true;
					if (visible.equals("N"))
						Visiblebool=false;
					

					//TODO 
//					LColec.getCollection().getFilesId().put(id,FileC);
					
					if (mio.getSons().get(0) instanceof CompleteTextElementType && mio.getSons().get(0).getName().equals(NameConstantsOda.IDNAME))
					{
					CompleteTextElement E=new CompleteTextElement((CompleteTextElementType) mio.getSons().get(0), id);
					OVirtual.getDescription().add(E);
					}
					
					
					CompleteResourceElementURL E3=new CompleteResourceElementURL(mio,name);
					OVirtual.getDescription().add(E3);			
					CompleteOperationalValue Valor=new CompleteOperationalValue(miofindValor2(mio),Boolean.toString(Visiblebool));
					E3.getShows().add(Valor);
					

				}
				else {
					if (OVirtual==null)
						LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");
					
				}
				}
			else {
				if (idov==null||idov.isEmpty())
					LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
				if (name==null||name.isEmpty())
					LColec.getLog().add("Warning: Nombre recurso URL propio asociado vacio, Idrecurso: '"+id+"' (ignorado)");

			}
			 }
			 
		}
		
			
		
	}
	
	
	
	private void InstancesAjenas() {
		
		
		
	JSONArray resources = (JSONArray) JSONGeneral.get("resources");

		
		for (int i = 0; i < resources.size(); i++) {
			JSONObject resourcesInst = 
					(JSONObject) resources.get(i);
			
//			String iconoov="";
//			if (resourcesInst.get("iconoov")!=null)
//				 iconoov = resourcesInst.
//					 get("iconoov").toString();
//			 
			String idov_refered="";
			if (resourcesInst.get("idov_refered")!=null)
				idov_refered = resourcesInst.
					 get("idov_refered").toString();
			 
			 String idov = resourcesInst.
					 get("idov").toString();
			 
			 String visible="N";
				if (resourcesInst.get("visible")!=null)
					visible = resourcesInst.
					 get("visible").toString();
			 
				String name="";
				if (resourcesInst.get("name")!=null)
					name = resourcesInst.
					 get("name").toString();
			 
			 String id = resourcesInst.
					 get("id").toString();
			 

			 String idresource_refered="";
				if (resourcesInst.get("idresource_refered")!=null)
					idresource_refered = resourcesInst.
					 get("idresource_refered").toString();
			 
				String type="";
				if (resourcesInst.get("type")!=null)
					type = resourcesInst.
					 get("type").toString();
			 
//				String ordinal="";
//				if (resourcesInst.get("ordinal")!=null)
//					ordinal = resourcesInst.
//					 get("ordinal").toString();
			
			if (type.equals("F"))
			 {
				
				if (idov!=null&&!idov.isEmpty()&&!name.isEmpty()&&(!idov_refered.isEmpty()||!idresource_refered.isEmpty()))
				{
				Integer Idov=Integer.parseInt(idov);
				
				name=name.trim();
				name=StaticFunctionsOda.CleanStringFromDatabase(name,LColec);
				
				CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
				
				
StringBuffer SB=new StringBuffer();
				
				if (LColec.getBaseURLOda().isEmpty()||
						(!LColec.getBaseURLOda().startsWith("http://")
								&&!LColec.getBaseURLOda().startsWith("https://")
								&&!LColec.getBaseURLOda().startsWith("ftp://")))
					SB.append("http://");
				
				SB.append(LColec.getBaseURLOda());
				if (!LColec.getBaseURLOda().isEmpty()&&!LColec.getBaseURLOda().endsWith("//"))
					SB.append("/");
				SB.append(idov+"/"+name);
				
				String Path=SB.toString();
				
				CompleteFile FileC=LColec.getCollection().getFiles().get(Path);
				
				if (FileC==null) 
					{
					FileC=new CompleteFile(Path, LColec.getCollection().getCollection());
					LColec.getCollection().getFiles().put(Path, FileC);
					LColec.getCollection().getCollection().getSectionValues().add(FileC);
					}
				
				if (OVirtual!=null)
				{
					
					
					List<CompleteResourceElementType> Actuales = numActivos.get(Idov);
					
					if (Actuales==null)
						Actuales=new ArrayList<CompleteResourceElementType>();
					
					while(Actuales.size()>=numTotales.size())
						clonereso();
					
					
					CompleteResourceElementType mio = numTotales.get(Actuales.size());
					
					Actuales.add(mio);
					
					numActivos.put(Idov, Actuales);
					
					boolean Visiblebool=true;
					if (visible.equals("N"))
						Visiblebool=false;
					
					
					
					
					//TODO 
//					LColec.getCollection().getFilesId().put(id,FileC);
					
					if (mio.getSons().get(0) instanceof CompleteTextElementType && mio.getSons().get(0).getName().equals(NameConstantsOda.IDNAME))
					{
					CompleteTextElement E=new CompleteTextElement((CompleteTextElementType) mio.getSons().get(0), id);
					OVirtual.getDescription().add(E);
					}
					
					
					CompleteResourceElementFile E3=new CompleteResourceElementFile(mio,FileC);
					OVirtual.getDescription().add(E3);			
					CompleteOperationalValue Valor=new CompleteOperationalValue(miofindValor2(mio),Boolean.toString(Visiblebool));
					E3.getShows().add(Valor);
					
					Long Idl=Long.parseLong(id);
					idsOV.add(Idl);
					
					CompleteAsociado.put(Idl, mio);
				
			
				}
				else{
					if (OVirtual==null)
						LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");
					
				}
				}
			else {
				if (idov==null||idov.isEmpty())
					LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
				if (name==null||name.isEmpty())
					LColec.getLog().add("Warning: Nombre recurso referencia asociado vacio, Idrecurso: '"+id+"' (ignorado)");
				if (idresource_refered==null||idresource_refered.isEmpty())
					LColec.getLog().add("Warning: Nombre objeto virtual referencia vacio, Idrecurso: '"+id+"' (ignorado)");
			}
				
			 }
		}
		
		
	}
	
	
	
	
	private void atributes_Recursos() {
		
		JSONArray section_data = (JSONArray) JSONGeneral.get("section_data");


		HashMap<String, LinkedList<JSONObject>> listaXPadre=new HashMap<String, LinkedList<JSONObject>>();
		
		for (int i = 0; i < section_data.size(); i++) {
			JSONObject section_dataInst = 
					(JSONObject) section_data.get(i);

			String idpadre=null;
			if(section_dataInst.get("idpadre")!=null)
				idpadre = section_dataInst.
				 get("idpadre").toString();
		
		
		
		if (idpadre!=null&&idpadre.equals("3"))
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
					
					
					if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&
							!tipo_valores.isEmpty()&&((tipo_valores.equals("C")&&
									vocabulario!=null)||(!(tipo_valores.equals("C")))))
					{
					
					nombre=nombre.trim();
					nombre = StaticFunctionsOda.CleanStringFromDatabase(nombre,LColec);
					
					ArrayList<CompleteResourceElementType> parsear = new ArrayList<CompleteResourceElementType>(numTotales);
					parsear.remove(AtributoMeta);
					
					
					ArrayList<CompleteElementType> Hermanos=new ArrayList<CompleteElementType>();
					
					ElementType_NODE_JSON Nodo=new ElementType_NODE_JSON(id,nombre,browseable,visible,
							tipo_valores,vocabulario,AtributoMeta,false,LColec,PadreGrammar,
							CompleteAsociado,CompleteAsociadoTabla,
							Hermanos,CompleteAsociadoID_IDOV,JSONGeneral," (Can be a OV)");
					CompleteElementType nodeattr = Nodo.getAtributoMeta();
					Hermanos.add(nodeattr);
					AtributoMeta.getSons().add(nodeattr);
					
					HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
					if (noexiste==null)
						noexiste=new HashMap<CompleteElementType, CompleteElementType>();
					noexiste.put(nodeattr, nodeattr);
					CompleteAsociadoTabla.put(AtributoMeta, noexiste);
					
					for (CompleteResourceElementType AtributoMeta2 : parsear) {
						ElementType_NODE Nodo2=new ElementType_NODE_JSON(id,nombre,browseable,visible,
								tipo_valores,vocabulario,AtributoMeta2,false,LColec,
								PadreGrammar,CompleteAsociado,CompleteAsociadoTabla,
								Hermanos,CompleteAsociadoID_IDOV,JSONGeneral," (Can be a OV)");
						CompleteElementType nodeattr2 = Nodo2.getAtributoMeta();
						nodeattr2.setClassOfIterator(nodeattr);
						AtributoMeta2.getSons().add(nodeattr2);
						Hermanos.add(nodeattr2);
						
						HashMap<CompleteElementType, CompleteElementType> noexiste2 = CompleteAsociadoTabla.get(AtributoMeta2);
						if (noexiste2==null)
							noexiste2=new HashMap<CompleteElementType, CompleteElementType>();
						noexiste2.put(nodeattr, nodeattr2);
						CompleteAsociadoTabla.put(AtributoMeta2, noexiste2);
					}
					
					
					Nodo.ProcessAttributes();
					Nodo.ProcessInstances();
					
					}
				else
					{
					if (tipo_valores==null||tipo_valores.isEmpty())
						LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '3' (ignorado)");
					if (nombre==null||nombre.isEmpty())
						LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '3' (ignorado)");
					if ((tipo_valores.equals("C")&&vocabulario==null))
						LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '3' (ignorado)");
						
					}

			}
		}
		

			
		
	}	
		
		
		
		
	

}
