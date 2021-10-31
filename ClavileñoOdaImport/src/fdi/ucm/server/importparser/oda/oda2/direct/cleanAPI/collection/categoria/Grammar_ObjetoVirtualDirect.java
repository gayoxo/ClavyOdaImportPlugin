/**
 * 
 */
package fdi.ucm.server.importparser.oda.oda2.direct.cleanAPI.collection.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fdi.ucm.server.importparser.oda.InterfaceOdaparser;
import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteResourceElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Clase que define la creacion de un virtual object metadata
 * @author Joaquin Gayoso-Cabada
 *
 */
public class Grammar_ObjetoVirtualDirect implements InterfaceOdaparser {

	private CompleteGrammar AtributoMeta;
	private CompleteTextElementType IDOV;
	
	private ElementType_ObjetoVirtual_Resource_Direct_OV Recursos;
	
	private ElementType_ObjetoVirtual_Resource_Direct_FILES_URL Recursos2;
	
	
	private CompleteOperationalValueType ValorOdaPUBLIC;
	private List<CompleteOperationalValueType> VistaOV;
	private List<CompleteOperationalValueType> VistaOVOda;
	private CompleteOperationalValueType ValorOdaPRIVATE;
	private LoadCollectionOda LColec;
	private CompleteTextElementType URLORIGINAL;
	private CompleteElementType Resources;

	
	public Grammar_ObjetoVirtualDirect(CompleteCollection completeCollection, LoadCollectionOda L) {
		AtributoMeta=new CompleteGrammar(NameConstantsOda.VIRTUAL_OBJECTNAME, NameConstantsOda.VIRTUAL_OBJECTNAME,completeCollection);
		
		VistaOV=new ArrayList<CompleteOperationalValueType>();
		LColec=L;
		
		CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		VistaOV.add(Valor);
		VistaOV.add(Valor2);
		VistaOV.add(Valor3);
		
		AtributoMeta.getViews().add(Valor);
		AtributoMeta.getViews().add(Valor2);
		AtributoMeta.getViews().add(Valor3);
		

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.VIRTUAL_OBJECT,NameConstantsOda.META);
		
		AtributoMeta.getViews().add(ValorMeta);
	
		
		VistaOVOda=new ArrayList<CompleteOperationalValueType>();
		
		ValorOdaPUBLIC=new CompleteOperationalValueType(NameConstantsOda.PUBLIC,Boolean.toString(true),NameConstantsOda.ODA);

		ValorOdaPRIVATE=new CompleteOperationalValueType(NameConstantsOda.PRIVATE,Boolean.toString(false),NameConstantsOda.ODA);

		VistaOVOda.add(ValorOdaPUBLIC);
		VistaOVOda.add(ValorOdaPRIVATE);
		
