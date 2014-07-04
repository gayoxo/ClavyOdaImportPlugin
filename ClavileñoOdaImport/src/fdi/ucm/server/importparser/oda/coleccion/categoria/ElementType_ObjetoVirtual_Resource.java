/**
 * 
 */
package fdi.ucm.server.importparser.oda.coleccion.categoria;

import java.sql.ResultSet;
import java.sql.SQLException;

import fdi.ucm.server.importparser.oda.InterfaceOdaparser;
import fdi.ucm.server.importparser.oda.NameConstantsOda;
import fdi.ucm.server.importparser.oda.StaticFunctionsOda;
import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.modelComplete.collection.document.CompleteDocuments;
import fdi.ucm.server.modelComplete.collection.document.CompleteFile;
import fdi.ucm.server.modelComplete.collection.document.CompleteLinkElement;
import fdi.ucm.server.modelComplete.collection.document.CompleteOperationalValue;
import fdi.ucm.server.modelComplete.collection.document.CompleteTextElement;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteIterator;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteLinkElementType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalValueType;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteOperationalView;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteTextElementType;

/**
 * Implementa el atributo de los recursos en los Objetos Virtuales
 * @author Joaquin Gayoso-Cabada
 *
 */
public class ElementType_ObjetoVirtual_Resource implements InterfaceOdaparser {

