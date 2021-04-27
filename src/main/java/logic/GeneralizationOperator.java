package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import IOHelper.OntologyHelper;
import IOHelper.TaxonomyHelper;
import Main.TransferProcess;
import OWLImpl.WorkflowImpl;
import interfaces.NodeInterface;

public class GeneralizationOperator {
	
	public List<WorkflowImpl> workflows = new ArrayList<WorkflowImpl>();

	public GeneralizationOperator(OntologyHelper srcOntology) {
		// TODO Auto-generated constructor stub
	
			HashMap<String, String> nodesToWorkflow = srcOntology.partOf;
			for(String key : nodesToWorkflow.keySet()) {
				//key is the node description
				//value is the workflow name but in form of "http://owl.api ...." 
				//so we need to simplify the workflow name value at line 51 
				System.out.println(key+" belongs to "+nodesToWorkflow.get(key));
			}
			Collection<String> workflowNames = new HashSet<String>(nodesToWorkflow.values());

				for(String name : workflowNames) {
					WorkflowImpl workflow = new WorkflowImpl();
					for(NodeInterface node:OntologyHelper.sourceOntologyHelper.nodes) {
						String key =  "http://owl.api.wf#"+node.getSemanticDescription()+":"+node.getId();
						if(nodesToWorkflow.containsKey(key)&&nodesToWorkflow.get(key).contentEquals(name)) {
							workflow.nodes.add(node);
						}
					}
					name = name.split("#")[1].split(":")[0];
					workflow.semanticDescription = name;
					workflows.add(workflow);
				}
				
				// generalize each node one step up.
				for(WorkflowImpl w: workflows){
					
					for(NodeInterface n: w.nodes) {
						if(n.isTask()) {
							OWLNamedIndividual nodeOWLNamedIndividual = TaxonomyHelper.sourceTaxonomy.getOWLNamedIndividualOf(n);
							OWLClass oneStepAncestor = TaxonomyHelper.sourceTaxonomy.getParentOf(nodeOWLNamedIndividual);
							System.out.println("Leaf Node description:");
							System.out.println(nodeOWLNamedIndividual.getIRI().getFragment());
							System.out.println("Its parent node description:");
							System.out.println(oneStepAncestor.getIRI().getFragment());
							//Generalization: replace leaf node description with its parent node description
							n.setSemanticDescription(oneStepAncestor.getIRI().getFragment());
						}
					}
					// output each workflow w into svg file
					System.out.println(w.semanticDescription);
					w.exportTo("./generalization/");
				}
	}

	public static void main(String args[]) throws IOException {
		 OntologyHelper.sourceOntologyHelper = new OntologyHelper("./Evaluation/data sets/EVER 2"
			 		+ "/airport handling of lugguage/Ontologie_Flughafen_all_ID.owl",
			 		TaxonomyHelper.sourceTaxonomy);
		 OntologyHelper.targetOntologyHelper = new OntologyHelper("./Evaluation/data sets/EVER 2/SAP warehouse management/Ontologie_SAP_all_ID.owl",TaxonomyHelper.targetTaxonomy);

		 List<WorkflowImpl> workflows = new ArrayList<WorkflowImpl>();
			OntologyHelper srcOntology = OntologyHelper.sourceOntologyHelper;
			
			HashMap<String, String> nodesToWorkflow = srcOntology.partOf;
			for(String key : nodesToWorkflow.keySet()) {
				//key is the node description
				//value is the workflow name but in form of "http://owl.api ...." 
				//so we need to simplify the workflow name value at line 51 
				System.out.println(key+" belongs to "+nodesToWorkflow.get(key));
			}
			Collection<String> workflowNames = new HashSet<String>(nodesToWorkflow.values());

				for(String name : workflowNames) {
					WorkflowImpl workflow = new WorkflowImpl();
					for(NodeInterface node:OntologyHelper.sourceOntologyHelper.nodes) {
						String key =  "http://owl.api.wf#"+node.getSemanticDescription()+":"+node.getId();
						if(nodesToWorkflow.containsKey(key)&&nodesToWorkflow.get(key).contentEquals(name)) {
							workflow.nodes.add(node);
						}
					}
					name = name.split("#")[1].split(":")[0];
					workflow.semanticDescription = name;
					workflows.add(workflow);
				}
				
				// generalize each node one step up.
				for(WorkflowImpl w: workflows){
					
					for(NodeInterface n: w.nodes) {
						if(n.isTask()) {
							OWLNamedIndividual nodeOWLNamedIndividual = TaxonomyHelper.sourceTaxonomy.getOWLNamedIndividualOf(n);
							OWLClass oneStepAncestor = TaxonomyHelper.sourceTaxonomy.getParentOf(nodeOWLNamedIndividual);
							System.out.println("Leaf Node description:");
							System.out.println(nodeOWLNamedIndividual.getIRI().getFragment());
							System.out.println("Its parent node description:");
							System.out.println(oneStepAncestor.getIRI().getFragment());
							//Generalization: replace leaf node description with its parent node description
							n.setSemanticDescription(oneStepAncestor.getIRI().getFragment());
						}
					}
					// output each workflow w into svg file
					System.out.println(w.semanticDescription);
					w.exportTo("./generalization/");
				}
				
				
	}
}
