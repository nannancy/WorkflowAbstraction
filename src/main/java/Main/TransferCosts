package Main;

import java.io.IOException;

import IOHelper.OntologyHelper;
import IOHelper.TaxonomyHelper;
import logic.GeneralizationOperator;
import logic.SequenceOperator;

public class Transfer_costs {

	public Transfer_costs() {
		// TODO Auto-generated constructor stub
		

	}
	public static void main(String args[]) throws IOException {
		 OntologyHelper.sourceOntologyHelper = new OntologyHelper("./Evaluation/data sets/EVER 2"
			 		+ "/airport handling of lugguage/Ontologie_Flughafen_all_ID.owl",
			 		TaxonomyHelper.sourceTaxonomy);
		 OntologyHelper.targetOntologyHelper = new OntologyHelper("./Evaluation/data sets/EVER 2/SAP warehouse management/Ontologie_SAP_all_ID.owl",TaxonomyHelper.targetTaxonomy);

		GeneralizationOperator genOperator = new GeneralizationOperator(OntologyHelper.sourceOntologyHelper);
		
		SequenceOperator sequenceOperator = new SequenceOperator(OntologyHelper.sourceOntologyHelper);
		
		sequenceOperator.onOntology("./test/", genOperator.workflows);
	}

}
