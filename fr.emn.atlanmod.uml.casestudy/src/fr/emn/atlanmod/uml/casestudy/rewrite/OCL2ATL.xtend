package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.ExpressionInOCL
import org.eclipse.ocl.pivot.Model
import org.eclipse.ocl.pivot.NullLiteralExp
import org.eclipse.ocl.pivot.Package
import java.util.Arrays

class OCL2ATL {
	
	static private HashSet<String> wdSet = new HashSet<String>
	static private String[] whiteList = #{
"Parameter_stream_and_exception",
"ObjectFlow_is_multicast_or_is_multireceive",
"State_submachine_states",
"InformationItem_not_instantiable",
"ValuePin_no_incoming_edges",
"JoinNode_one_outgoing_edge",
"MergeNode_one_outgoing_edge",
"FinalState_no_regions",
"InitialNode_no_incoming_edges",
"ForkNode_one_incoming_edge",
"FinalState_no_outgoing_transitions",
"InitialNode_control_edges",
"ActivityParameterNode_no_edges",
"State_submachine_or_regions",
"AcceptCallAction_unmarshall",
"Extension_is_binary",
"ConditionalNode_no_input_pins",
"Property_derived_union_is_derived",
"AcceptEventAction_no_output_pins",
"Reception_same_name_as_signal",
"FinalNode_no_outgoing_edges",
"Property_subsetted_property_names",
"Property_derived_union_is_read_only",
"DecisionNode_decision_input_flow_incoming",
"ClassifierTemplateParameter_has_constraining_classifier",
"State_composite_states",
"InformationItem_has_no",
"Enumeration_immutable",
"Pin_control_pins",
"Pin_not_unique",
"CallAction_synchronous_call",
"StringExpression_operands",
"AcceptEventAction_no_input_pins",
"ActivityParameterNode_has_parameters",
"Component_no_nested_classifiers",
"BehavioralFeature_abstract_no_method",
"Extend_extension_points",
"LinkAction_not_static",
"ExecutionSpecification_same_lifeline",
"CreateObjectAction_classifier_not_association_class",
"Node_internal_structure",
"ReadLinkObjectEndAction_ends_of_association",
"StructuralFeatureAction_not_static",
"CommunicationPath_association_ends",
"InformationFlow_convey_classifiers",
"ActionInputPin_input_pin",
"State_destinations_or_sources_of_transitions",
"Behavior_feature_of_context_classifier",
"ActionInputPin_one_output_pin",
"CreateObjectAction_classifier_not_abstract"
	}
	
	static public String model = "UML"
	static public String modelReplacer = "UMLs"
	static public boolean postMode = false;
	
	// dispatcher
	def static dispatch String rewrite(EObject o) '''
		// We don't understand «o.eClass.name»
	'''
	
	def static dispatch String rewrite(Model m) '''
	«FOR pac : m.ownedPackages»
		«rewrite(pac)»	
	«ENDFOR»
	'''
	
	/**
	 * ps: we don't generate helper if the invariant body is {@code null} or it has been filter out by the projector {@code OCLProjector}
	 */
	def static dispatch String rewrite(Package p) '''	
	«{postMode = false;null}»
	«FOR clazz : p.ownedClasses»
		«FOR inv : clazz.ownedInvariants»«var i=0»
			«IF Arrays.asList(whiteList).contains(String.format("%s_%s", clazz.name, inv.name))»
				«IF (OCLProjector.proj((inv.ownedSpecification as ExpressionInOCL).ownedBody)
					&& !((inv.ownedSpecification as ExpressionInOCL).ownedBody instanceof NullLiteralExp)
				)»«
				val wdExprs = OCLWDGenerator.wd((inv.ownedSpecification as ExpressionInOCL).ownedBody)
				»
				--@pre
				helper context «model»!«clazz.name» def: pre_«inv.name»(): Boolean = --«inv.name»
				  «model»!«clazz.name».allInstances()->forAll(«OCL.genIteratorName(clazz.name)» |  «OCL.bvMap.put(OCL.genIteratorName(clazz.name), null)»
				  	«FOR e: wdExprs»
					  	«IF OCL.printAtHere(e, OCL.genIteratorName(clazz.name)) && !wdSet.contains(OCL.gen(e))»«
					  		{wdSet.add(OCL.gen(e));null}»«
					  		IF !OCL.isPrimtive(e)»«{i=i+1;null}»«
					  			IF !OCL.isCollection(e)»
					  			«e.type.toString().replace("::", "!")».allInstances()->includes(«OCL.gen(e)») implies (
					  			«ELSE»
					  			(«OCL.gen(e)»->size()>0 or «OCL.gen(e)»->size()=0) implies (
					  			«ENDIF»«
					  		ENDIF»«
					  	ENDIF»«
				  	ENDFOR»
				    «OCL.gen((inv.ownedSpecification as ExpressionInOCL).ownedBody)»
				    «FOR e: {0..<i}»)«ENDFOR»
				); «{wdSet.clear(); OCL.bvMap.clear()}»
				
				«ENDIF»
			«ENDIF»
		«ENDFOR»
	«ENDFOR»
	
	
	«{postMode = true;null}»
	«FOR clazz : p.ownedClasses»
		«FOR inv : clazz.ownedInvariants»«var i=0»
			«IF Arrays.asList(whiteList).contains(String.format("%s_%s", clazz.name, inv.name))»
				«IF (OCLProjector.proj((inv.ownedSpecification as ExpressionInOCL).ownedBody)
					&& !((inv.ownedSpecification as ExpressionInOCL).ownedBody instanceof NullLiteralExp)
				)»«
				val wdExprs = OCLWDGenerator.wd((inv.ownedSpecification as ExpressionInOCL).ownedBody)
				»
				--@post
				helper context «if (postMode) modelReplacer else model»!«clazz.name» def: post_«inv.name»(): Boolean = --«clazz.name»_«inv.name»
				  «if (postMode) modelReplacer else model»!«clazz.name».allInstances()->forAll(«OCL.genIteratorName(clazz.name)» |  «OCL.bvMap.put(OCL.genIteratorName(clazz.name), null)»
				  	«FOR e: wdExprs»
					  	«IF OCL.printAtHere(e, OCL.genIteratorName(clazz.name)) && !wdSet.contains(OCL.gen(e))»«
					  		{wdSet.add(OCL.gen(e));null}»«
					  		IF !OCL.isPrimtive(e)»«{i=i+1;null}»«
					  			IF !OCL.isCollection(e)»
					  			«if (postMode) e.type.toString().replace("::", "!").replace(model+"!", modelReplacer+"!") else e.type.toString().replace("::", "!")».allInstances()->includes(«OCL.gen(e)») implies (
					  			«ELSE»
					  			(«OCL.gen(e)»->size()>0 or «OCL.gen(e)»->size()=0) implies (
					  			«ENDIF»«
					  		ENDIF»«
					  	ENDIF»«
				  	ENDFOR»
				    «OCL.gen((inv.ownedSpecification as ExpressionInOCL).ownedBody)»
				    «FOR e: {0..<i}»)«ENDFOR»
				); «{wdSet.clear(); OCL.bvMap.clear()}»
				
				«ENDIF»
			«ENDIF»
		«ENDFOR»
	«ENDFOR»
	'''
	

	
	

	

	
}