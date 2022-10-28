package fdi.ucm.server.importparser.oda.oda2.json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_NODE;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

public class ElementType_NODE_JSON extends ElementType_NODE {

	private JSONObject JSONGeneral;

	public ElementType_NODE_JSON(String id, String nombre, String navegable, String visible, String tipo_valores,
			String vocabulario, CompleteElementType tpadre, boolean summary, LoadCollectionOda L, CompleteGrammar Cm,
			HashMap<Long, CompleteElementType> completeAsociado,
			HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>> completeAsociadoTabla,
			ArrayList<CompleteElementType> hermanos, HashMap<Long, Integer> completeAsociadoID_IDOV, JSONObject jSONGeneral) {
		super(id, nombre, navegable, visible, tipo_valores, vocabulario, tpadre, summary, L, Cm, completeAsociado,
				completeAsociadoTabla, hermanos, completeAsociadoID_IDOV);
		AtributoMeta.setName(StaticFunctionsOda.CleanStringFromDatabase(nombre,L));
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

				String browseable="N";
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
				
				
				
				if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&!tipo_valores.isEmpty()&&((tipo_valores.equals("C")&&vocabulario!=null)||(!(tipo_valores.equals("C")))))
				{
				
				ArrayList<CompleteElementType> parsear = new ArrayList<CompleteElementType>(Hermanos);
				parsear.remove(AtributoMeta);
				
				ArrayList<CompleteElementType> Hermanosint=new ArrayList<CompleteElementType>();
				
				
				ElementType_NODE_JSON Nodo=new ElementType_NODE_JSON(id,nombre,browseable,visible,tipo_valores,vocabulario,AtributoMeta,
						false,LColec,CM,CompleteAsociado,CompleteAsociadoTabla,Hermanosint,CompleteAsociadoID_IDOV,JSONGeneral);
				CompleteElementType nodeattr = Nodo.getAtributoMeta();
				Hermanosint.add(nodeattr);
				AtributoMeta.getSons().add(nodeattr);
				
				HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
				if (noexiste==null)
					noexiste=new HashMap<CompleteElementType, CompleteElementType>();
				noexiste.put(nodeattr, nodeattr);
				CompleteAsociadoTabla.put(AtributoMeta, noexiste);
				
				for (CompleteElementType AtributoMeta2 : parsear) {
					ElementType_NODE_JSON Nodo2=new ElementType_NODE_JSON(id,nombre,browseable,visible,tipo_valores,vocabulario,AtributoMeta2,false,
							LColec,CM,CompleteAsociado,CompleteAsociadoTabla,Hermanosint,CompleteAsociadoID_IDOV,JSONGeneral);
					CompleteElementType nodeattr2 = Nodo2.getAtributoMeta();
					nodeattr2.setClassOfIterator(nodeattr);
					AtributoMeta2.getSons().add(nodeattr2);
					Hermanosint.add(nodeattr2);
					
					HashMap<CompleteElementType, CompleteElementType> noexiste2 = CompleteAsociadoTabla.get(AtributoMeta2);
					if (noexiste2==null)
						noexiste2=new HashMap<CompleteElementType, CompleteElementType>();
					noexiste2.put(nodeattr, nodeattr2);
					CompleteAsociadoTabla.put(AtributoMeta, noexiste2);
				}
				
				Nodo.ProcessAttributes();
				Nodo.ProcessInstances();
				
				}
			else
			{
			if (tipo_valores==null||tipo_valores.isEmpty())
				LColec.getLog().add("Warning: Tipo de valores vacio o nulo, id estructura: '"+id+"', estuctura padre : '"+Id+"' (ignorado)");
			if (nombre==null||nombre.isEmpty())
				LColec.getLog().add("Warning: Nombre de la estructura del recurso vacia, id estructura: '"+id+"', padre : '"+Id+"' (ignorado)");
			if ((tipo_valores.equals("C")&&vocabulario==null))
				LColec.getLog().add("Warning: Tipo de estructura controlado pero valor de vocabulario vacio, id estructura: '"+id+"', padre : '"+Id+"' (ignorado)");
				
			}
				
				
		}
		}
		

		
	}
	
	
	@Override
	protected void ProcessInstancesNumericas() {

		JSONArray numeric_data = (JSONArray) JSONGeneral.get("numeric_data");
		
		for (int i = 0; i < numeric_data.size(); i++) {
			
			JSONObject numeric_dataInst = 
					(JSONObject) numeric_data.get(i);
					
			String idseccion=null;
			if (numeric_dataInst.get("idseccion")!=null)
				idseccion = numeric_dataInst.
				 get("idseccion").toString();
			
			if (idseccion.equals(Id))
			{
				String id1=numeric_dataInst.get("id").toString();
				
				String idov=numeric_dataInst.get("idov").toString();
				
				String value="";
				if(numeric_dataInst.get("value")!=null)
					value=numeric_dataInst.get("value").toString();
				
				String IdRecurso=null;
				if(numeric_dataInst.get("idrecurso")!=null)
					IdRecurso=numeric_dataInst.get("idrecurso").toString();
				
				
				//Bypass para los ids vacios de los recursos
				try {
					if ((idov==null||idov.isEmpty())&&IdRecurso!=null)
					{
						Long IdRecursoL=Long.parseLong(IdRecurso);
						idov=Integer.toString(CompleteAsociadoID_IDOV.get(IdRecursoL));
					}
				} catch (Exception e) {
					LColec.getLog().add("Error en idrecurso idrecurso no vacio ="+IdRecurso+" pero no asociado a ningun recurso , revisa que la base de datos es correcta");

				}
				
				
				
				if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
					{
	

					try {
					
					int Idov=Integer.parseInt(idov);
					CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);

					if (IdRecurso!=null)
					{
					
Long RecursoIntId = Long.parseLong(IdRecurso);
						
						
						CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
						
						
						if (EsteActual!=null)
						{

							HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
							if (TablaCuadre!=null)
							{
								CompleteElementType estees = TablaCuadre.get(AtributoMeta);
								if (estees!=null && estees instanceof CompleteTextElementType)
								{
									CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, value);
									MTV.setDocumentsFather(C);
									C.getDescription().add(MTV);
								}
								else
									LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
							}
							else
								LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
						}
					else 
						LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");
						
						
						
					}else
					{
						CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, value);
						MTV.setDocumentsFather(C);
						C.getDescription().add(MTV);
						}
					
					} catch (Exception e) {
						LColec.getLog().add("Error en numeric_data id='"+id1+"' en idov='"+idov+"'  y valor '"+value+"', revisa que la base de datos es correcta");
						e.printStackTrace();
					}
					
					}
					
				else 
				{
				if (idov==null||idov.isEmpty())
					LColec.getLog().add("Warning: Numerico asociado a un objeto virtual vacio o nulo, id en numeric_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
				if (value==null||value.isEmpty())
					LColec.getLog().add("Warning: Numerico asociado al campo padre: '"+Id+"' vacio o nulo: id en numeric_data: '"+id1+"' (ignorado)");
				}
			}
			
		}

		
		
	}
	
	@Override
	protected void ProcessInstancesControladas() {

		JSONArray controlled_data = (JSONArray) JSONGeneral.get("controlled_data");
		
		for (int i = 0; i < controlled_data.size(); i++) {
			
			JSONObject controlled_dataInst = 
					(JSONObject) controlled_data.get(i);
					
			String idseccion=null;
			if (controlled_dataInst.get("idseccion")!=null)
				idseccion = controlled_dataInst.
				 get("idseccion").toString();
			
			if (idseccion.equals(Id))
			{
				
				String id1=controlled_dataInst.get("id").toString();
				
				String idov=null;
				if(controlled_dataInst.get("idov")!=null)
					idov=controlled_dataInst.get("idov").toString();
//				
//				if (id1.equals("37417"))
//					System.out.println("aqui");
			
				
				String value="";
				if(controlled_dataInst.get("value")!=null)
					value=controlled_dataInst.get("value").toString();
				
				String IdRecurso=null;
				if(controlled_dataInst.get("idrecurso")!=null)
					IdRecurso=controlled_dataInst.get("idrecurso").toString();
	
				//Bypass para los ids vacios de los recursos
				try {
					if ((idov==null||idov.isEmpty())&&IdRecurso!=null)
					{
						Long IdRecursoL=Long.parseLong(IdRecurso);
						idov=Integer.toString(CompleteAsociadoID_IDOV.get(IdRecursoL));
					}
				} catch (Exception e) {
					LColec.getLog().add("Error en idrecurso idrecurso no vacio ="+IdRecurso+" pero no asociado a ningun recurso , revisa que la base de datos es correcta");

				}
				
				
				if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
					{
					
					value=value.trim();
					String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
					
					
//					
//					String T=StaticFunctionsOda1.BuscaEnLista(Vocabulary,valueclean);
					
					try {
//					CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
					int Idov=Integer.parseInt(idov);
					CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);
//					MTV.setDocumentsFather(C);
					
					if (IdRecurso!=null)
						{
						
						Long RecursoIntId = Long.parseLong(IdRecurso);
						CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
													
													
													if (EsteActual!=null)
													{

														HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
														if (TablaCuadre!=null)
														{
															CompleteElementType estees = TablaCuadre.get(AtributoMeta);
															if (estees!=null && estees instanceof CompleteTextElementType)
															{
																CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, valueclean);
																MTV.setDocumentsFather(C);
																C.getDescription().add(MTV);
															}
															else
																LColec.getLog().add("Error en controled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
														}
														else
															LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
													}
												else 
													LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");
													
						
						}else
						{
							CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
							C.getDescription().add(MTV);
							MTV.setDocumentsFather(C);
							}
					
					} catch (Exception e) {
						LColec.getLog().add("Error en controlled_data id='"+id1+"' en idov='"+idov+"' y valor '"+valueclean+"', revisa que la base de datos es correcta");
						e.printStackTrace();
					}
					}
				else 
					{
					if (idov==null||idov.isEmpty())
						LColec.getLog().add("Warning: Controlado asociado a un objeto virtual vacio o nulo, id en controlled_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
					if (value==null||value.isEmpty())
						LColec.getLog().add("Warning: Controlado asociado al campo padre: '"+Id+"' vacio o nulo: id en controlled_data: '"+id1+"' (ignorado)");
					}
				
			}
		}
		

		
		
	}
	
	@Override
	protected void ProcessInstancesFecha() {
		
		JSONArray date_data = (JSONArray) JSONGeneral.get("date_data");
		
		for (int i = 0; i < date_data.size(); i++) {
			
			JSONObject date_dataInst = 
					(JSONObject) date_data.get(i);
					
			String idseccion=null;
			if (date_dataInst.get("idseccion")!=null)
				idseccion = date_dataInst.
				 get("idseccion").toString();
			
			if (idseccion.equals(Id))
			{
				
				String id1=date_dataInst.get("id").toString();
				
				String idov=date_dataInst.get("idov").toString();
				
				String value="";
				if(date_dataInst.get("value")!=null)
					value=date_dataInst.get("value").toString();
				
				String IdRecurso=null;
				if(date_dataInst.get("idrecurso")!=null)
					IdRecurso=date_dataInst.get("idrecurso").toString();
				
				
				//Bypass para los ids vacios de los recursos
				try {
					if ((idov==null||idov.isEmpty())&&IdRecurso!=null)
					{
						Long IdRecursoL=Long.parseLong(IdRecurso);
						idov=Integer.toString(CompleteAsociadoID_IDOV.get(IdRecursoL));
					}
				} catch (Exception e) {
					LColec.getLog().add("Error en idrecurso idrecurso no vacio ="+IdRecurso+" pero no asociado a ningun recurso , revisa que la base de datos es correcta");

				}
				
				
				
				if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
					{
					
					
					value=value.trim();
					String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					if (!valueclean.isEmpty())
						{
						try {
						Date D= formatter.parse(valueclean);
						DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						String valueE=df.format(D);
						
//						CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueE);
						int Idov=Integer.parseInt(idov);
						CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);

//						MTV.setDocumentsFather(C);
						
	
						if (IdRecurso!=null)
						{
						
							
							Long RecursoIntId = Long.parseLong(IdRecurso);
							
							
							CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
							
							
							if (EsteActual!=null)
							{

								HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
								if (TablaCuadre!=null)
								{
									CompleteElementType estees = TablaCuadre.get(AtributoMeta);
									if (estees!=null && estees instanceof CompleteTextElementType)
									{
										CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, valueE);
										MTV.setDocumentsFather(C);
										C.getDescription().add(MTV);
									}
									else
										LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
								}
								else
									LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
							}
						else 
							LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");								
							

						}else
						{
							CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueE);
							MTV.setDocumentsFather(C);	
							C.getDescription().add(MTV);
						}
						
						} catch (Exception e) {
							LColec.getLog().add("Error en date_data id='"+id1+"' en idov='"+idov+"' y valor '"+valueclean+"', revisa que la base de datos es correcta");
							e.printStackTrace();
						}
					}
					
					
					
					
					
					}
				else 
				{
				if (idov==null||idov.isEmpty())
					LColec.getLog().add("Warning: Texto asociado a un objeto virtual vacio o nulo, id en text_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
				if (value==null||value.isEmpty())
					LColec.getLog().add("Warning: Texto asociado al campo padre: '"+Id+"' vacio o nulo: id en text_data: '"+id1+"' (ignorado)");
				}
				
			}
			
	}
		
		


	}
	
	@Override
	protected void ProcessInstancesTexto() {

		
		
		JSONArray text_data = (JSONArray) JSONGeneral.get("text_data");
		
		for (int i = 0; i < text_data.size(); i++) {
			
			JSONObject text_dataInst = 
					(JSONObject) text_data.get(i);
					
			String idseccion=null;
			if (text_dataInst.get("idseccion")!=null)
				idseccion = text_dataInst.
				 get("idseccion").toString();
			
			if (idseccion.equals(Id))
			{
				String id1=text_dataInst.get("id").toString();
				
				String idov=text_dataInst.get("idov").toString();
				
				String value="";
				if(text_dataInst.get("value")!=null)
					value=text_dataInst.get("value").toString();
				
				String IdRecurso=null;
				if(text_dataInst.get("idrecurso")!=null)
					IdRecurso=text_dataInst.get("idrecurso").toString();
				
				
				//Bypass para los ids vacios de los recursos
				try {
					if ((idov==null||idov.isEmpty())&&IdRecurso!=null)
					{
						Long IdRecursoL=Long.parseLong(IdRecurso);
						idov=Integer.toString(CompleteAsociadoID_IDOV.get(IdRecursoL));
					}
				} catch (Exception e) {
					LColec.getLog().add("Error en idrecurso idrecurso no vacio ="+IdRecurso+" pero no asociado a ningun recurso , revisa que la base de datos es correcta");

				}
				
				
				
				if (idov!=null&&!idov.isEmpty()&&!value.isEmpty())
					{
					
					
					value=value.trim();
					String valueclean = StaticFunctionsOda.CleanStringFromDatabase(value,LColec);
					
					try {
					
//					CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
					int Idov=Integer.parseInt(idov);
					CompleteDocuments C=LColec.getCollection().getObjetoVirtual().get(Idov);

//					MTV.setDocumentsFather(C);
					
					if (IdRecurso!=null)
					{
						

						Long RecursoIntId = Long.parseLong(IdRecurso);
							CompleteElementType EsteActual = CompleteAsociado.get(RecursoIntId);
							
							
							if (EsteActual!=null)
							{

								HashMap<CompleteElementType, CompleteElementType> TablaCuadre = CompleteAsociadoTabla.get(EsteActual);
								if (TablaCuadre!=null)
								{
									CompleteElementType estees = TablaCuadre.get(AtributoMeta);
									if (estees!=null && estees instanceof CompleteTextElementType)
									{
										CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) estees, valueclean);
										MTV.setDocumentsFather(C);
										C.getDescription().add(MTV);
									}
									else
										LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NB)");
								}
								else
									LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado(Code:NT)");
							}
						else 
							LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' no se encuentra el recurso asociado (Code:NR)");
				
						
					}
					else
					{
						
						CompleteTextElement MTV=new CompleteTextElement((CompleteTextElementType) AtributoMeta, valueclean);
						MTV.setDocumentsFather(C);
						C.getDescription().add(MTV);
						}
					
					} catch (Exception e) {
						LColec.getLog().add("Error en text_data id='"+id1+"' en idov='"+idov+"' con Recurso '"+IdRecurso+"' y valor '"+valueclean+"', revisa que la base de datos es correcta");
						e.printStackTrace();
					}
					
					}
				else 
				{
				if (idov==null||idov.isEmpty())
					LColec.getLog().add("Warning: Texto asociado a un objeto virtual vacio o nulo, id en text_data: '"+id1+"', campo padre: '"+Id+"' (ignorado)");
				if (value==null||value.isEmpty())
					LColec.getLog().add("Warning: Texto asociado al campo padre: '"+Id+"' vacio o nulo: id en text_data: '"+id1+"' (ignorado)");
				}

				
				
			}
			
	}
		

	}

}
