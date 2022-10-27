package fdi.ucm.server.importparser.oda.oda2.json;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.ElementType_ObjetoVirtual_Resource_Direct_FILES_URL;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.ElementType_ObjetoVirtual_Resource_Direct_OV;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.Grammar_ObjetoVirtualDirect;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;

public class Grammar_ObjetoVirtualDirectJSON extends Grammar_ObjetoVirtualDirect {

	private JSONArray VirtualObject;


	public Grammar_ObjetoVirtualDirectJSON(CompleteCollection completeCollection,
			LoadCollectionOda L, JSONArray virtualObject) {
		super(completeCollection, L);
		VirtualObject=virtualObject;
	}
	
	@Override
	public void ProcessAttributes() {
		super.ProcessAttributes();
	}
	
	
	@Override
	public void ProcessInstances() {
		OwnInstances();
		
		Recursos=new ElementType_ObjetoVirtual_Resource_Direct_OV(AtributoMeta,LColec);
		Recursos.ProcessAttributes();
		AtributoMeta.getSons().add(Recursos.getAtributoMeta());

		//Recursos.ProcessInstances();
		
		
		Recursos2=new ElementType_ObjetoVirtual_Resource_Direct_FILES_URL(AtributoMeta,LColec);
		Recursos2.ProcessAttributes();
		AtributoMeta.getSons().add(Recursos2.getAtributoMeta());
		
		
		
		//Recursos2.ProcessInstances();

	}
	
