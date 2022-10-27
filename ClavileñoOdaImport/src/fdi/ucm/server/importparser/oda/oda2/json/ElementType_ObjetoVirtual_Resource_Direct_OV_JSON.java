package fdi.ucm.server.importparser.oda.oda2.json;

import org.json.simple.JSONObject;

import fdi.ucm.server.importparser.oda.coleccion.LoadCollectionOda;
import fdi.ucm.server.importparser.oda.oda2.direct.collection.categoria.ElementType_ObjetoVirtual_Resource_Direct_OV;
import fdi.ucm.server.modelComplete.collection.grammar.CompleteGrammar;

public class ElementType_ObjetoVirtual_Resource_Direct_OV_JSON extends ElementType_ObjetoVirtual_Resource_Direct_OV {

	public ElementType_ObjetoVirtual_Resource_Direct_OV_JSON(CompleteGrammar I,
			LoadCollectionOda L, JSONObject jSONGeneral) {
		super(I, L);
	}

	@Override
	public void ProcessInstances() {
		// TODO Auto-generated method stub
	}
	
}