	private CompleteLinkElementType AtributoMeta;
	private CompleteTextElementType ID;
	private CompleteIterator IteradorPadre;
	private CompleteOperationalValueType Valor;
	private CompleteOperationalValueType Valor2;
	private LoadCollectionOda LColec;
	
	
	public ElementType_ObjetoVirtual_Resource(CompleteIterator I,LoadCollectionOda L) {
		IteradorPadre=I;
		AtributoMeta=new CompleteLinkElementType(NameConstantsOda.RESOURCENAME,I);
		LColec=L;
		
		CompleteOperationalView VistaOV=new CompleteOperationalView(NameConstantsOda.PRESNTACION); 
		
		Valor2=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),VistaOV);
		CompleteOperationalValueType Valor4=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),VistaOV);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),VistaOV);
		
		VistaOV.getValues().add(Valor2);
		VistaOV.getValues().add(Valor4);
		VistaOV.getValues().add(Valor3);
		
		CompleteOperationalView VistaOVMeta=new CompleteOperationalView(NameConstantsOda.META);

		CompleteOperationalValueType ValorMeta=new CompleteOperationalValueType(NameConstantsOda.TYPE,NameConstantsOda.RESOURCE,VistaOV);
		VistaOVMeta.getValues().add(ValorMeta);
		
		AtributoMeta.getShows().add(VistaOVMeta);
		
		AtributoMeta.getShows().add(VistaOV);
	}
	
	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessAttributes()
	 */
	@Override
	public void ProcessAttributes() {
		{
		ID=new CompleteTextElementType(NameConstantsOda.IDNAME, AtributoMeta);
		AtributoMeta.getSons().add(ID);
		
		CompleteOperationalView VistaOV=new CompleteOperationalView(NameConstantsOda.PRESNTACION); 
		
		Valor=new CompleteOperationalValueType(NameConstantsOda.VISIBLESHOWN,Boolean.toString(true),VistaOV);
		CompleteOperationalValueType Valor2=new CompleteOperationalValueType(NameConstantsOda.BROWSERSHOWN,Boolean.toString(false),VistaOV);
		CompleteOperationalValueType Valor3=new CompleteOperationalValueType(NameConstantsOda.SUMMARYSHOWN,Boolean.toString(false),VistaOV);
		
		VistaOV.getValues().add(Valor);
		VistaOV.getValues().add(Valor2);
		VistaOV.getValues().add(Valor3);
		ID.getShows().add(VistaOV);
		
		CompleteOperationalView VistaOV2=new CompleteOperationalView(NameConstantsOda.METATYPE);
		 CompleteOperationalValueType Valor=new CompleteOperationalValueType(NameConstantsOda.METATYPETYPE,NameConstantsOda.TEXT,VistaOV2);
		 VistaOV2.getValues().add(Valor);
		 ID.getShows().add(VistaOV2);
		}

	}

	/* (non-Javadoc)
	 * @see fdi.ucm.server.importparser.oda1.Oda1parserModel#ProcessInstances()
	 */
	@Override
	public void ProcessInstances() {
		OwnInstances();

	}

	/**
	 * Procesa las instancias propias
	 */
	private void OwnInstances() {
		OwnInstancesPropias();
		InstancesAjenas();
		OVInstances();
		
		
		
		
	}
	private void OVInstances() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM resources where type='OV' order by idov;");
			if (rs!=null) 
			{
				Integer preIdov=null;
				int MaxCount=0;
				int count=1;
				while (rs.next()) {
					
					String id=rs.getObject("id").toString();
					String idov=rs.getObject("idov").toString();
					
					String Visible="N";
					if(rs.getObject("visible")!=null)
						Visible=rs.getObject("visible").toString();
					
					String idovrefered="";
					if(rs.getObject("idov_refered")!=null)
						idovrefered=rs.getObject("idov_refered").toString();
					

					
					
					if (idov!=null&&!idov.isEmpty()&&!idovrefered.isEmpty())
						{
						Integer Idov=Integer.parseInt(idov);
						Integer idovrefered2=Integer.parseInt(idovrefered);
						
						
						CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
						CompleteDocuments OVirtualRef=LColec.getCollection().getObjetoVirtual().get(idovrefered2);
						
						if (OVirtual!=null&&OVirtualRef!=null)
						{
							
						if (preIdov!=null&&preIdov.intValue()==Idov.intValue())
							count++;
						else
							{
							if (count>MaxCount)
								MaxCount=count;
							preIdov=Idov;
							count=1;
							}
						
						
						boolean Visiblebool=false;
						if (Visible.equals("S"))
							Visiblebool=true;
						
						{
						CompleteTextElement E=new CompleteTextElement(ID, id);
						E.getAmbitos().add(count);
						OVirtual.getDescription().add(E);

						CompleteOperationalValue Valor=new CompleteOperationalValue(this.Valor,Boolean.toString(Visiblebool));
						E.getShows().add(Valor);
						}
						
						{
							CompleteLinkElement E3=new CompleteLinkElement(AtributoMeta,OVirtualRef);
						E3.getAmbitos().add(count);
						OVirtual.getDescription().add(E3);
						


						CompleteOperationalValue Valor=new CompleteOperationalValue(Valor2,Boolean.toString(Visiblebool));

						E3.getShows().add(Valor);
						
						
						}

							
						
						}
					else 
					{
						if (OVirtual==null)
							LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");
						if (OVirtualRef==null)
							LColec.getLog().add("Warning: El Objeto Virtal referencia al que apunta : Referencia : '" + idovrefered + "' no existe, Idrecurso: '"+id+ "'(ignorado)");
					}
						}
					else {
						if (idov==null||idov.isEmpty())
							LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
						if (idovrefered==null||idovrefered.isEmpty())
							LColec.getLog().add("Warning: Nombre objeto virtual referencia vacio, Idrecurso: '"+id+"' (ignorado)");
					}
				}
			IteradorPadre.setAmbitoSTotales(MaxCount);
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void InstancesAjenas() {
		try {
			ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM resources where type='F' order by idov;");
			if (rs!=null) 
			{
				Integer preIdov=null;
				int MaxCount=0;
				int count=1;
				while (rs.next()) {
					
					String id=rs.getObject("id").toString();
					String idov=rs.getObject("idov").toString();
					
					String Visible="N";
					if(rs.getObject("visible")!=null)
						Visible=rs.getObject("visible").toString();
					
					String idovreferedFile="";
					if(rs.getObject("idresource_refered")!=null)
						idovreferedFile=rs.getObject("idresource_refered").toString();
					
					String name="";
					if(rs.getObject("name")!=null)
						name=rs.getObject("name").toString();
					
					
					if (idov!=null&&!idov.isEmpty()&&!name.isEmpty()&&!idovreferedFile.isEmpty())
						{
						Integer Idov=Integer.parseInt(idov);
						
						name=name.trim();
						name=StaticFunctionsOda.CleanStringFromDatabase(name,LColec);
						
						CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
						CompleteDocuments FileC=LColec.getCollection().getFilesC().get(idovreferedFile+"/"+name);
						
						if (OVirtual!=null&&FileC!=null)
						{
							
						
						if (preIdov!=null&&preIdov.intValue()==Idov.intValue())
							count++;
						else
							{
							if (count>MaxCount)
								MaxCount=count;
							preIdov=Idov;
							count=1;
							}
						
						
						boolean Visiblebool=true;
						if (Visible.equals("N"))
							Visiblebool=false;
						
						
						CompleteTextElement E=new CompleteTextElement(ID, id);
						E.getAmbitos().add(count);
						OVirtual.getDescription().add(E);
						
						LColec.getCollection().getFilesId().put(id,FileC);
						
						
						CompleteLinkElement E3=new CompleteLinkElement(AtributoMeta,FileC);
						E3.getAmbitos().add(count);
						OVirtual.getDescription().add(E3);
//						
//						MetaBooleanValue E2=new MetaBooleanValue(VISIBLE, Visiblebool);
//						E2.getAmbitos().add(count);
//						OVirtual.getDescription().add(E2);

						if (Visiblebool)
						{
							CompleteOperationalValue Valor=new CompleteOperationalValue(Valor2,Boolean.toString(Visiblebool));

							E3.getShows().add(Valor);
						}
					
						}
						else{
							if (OVirtual==null)
								LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");
							if (FileC==null)
								LColec.getLog().add("Warning: El recurso referencia al que apunta con referencia al objeto: Referencia : '" + idovreferedFile + "' y objeto : '" + name +"', no existe, Idrecurso: '"+id+ "'(ignorado)");
						}
						}
					else {
						if (idov==null||idov.isEmpty())
							LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
						if (name==null||name.isEmpty())
							LColec.getLog().add("Warning: Nombre recurso referencia asociado vacio, Idrecurso: '"+id+"' (ignorado)");
						if (idovreferedFile==null||idovreferedFile.isEmpty())
							LColec.getLog().add("Warning: Nombre objeto virtual referencia vacio, Idrecurso: '"+id+"' (ignorado)");
					}
				}
			IteradorPadre.setAmbitoSTotales(MaxCount);
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Procesa las instancias que corresponden a mis elementos
	 */
	private void OwnInstancesPropias() {
			try {
				ResultSet rs=LColec.getSQL().RunQuerrySELECT("SELECT * FROM resources where type='P' order by idov;");
				if (rs!=null) 
				{
					Integer preIdov=null;
					int MaxCount=0;
					int count=1;
					while (rs.next()) {
						
						String id=rs.getObject("id").toString();
						String idov=rs.getObject("idov").toString();
						
						String Visible="N";
						if(rs.getObject("visible")!=null)
							Visible=rs.getObject("visible").toString();
						
						String iconoOV="N";
						if(rs.getObject("iconoov")!=null)
							iconoOV=rs.getObject("iconoov").toString();
						
						String name="";
						if(rs.getObject("name")!=null)
							name=rs.getObject("name").toString();
						
						
						if (idov!=null&&!idov.isEmpty()&&!name.isEmpty())
							{
							Integer Idov=Integer.parseInt(idov);
							
							name=name.trim();
							name=StaticFunctionsOda.CleanStringFromDatabase(name,LColec);
							CompleteDocuments OVirtual=LColec.getCollection().getObjetoVirtual().get(Idov);
							CompleteFile FileActual=LColec.getCollection().getFiles().get(Idov+"/"+name);
							CompleteDocuments FileC=LColec.getCollection().getFilesC().get(Idov+"/"+name);
							
							if (OVirtual!=null&&FileC!=null)
							{
							if (preIdov!=null&&preIdov.intValue()==Idov.intValue())
								count++;
							else
								{
								if (count>MaxCount)
									MaxCount=count;
								preIdov=Idov;
								count=1;
								}
							
							
							boolean Visiblebool=true;
							if (Visible.equals("N"))
								Visiblebool=false;
							
							
							CompleteTextElement E=new CompleteTextElement(ID, id);
							
							LColec.getCollection().getFilesId().put(id,FileC);
							
							E.getAmbitos().add(count);
							OVirtual.getDescription().add(E);
							
							
							
							CompleteLinkElement E3=new CompleteLinkElement(AtributoMeta,FileC);
							E3.getAmbitos().add(count);
							OVirtual.getDescription().add(E3);			
						
							
							if (Visiblebool)
							{
								CompleteOperationalValue Valor=new CompleteOperationalValue(Valor2,Boolean.toString(Visiblebool));

								E3.getShows().add(Valor);
							}
							

							CompleteLinkElement FileValue=new CompleteLinkElement(Grammar_File.getOWNER(),OVirtual);
							
							FileC.getDescription().add(FileValue);
							
							if (iconoOV.equals("S"))
								OVirtual.setIcon(FileActual.getPath());

							
							}
							else {
								if (OVirtual==null)
									LColec.getLog().add("Warning: Objeto Virtual al que se asocia este recurso no existe, Id de objeto virtual : '" +Idov + "', Idrecurso: '"+id+ "'(ignorado)");
								if (FileC==null)
									LColec.getLog().add("Warning: El recurso propio al que apunta con referencia objeto : '" + name +"', no existe, Idrecurso: '"+id+ "'(ignorado)");	
							}
							}
						else {
							if (idov==null||idov.isEmpty())
								LColec.getLog().add("Warning: Objeto Virtual asociado al que se asocia el recurso es vacio, Idrecurso: '"+id+"' es vacio o el file referencia asociado es vacio (ignorado)");
							if (name==null||name.isEmpty())
								LColec.getLog().add("Warning: Nombre recurso propio asociado vacio, Idrecurso: '"+id+"' (ignorado)");

						}
					}
				IteradorPadre.setAmbitoSTotales(MaxCount);
				rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	/**
	 * @return the atributoMeta
	 */
	public CompleteLinkElementType getAtributoMeta() {
		return AtributoMeta;
	}

	/**
	 * @param atributoMeta the atributoMeta to set
	 */
	public void setAtributoMeta(CompleteLinkElementType atributoMeta) {
		AtributoMeta = atributoMeta;
	}
	
	

}