	private void OwnInstances() {
		
		 HashMap<Integer, CompleteDocuments> ObjetoVirtual= new HashMap<Integer, CompleteDocuments>();
		
		for (int i = 0; i < VirtualObject.size(); i++) {
			
			JSONObject virtualObjectInst = 
					(JSONObject) VirtualObject.get(i);
			
			 String isprivate = virtualObjectInst.
					 get("isprivate").toString();
			 
			 String ispublic = virtualObjectInst.
					 get("ispublic").toString();
			 
			 String id = virtualObjectInst.
					 get("id").toString();

			 
				if (isprivate!=null&&!ispublic.isEmpty())
				{
				int Idov=Integer.parseInt(id);
				boolean publico=true;
				if (ispublic.equals("N"))
					publico=false;
				boolean privado=true;
				if (isprivate.equals("N"))
					privado=false;
				CompleteCollection C=LColec.getCollection().getCollection();
				CompleteDocuments sectionValue = new CompleteDocuments(C,"","");
				C.getEstructuras().add(sectionValue);
				CompleteTextElement E=new CompleteTextElement(IDOV, id);
				sectionValue.getDescription().add(E);
				
				CompleteOperationalValue ValorOdaPUBLIInstance=new CompleteOperationalValue(ValorOdaPUBLIC,Boolean.toString(publico));

		//		Element MetaValueAsociado = new Element(AtributoMeta);
				sectionValue.getViewsValues().add(ValorOdaPUBLIInstance);
//				MetaValueAsociado.getShows().add(ValorOdaPUBLIInstance);
				
				CompleteOperationalValue ValorOdaPRIVInstance=new CompleteOperationalValue(ValorOdaPRIVATE,Boolean.toString(privado));

				//		Element MetaValueAsociado = new Element(AtributoMeta);
						sectionValue.getViewsValues().add(ValorOdaPRIVInstance);
				
				
//				ObjetoVirtualMetaValueAsociado.put(Idov, MetaValueAsociado);
				ObjetoVirtual.put(Idov, sectionValue);
				
				
				StringBuffer SB=new StringBuffer();
				
				if (LColec.getBaseURLOdaSimple().isEmpty()||
						(!LColec.getBaseURLOdaSimple().startsWith("http://")
								&&!LColec.getBaseURLOdaSimple().startsWith("https://")
								&&!LColec.getBaseURLOdaSimple().startsWith("ftp://")))
					SB.append("http://");
				
				SB.append(LColec.getBaseURLOdaSimple());
				if (!LColec.getBaseURLOdaSimple().isEmpty()&&!LColec.getBaseURLOdaSimple().endsWith("//"))
					SB.append("/");
				SB.append(NameConstantsOda.VIEWDOC+id);
				
				String Path=SB.toString();
				
				CompleteTextElement RR=new CompleteTextElement(URLORIGINAL, Path);
				sectionValue.getDescription().add(RR);
				
				
				}
			else {
				if (isprivate==null||ispublic.isEmpty())
					LColec.getLog().add("Warning: Estado de privacidad ambiguo en, Identificador Objeto virtual: '"+id+"' (ignorado)");
			}
			 
			 
			
		}
		
		LColec.getCollection().setObjetoVirtual(ObjetoVirtual);
		
	}
	
	
//	/**
//	 * Procesa las instancias propias
//	 */
//	private void OwnInstancesOld() {
////		 ObjetoVirtualMetaValueAsociado=new HashMap<Integer, Element>();
//		 HashMap<Integer, CompleteDocuments> ObjetoVirtual= new HashMap<Integer, CompleteDocuments>();
//		try {
//			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM virtual_object;");
//			if (rs!=null) 
//			{
//				while (rs.next()) {
//					
//					String id=rs.getObject("id").toString();
//					
//					String publicoT="";
//					if(rs.getObject("ispublic")!=null)
//						publicoT=rs.getObject("ispublic").toString();
//
//					String privateT="S";
//					if(rs.getObject("isprivate")!=null&&!rs.getObject("isprivate").toString().isEmpty())
//						privateT=rs.getObject("isprivate").toString();
//					
//					
//					if (publicoT!=null&&!publicoT.isEmpty())
//						{
//						int Idov=Integer.parseInt(id);
//						boolean publico=true;
//						if (publicoT.equals("N"))
//							publico=false;
//						boolean privado=true;
//						if (privateT.equals("N"))
//							privado=false;
//						CompleteCollection C=LColec.getCollection().getCollection();
//						CompleteDocuments sectionValue = new CompleteDocuments(C,"","");
//						C.getEstructuras().add(sectionValue);
//						CompleteTextElement E=new CompleteTextElement(IDOV, id);
//						sectionValue.getDescription().add(E);
//						
//						CompleteOperationalValue ValorOdaPUBLIInstance=new CompleteOperationalValue(ValorOdaPUBLIC,Boolean.toString(publico));
//
//				//		Element MetaValueAsociado = new Element(AtributoMeta);
//						sectionValue.getViewsValues().add(ValorOdaPUBLIInstance);
////						MetaValueAsociado.getShows().add(ValorOdaPUBLIInstance);
//						
//						CompleteOperationalValue ValorOdaPRIVInstance=new CompleteOperationalValue(ValorOdaPRIVATE,Boolean.toString(privado));
//
//						//		Element MetaValueAsociado = new Element(AtributoMeta);
//								sectionValue.getViewsValues().add(ValorOdaPRIVInstance);
//						
//						
////						ObjetoVirtualMetaValueAsociado.put(Idov, MetaValueAsociado);
//						ObjetoVirtual.put(Idov, sectionValue);
//						
//						
//						StringBuffer SB=new StringBuffer();
//						
//						if (LColec.getBaseURLOdaSimple().isEmpty()||
//								(!LColec.getBaseURLOdaSimple().startsWith("http://")
//										&&!LColec.getBaseURLOdaSimple().startsWith("https://")
//										&&!LColec.getBaseURLOdaSimple().startsWith("ftp://")))
//							SB.append("http://");
//						
//						SB.append(LColec.getBaseURLOdaSimple());
//						if (!LColec.getBaseURLOdaSimple().isEmpty()&&!LColec.getBaseURLOdaSimple().endsWith("//"))
//							SB.append("/");
//						SB.append(NameConstantsOda.VIEWDOC+id);
//						
//						String Path=SB.toString();
//						
//						CompleteTextElement RR=new CompleteTextElement(URLORIGINAL, Path);
//						sectionValue.getDescription().add(RR);
//						
//						
//						}
//					else {
//						if (publicoT==null||publicoT.isEmpty())
//							LColec.getLog().add("Warning: Estado de privacidad ambiguo en, Identificador Objeto virtual: '"+id+"' (ignorado)");
//					}
//				}
//				LColec.getCollection().setObjetoVirtual(ObjetoVirtual);
//			rs.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}

}
