package fdi.ucm.server.importparser.oda.oda2.direct.cleanAPI.collection.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_NODE;
import fdi.ucm.server.importparser.oda.coleccion.categoria.ElementType_ObjetoVirtual_Resource;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteResourceElementURL;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteResourceElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

public class ElementType_ObjetoVirtual_Resource_Direct extends ElementType_ObjetoVirtual_Resource{

	private ArrayList<Long> idsOV=new ArrayList<Long>();
	protected HashMap<Integer, List<CompleteResourceElementType>> numActivos;
	protected List<CompleteResourceElementType> numTotales;
	
	
	public ElementType_ObjetoVirtual_Resource_Direct(CompleteGrammar I, LoadCollectionOda L) {
		super();
		PadreGrammar=I;
		AtributoMeta=new CompleteResourceElementType(NameConstantsOda.RESOURCESNAME,I);
		AtributoMeta.setMultivalued(true);
		
		CompleteAsociado=new HashMap<Long, CompleteElementType>();
		CompleteAsociadoTabla=new HashMap<CompleteElementType, HashMap<CompleteElementType, CompleteElementType>>();
		
		numTotales=new ArrayList<CompleteResourceElementType>();
		numActivos=new HashMap<Integer, List<CompleteResourceElementType>>();
		
		numTotales.add((CompleteResourceElementType)AtributoMeta);
		LColec=L;
		idsOV=new ArrayList<Long>();
		
		
		
		HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
		if (noexiste==null)
			noexiste=new HashMap<CompleteElementType, CompleteElementType>();
		noexiste.put(AtributoMeta, AtributoMeta);
		CompleteAsociadoTabla.put(AtributoMeta, noexiste);
		
		Valor2=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		AtributoMeta.getShows().add(Valor2);
		AtributoMeta.getShows().add(Valor4);
		AtributoMeta.getShows().add(Valor3);
		

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.RESOURCE,NameConstantsOda.META);
		AtributoMeta.getShows().add(ValorMeta);
		