		AtributoMeta.getViews().add(ValorOdaPUBLIC);
		AtributoMeta.getViews().add(ValorOdaPRIVATE);
		

	}
	
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		{
		IDOV=new CompleteTextElementType(NameConstantsOda.IDOVNAME, AtributoMeta);
		

		
		CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		
		IDOV.getShows().add(Valor);
		IDOV.getShows().add(Valor2);
		IDOV.getShows().add(Valor3);
		


		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.IDOV,NameConstantsOda.META);
		
		IDOV.getShows().add(ValorMeta);

		
		AtributoMeta.getSons().add(IDOV);
		

		 CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.TEXT,NameConstantsOda.METATYPE);
		 IDOV.getShows().add(Valor4);
		
		}
		
		{
			URLORIGINAL=new CompleteTextElementType(NameConstantsOda.URLORIGINAL, AtributoMeta);
			


			CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.URLORIGINAL,NameConstantsOda.META);
			
			URLORIGINAL.getShows().add(ValorMeta);
			

			 CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.TEXT,NameConstantsOda.METATYPE);
			 URLORIGINAL.getShows().add(Valor4);


			 CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.IGNORED,NameConstantsOda.META);
			 URLORIGINAL.getShows().add(Valor);

			 
			
			AtributoMeta.getSons().add(URLORIGINAL);
			}
		
		
		{
		
		
		
		}
		
		
	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessInstances()
	 */
	@Override
	public void ProcessInstances() {
		OwnInstances();
		
		Resources = new CompleteElementType(NameConstantsOda.RESOURCESNAME,AtributoMeta);
		AtributoMeta.getSons().add(Resources);
		
		
		Recursos=new ElementType_ObjetoVirtual_Resource_Direct_OV(AtributoMeta,LColec);
		Recursos.ProcessAttributes();
		AtributoMeta.getSons().add(Recursos.getAtributoMeta());

		Recursos.ProcessInstances();
		
		
		Recursos2=new ElementType_ObjetoVirtual_Resource_Direct_FILES_URL(AtributoMeta,LColec);
		Recursos2.ProcessAttributes();
		AtributoMeta.getSons().add(Recursos2.getAtributoMeta());
		
		
		
		Recursos2.ProcessInstances();
		
		
		
		

	}
	
	
	public void Add2Grammar() {
		
		
		AtributoMeta.getSons().add(Recursos.getAtributoMeta());
		AtributoMeta.getSons().add(Recursos2.getAtributoMeta());
	}
	

	/**
	 * Procesa las instancias propias
	 */
	private void OwnInstances() {
//		 ObjetoVirtualMetaValueAsociado=new HashMap<Integer, Element>();
		 HashMap<Integer, CompleteDocuments> ObjetoVirtual= new HashMap<Integer, CompleteDocuments>();
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM virtual_object;");
			if (rs!=null) 
			{
				while (rs.next()) {
					
					String id=rs.getObject("id").toString();
					
					String publicoT="";
					if(rs.getObject("ispublic")!=null)
						publicoT=rs.getObject("ispublic").toString();

					String privateT="S";
					if(rs.getObject("isprivate")!=null&&!rs.getObject("isprivate").toString().isEmpty())
						privateT=rs.getObject("isprivate").toString();
					
					
					if (publicoT!=null&&!publicoT.isEmpty())
						{
						int Idov=Integer.parseInt(id);
						boolean publico=true;
						if (publicoT.equals("N"))
							publico=false;
						boolean privado=true;
						if (privateT.equals("N"))
							privado=false;
						CompleteCollection C=LColec.getCollection().getCollection();
						CompleteDocuments sectionValue = new CompleteDocuments(C,"","");
						C.getEstructuras().add(sectionValue);
						CompleteTextElement E=new CompleteTextElement(IDOV, id);
						sectionValue.getDescription().add(E);
						
						CompleteOperationalValue ValorOdaPUBLIInstance=new CompleteOperationalValue(ValorOdaPUBLIC,Boolean.toString(publico));

				//		Element MetaValueAsociado = new Element(AtributoMeta);
						sectionValue.getViewsValues().add(ValorOdaPUBLIInstance);
//						MetaValueAsociado.getShows().add(ValorOdaPUBLIInstance);
						
						CompleteOperationalValue ValorOdaPRIVInstance=new CompleteOperationalValue(ValorOdaPRIVATE,Boolean.toString(privado));

						//		Element MetaValueAsociado = new Element(AtributoMeta);
								sectionValue.getViewsValues().add(ValorOdaPRIVInstance);
						
						
//						ObjetoVirtualMetaValueAsociado.put(Idov, MetaValueAsociado);
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
						if (publicoT==null||publicoT.isEmpty())
							LColec.getLog().add("Warning: Estado de privacidad ambiguo en, Identificador Objeto virtual: '"+id+"' (ignorado)");
					}
				}
				LColec.getCollection().setObjetoVirtual(ObjetoVirtual);
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * @return the atributoMeta
	 */
	public CompleteGrammar getAtributoMeta() {
		return AtributoMeta;
	}

	/**
	 * @param atributoMeta the atributoMeta to set
	 */
	public void setAtributoMeta(CompleteGrammar atributoMeta) {
		AtributoMeta = atributoMeta;
	}




	/**
	 * @return the vistaOV
	 */
	public List<CompleteOperationalValueType> getVistaOV() {
		return VistaOV;
	}


	/**
	 * @param vistaOV the vistaOV to set
	 */
	public void setVistaOV(List<CompleteOperationalValueType> vistaOV) {
		VistaOV = vistaOV;
	}

	
	
	


	
	

}
