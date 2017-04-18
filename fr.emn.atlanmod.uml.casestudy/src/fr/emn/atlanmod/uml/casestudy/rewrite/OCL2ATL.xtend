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
"ClassifierTemplateParameter_has_constraining_classifier",
"ObjectFlow_is_multicast_or_is_multireceive",
"Parameter_stream_and_exception",
"State_submachine_states",
"AcceptCallAction_unmarshall",
"InformationItem_not_instantiable",
"ReadExtentAction_type_is_classifier",
"ActivityParameterNode_has_parameters",
"DecisionNode_decision_input_flow_incoming",
"ActivityParameterNode_same_type",
"InitialNode_no_incoming_edges",
"ActivityParameterNode_no_edges",
"JoinNode_one_outgoing_edge",
"FinalState_no_regions",
"CreateObjectAction_same_type",
"ReadLinkObjectEndQualifierAction_same_type",
"ExecutionSpecification_same_lifeline",
"Extend_extension_points",
"FinalState_no_outgoing_transitions",
"ForkNode_one_incoming_edge",
"AcceptEventAction_no_output_pins",
"MergeNode_one_outgoing_edge",
"ValuePin_no_incoming_edges",
"ReadLinkObjectEndAction_type_of_result",
"ExceptionHandler_handler_body_edges",
"State_submachine_or_regions",
"InitialNode_control_edges",
"Extension_is_binary",
"Property_derived_union_is_derived",
"FinalNode_no_outgoing_edges",
"ConditionalNode_no_input_pins",
"BehavioralFeature_abstract_no_method",
"Property_derived_union_is_read_only",
"Reception_same_name_as_signal",
"State_composite_states",
"ReadLinkObjectEndAction_type_of_object",
"InformationItem_has_no",
"Pin_control_pins",
"Pin_not_unique",
"AcceptEventAction_no_input_pins",
"Property_subsetted_property_names",
"CallAction_synchronous_call",
"ObjectFlow_no_executable_nodes",
"ExceptionHandler_handler_body_owner",
"Enumeration_immutable",
"ProtocolStateMachine_protocol_transitions",
"StringExpression_operands",
"TemplateBinding_parameter_substitution_formal",
"ActionInputPin_one_output_pin",
"Component_no_nested_classifiers",
"ActionInputPin_input_pin",
"LinkAction_not_static",
"CreateObjectAction_classifier_not_association_class",
"Node_internal_structure",
"StructuralFeatureAction_not_static",
"InformationFlow_convey_classifiers",
"State_destinations_or_sources_of_transitions",
"CommunicationPath_association_ends",
"ReadLinkObjectEndAction_ends_of_association",
"CreateObjectAction_classifier_not_abstract",
"Behavior_feature_of_context_classifier",
"DurationConstraint_has_one_or_two_constrainedElements",
"TimeConstraint_has_one_constrainedElement",
"Constraint_not_apply_to_self",
"ConsiderIgnoreFragment_type"
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