		CompleteOperationalValueType ValorLink = new CompleteOperationalValueType(NameConstantsOda.LINK,Boolean.toString(false),NameConstantsOda.META);
		AtributoMeta.getShows().add(ValorLink);
	}

	public void ProcessAttributes() {
		{
			ID=new CompleteTextElementType(NameConstantsOda.IDNAME, AtributoMeta,PadreGrammar);
			AtributoMeta.getSons().add(ID);
			
			
			Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
			CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
			CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
			
			ID.getShows().add(Valor);
			ID.getShows().add(Valor2);
			ID.getShows().add(Valor3);

			
			 CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.IGNORED,NameConstantsOda.META);
			 ID.getShows().add(Valor);
			}

		
	}


	public void ProcessInstances() {
		OwnInstances();
		
	}
	
	/**
	 * Procesa las instancias propias
	 */
	private void OwnInstances() {
		OwnInstancesGeneral();
		

		
		atributes_Recursos();
		
		
		
	}
	
	
	private void atributes_Recursos() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM section_data where idpadre=3 order by orden;");
			if (rs!=null) 
			{
				while (rs.next()) {
					String id=rs.getObject("id").toString();
					
					String nombre=rs.getObject("nombre").toString();
					
					String navegable="N";
					if(rs.getObject("browseable")!=null)
						navegable=rs.getObject("browseable").toString();
					
					String visible="N";
					if(rs.getObject("visible")!=null)
						visible=rs.getObject("visible").toString();
					
					String tipo_valores=null;
					if(rs.getObject("tipo_valores")!=null)
						tipo_valores=rs.getObject("tipo_valores").toString();
					
					String vocabulario=null;
					if(rs.getObject("vocabulario")!=null)
						vocabulario=rs.getObject("vocabulario").toString();
					
					if (nombre!=null&&!nombre.isEmpty()&&tipo_valores!=null&&!tipo_valores.isEmpty()&&((tipo_valores.equals("C")&&vocabulario!=null)||(!(tipo_valores.equals("C")))))
						{
						
						nombre=nombre.trim();
						nombre = StaticFunctionsOda.CleanStringFromDatabase(nombre,LColec);
						
						ArrayList<CompleteResourceElementType> parsear = new ArrayList<CompleteResourceElementType>(numTotales);
						parsear.remove(AtributoMeta);
						
						
						ArrayList<CompleteElementType> Hermanos=new ArrayList<CompleteElementType>();
						
						ElementType_NODE Nodo=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta,false,LColec,PadreGrammar,
								CompleteAsociado,CompleteAsociadoTabla,Hermanos,CompleteAsociadoID_IDOV);
						CompleteElementType nodeattr = Nodo.getAtributoMeta();
						Hermanos.add(nodeattr);
						AtributoMeta.getSons().add(nodeattr);
						
						HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta);
						if (noexiste==null)
							noexiste=new HashMap<CompleteElementType, CompleteElementType>();
						noexiste.put(nodeattr, nodeattr);
						CompleteAsociadoTabla.put(AtributoMeta, noexiste);
						
						for (CompleteResourceElementType AtributoMeta2 : parsear) {
							ElementType_NODE Nodo2=new ElementType_NODE(id,nombre,navegable,visible,tipo_valores,vocabulario,AtributoMeta2,false,LColec,
									PadreGrammar,CompleteAsociado,CompleteAsociadoTabla,Hermanos,CompleteAsociadoID_IDOV);
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
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
	private void OwnInstancesGeneral() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM resources order by idov,id;");
			if (rs!=null) 
			{

				while (rs.next()) {
					
					String id=rs.getObject("id").toString();
					String idov=rs.getObject("idov").toString();
					
					String Visible="N";
					if(rs.getObject("visible")!=null)
						Visible=rs.getObject("visible").toString();
					
					String name="";
					if(rs.getObject("name")!=null)
						name=rs.getObject("name").toString();
					
					String idovreferedFile="";
					if(rs.getObject("idov_refered")!=null)
						idovreferedFile=rs.getObject("idov_refered").toString();
					
					String idreferedFile="";
					if(rs.getObject("idresource_refered")!=null)
						idreferedFile=rs.getObject("idresource_refered").toString();
					
					String type="";
					if(rs.getObject("type")!=null)
						type=rs.getObject("type").toString();
					
					String iconoOV="N";
					if(rs.getObject("iconoov")!=null)
						iconoOV=rs.getObject("iconoov").toString();
					
					
					if (idov!=null&&!idov.isEmpty()&&!name.isEmpty()&&!type.isEmpty())
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
							if (Visible.equals("N"))
								Visiblebool=false;
							

							if (mio.getSons().get(0) instanceof CompleteTextElementType && mio.getSons().get(0).getName().equals(NameConstantsOda.IDNAME))
							{
							CompleteTextElement E=new CompleteTextElement((CompleteTextElementType) mio.getSons().get(0), id);
							OVirtual.getDescription().add(E);
							}
							
							
							Long Idl=Long.parseLong(id);
							idsOV.add(Idl);
							
							CompleteAsociado.put(Idl, mio);
							CompleteAsociadoID_IDOV.put(Idl, Idov);
							
							
							String valorURL="";
							
							if (type.equals("U"))
							{
																
								valorURL=name;
								
								
							}
							
							if (type.equals("P"))  
							{

//									SB.append("bo/download/");
									SB.append(idov+"/"+name);
									
									valorURL=SB.toString();

									if (iconoOV.equals("S"))
										OVirtual.setIcon(valorURL);


								
							}
							
							
							if (type.equals("F"))  
							{
								if (!idreferedFile.isEmpty())
								{
									try {
										ResultSet rsF=LColec.getSQL().RunQuerrySELECT("SELECT * FROM resources where idresource_refered = "+idreferedFile+" order by idov,id;");
										
//										String idF=rsF.getObject("id").toString();
										String idovF=rsF.getObject("idov").toString();
										
										String nameF="";
										if(rsF.getObject("name")!=null)
											nameF=rsF.getObject("name").toString();
										
//										SB.append("bo/download/");
										SB.append(idovF+"/"+nameF);
										
										valorURL=SB.toString();
										
									} catch (Exception e) {
										LColec.getLog().add("Warning: Error recuperando referencia a recurso externo (F) vacio, Idrecurso: '"+id+"' f:'"+idreferedFile+"' (ignorado)");
									}
									
	
								}else
										LColec.getLog().add("Warning: Nombre fichero en objeto virtual referencia vacio, Idrecurso: '"+id+"' (ignorado)");
								
							} 
							
							if (type.equals("OV"))
							{
								
								//http://repositorios.fdi.ucm.es/DiccionarioDidacticoLatin/view/cm_view_virtual_object.php?idov=514&seleccion=1
								if (!idovreferedFile.isEmpty())
								{
									Integer idovrefered2=Integer.parseInt(idovreferedFile);
									CompleteDocuments OVirtualRef=LColec.getCollection().getObjetoVirtual().get(idovrefered2);
									
									if (OVirtualRef!=null) {
										
										SB= new StringBuffer();
										if (LColec.getBaseURLOdaSimple().isEmpty()||
												(!LColec.getBaseURLOdaSimple().startsWith("http://")
														&&!LColec.getBaseURLOdaSimple().startsWith("https://")
														&&!LColec.getBaseURLOdaSimple().startsWith("ftp://")))
											SB.append("http://");
										
										SB.append(LColec.getBaseURLOdaSimple());
										if (!LColec.getBaseURLOdaSimple().isEmpty()&&!LColec.getBaseURLOdaSimple().endsWith("//"))
											SB.append("/");
										
										SB.append(NameConstantsOda.VIEWDOC);
										SB.append(idovrefered2);
										SB.append("&seleccion=1");
										
										valorURL=SB.toString();
									}
									else
										LColec.getLog().add("Warning: El Objeto Virtal referencia al que apunta : Referencia : '" + idovreferedFile + "' no existe, Idrecurso: '"+id+ "'(ignorado)");


								}else
									LColec.getLog().add("Warning: Nombre objeto virtual referenciado es vacio, Idrecurso: '"+id+"' (ignorado)");
									
							}
							
							
							
							
							
							
							if (!valorURL.isEmpty())
							{
								CompleteResourceElementURL E3=new CompleteResourceElementURL(mio,valorURL);
								OVirtual.getDescription().add(E3);			
								CompleteOperationalValue Valor=new CompleteOperationalValue(miofindValor2(mio),Boolean.toString(Visiblebool));
								E3.getShows().add(Valor);
								CompleteOperationalValue ValorLink=new CompleteOperationalValue(miofindValor2(mio),Boolean.toString(true));
								E3.getShows().add(ValorLink);
							}

							
	
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
							LColec.getLog().add("Warning: Nombre recurso asociado vacio, Idrecurso: '"+id+"' (ignorado)");
						if (type==null||type.isEmpty())
							LColec.getLog().add("Warning: Tipo recurso asociado vacio, Idrecurso: '"+id+"' (ignorado)");

					}
				}

			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	protected CompleteOperationalValueType miofindValor3(CompleteElementType mio) {
		for (CompleteOperationalValueType Ovalue : mio.getShows()) {
			if (Ovalue.getView().equals(NameConstantsOda.META)&&Ovalue.getName().equals(NameConstantsOda.LINK))
				return Ovalue;
		}
		return null;
	}


	
	@Override
	protected void clonereso() {
		CompleteResourceElementType AtributoMeta2 = new CompleteResourceElementType(NameConstantsOda.RESOURCESNAME,PadreGrammar);
		numTotales.add(AtributoMeta2);
		AtributoMeta2.setClassOfIterator(AtributoMeta);
		AtributoMeta2.setMultivalued(true);
		
		CompleteOperationalValueType Valor23 = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor43=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		CompleteOperationalValueType Valor33=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
		
		AtributoMeta2.getShows().add(Valor23);
		AtributoMeta2.getShows().add(Valor43);
		AtributoMeta2.getShows().add(Valor33);
		


		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.RESOURCE,NameConstantsOda.META);
		AtributoMeta2.getShows().add(ValorMeta);
		
		CompleteOperationalValueType ValorLink = new CompleteOperationalValueType(NameConstantsOda.LINK,Boolean.toString(false),NameConstantsOda.META);
		AtributoMeta2.getShows().add(ValorLink);
		
		{
			CompleteTextElementType ID2 = new CompleteTextElementType(NameConstantsOda.IDNAME, AtributoMeta2,PadreGrammar);
			AtributoMeta2.getSons().add(ID2);
			ID2.setClassOfIterator(ID);
			
			CompleteOperationalValueType Valor12 = new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),NameConstantsOda.PRESNTACION);
			CompleteOperationalValueType Valor22=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
			CompleteOperationalValueType Valor32=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),NameConstantsOda.PRESNTACION);
			
			ID2.getShows().add(Valor12);
			ID2.getShows().add(Valor22);
			ID2.getShows().add(Valor32);
			

			 CompleteOperationalValueType Valor42=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.IGNORED,NameConstantsOda.META);
			 ID2.getShows().add(Valor42);
			}
		
		PadreGrammar.getSons().add(AtributoMeta2);
		
		HashMap<CompleteElementType, CompleteElementType> noexiste = CompleteAsociadoTabla.get(AtributoMeta2);
		if (noexiste==null)
			noexiste=new HashMap<CompleteElementType, CompleteElementType>();
		noexiste.put(AtributoMeta, AtributoMeta2);
		CompleteAsociadoTabla.put(AtributoMeta2, noexiste);
		
	}

	

	


	
	/**
	 * @return the atributoMeta
	 */
	public CompleteElementType getAtributoMeta() {
		return AtributoMeta;
	}

	/**
	 * @param atributoMeta the atributoMeta to set
	 */
	public void setAtributoMeta(CompleteResourceElementType atributoMeta) {
		AtributoMeta = atributoMeta;
	}

	public List<CompleteResourceElementType> getAtributoMetaList() {
		return new LinkedList<CompleteResourceElementType>(numTotales);
	}

}
