package fr.emn.atlanmod.uml.casestudy.rewrite;

import fr.emn.atlanmod.uml.casestudy.rewrite.OCL;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLProjector;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLWDGenerator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LanguageExpression;
import org.eclipse.ocl.pivot.Model;
import org.eclipse.ocl.pivot.NullLiteralExp;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class OCL2ATL {
  private static HashSet<String> wdSet = new HashSet<String>();
  
  private static String[] whiteList = ((String[])Conversions.unwrapArray(Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("ClassifierTemplateParameter_has_constraining_classifier", "ObjectFlow_is_multicast_or_is_multireceive", "Parameter_stream_and_exception", "State_submachine_states", "AcceptCallAction_unmarshall", "InformationItem_not_instantiable", "ReadExtentAction_type_is_classifier", "ActivityParameterNode_has_parameters", "DecisionNode_decision_input_flow_incoming", "ActivityParameterNode_same_type", "InitialNode_no_incoming_edges", "ActivityParameterNode_no_edges", "JoinNode_one_outgoing_edge", "FinalState_no_regions", "CreateObjectAction_same_type", "ReadLinkObjectEndQualifierAction_same_type", "ExecutionSpecification_same_lifeline", "Extend_extension_points", "FinalState_no_outgoing_transitions", "ForkNode_one_incoming_edge", "AcceptEventAction_no_output_pins", "MergeNode_one_outgoing_edge", "ValuePin_no_incoming_edges", "ReadLinkObjectEndAction_type_of_result", "ExceptionHandler_handler_body_edges", "State_submachine_or_regions", "InitialNode_control_edges", "Extension_is_binary", "Property_derived_union_is_derived", "FinalNode_no_outgoing_edges", "ConditionalNode_no_input_pins", "BehavioralFeature_abstract_no_method", "Property_derived_union_is_read_only", "Reception_same_name_as_signal", "State_composite_states", "ReadLinkObjectEndAction_type_of_object", "InformationItem_has_no", "Pin_control_pins", "Pin_not_unique", "AcceptEventAction_no_input_pins", "Property_subsetted_property_names", "CallAction_synchronous_call", "ObjectFlow_no_executable_nodes", "ExceptionHandler_handler_body_owner", "Enumeration_immutable", "ProtocolStateMachine_protocol_transitions", "StringExpression_operands", "TemplateBinding_parameter_substitution_formal", "ActionInputPin_one_output_pin", "Component_no_nested_classifiers", "ActionInputPin_input_pin", "LinkAction_not_static", "CreateObjectAction_classifier_not_association_class", "Node_internal_structure", "StructuralFeatureAction_not_static", "InformationFlow_convey_classifiers", "State_destinations_or_sources_of_transitions", "CommunicationPath_association_ends", "ReadLinkObjectEndAction_ends_of_association", "CreateObjectAction_classifier_not_abstract", "Behavior_feature_of_context_classifier", "DurationConstraint_has_one_or_two_constrainedElements", "TimeConstraint_has_one_constrainedElement", "Constraint_not_apply_to_self", "ConsiderIgnoreFragment_type")), String.class));
  
  public static String model = "UML";
  
  public static String modelReplacer = "UMLs";
  
  public static boolean postMode = false;
  
  protected static String _rewrite(final EObject o) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We don\'t understand ");
    String _name = o.eClass().getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected static String _rewrite(final Model m) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<org.eclipse.ocl.pivot.Package> _ownedPackages = m.getOwnedPackages();
      for(final org.eclipse.ocl.pivot.Package pac : _ownedPackages) {
        String _rewrite = OCL2ATL.rewrite(pac);
        _builder.append(_rewrite);
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder.toString();
  }
  
  /**
   * ps: we don't generate helper if the invariant body is {@code null} or it has been filter out by the projector {@code OCLProjector}
   */
  protected static String _rewrite(final org.eclipse.ocl.pivot.Package p) {
    StringConcatenation _builder = new StringConcatenation();
    Object _xblockexpression = null;
    {
      OCL2ATL.postMode = false;
      _xblockexpression = null;
    }
    _builder.append(_xblockexpression);
    _builder.newLineIfNotEmpty();
    {
      List<org.eclipse.ocl.pivot.Class> _ownedClasses = p.getOwnedClasses();
      for(final org.eclipse.ocl.pivot.Class clazz : _ownedClasses) {
        {
          List<Constraint> _ownedInvariants = clazz.getOwnedInvariants();
          for(final Constraint inv : _ownedInvariants) {
            int i = 0;
            _builder.newLineIfNotEmpty();
            {
              boolean _contains = Arrays.<String>asList(OCL2ATL.whiteList).contains(String.format("%s_%s", clazz.getName(), inv.getName()));
              if (_contains) {
                _builder.append("\t");
                {
                  if ((OCLProjector.proj(((ExpressionInOCL) inv.getOwnedSpecification()).getOwnedBody()) && (!(((ExpressionInOCL) inv.getOwnedSpecification()).getOwnedBody() instanceof NullLiteralExp)))) {
                    LanguageExpression _ownedSpecification = inv.getOwnedSpecification();
                    final HashSet<PropertyCallExp> wdExprs = OCLWDGenerator.wd(((ExpressionInOCL) _ownedSpecification).getOwnedBody());
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("--@pre");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("helper context ");
                    _builder.append(OCL2ATL.model, "\t");
                    _builder.append("!");
                    String _name = clazz.getName();
                    _builder.append(_name, "\t");
                    _builder.append(" def: pre_");
                    String _name_1 = inv.getName();
                    _builder.append(_name_1, "\t");
                    _builder.append("(): Boolean = --");
                    String _name_2 = inv.getName();
                    _builder.append(_name_2, "\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("  ");
                    _builder.append(OCL2ATL.model, "\t  ");
                    _builder.append("!");
                    String _name_3 = clazz.getName();
                    _builder.append(_name_3, "\t  ");
                    _builder.append(".allInstances()->forAll(");
                    String _genIteratorName = OCL.genIteratorName(clazz.getName());
                    _builder.append(_genIteratorName, "\t  ");
                    _builder.append(" |  ");
                    EObject _put = OCL.bvMap.put(OCL.genIteratorName(clazz.getName()), null);
                    _builder.append(_put, "\t  ");
                    _builder.newLineIfNotEmpty();
                    {
                      for(final PropertyCallExp e : wdExprs) {
                        {
                          if ((OCL.printAtHere(e, OCL.genIteratorName(clazz.getName())) && (!OCL2ATL.wdSet.contains(OCL.gen(e))))) {
                            Object _xblockexpression_1 = null;
                            {
                              OCL2ATL.wdSet.add(OCL.gen(e));
                              _xblockexpression_1 = null;
                            }
                            _builder.append(_xblockexpression_1);
                            {
                              boolean _isPrimtive = OCL.isPrimtive(e);
                              boolean _not = (!_isPrimtive);
                              if (_not) {
                                Object _xblockexpression_2 = null;
                                {
                                  i = (i + 1);
                                  _xblockexpression_2 = null;
                                }
                                _builder.append(_xblockexpression_2);
                                {
                                  boolean _isCollection = OCL.isCollection(e);
                                  boolean _not_1 = (!_isCollection);
                                  if (_not_1) {
                                    _builder.newLineIfNotEmpty();
                                    String _replace = e.getType().toString().replace("::", "!");
                                    _builder.append(_replace);
                                    _builder.append(".allInstances()->includes(");
                                    String _gen = OCL.gen(e);
                                    _builder.append(_gen);
                                    _builder.append(") implies (");
                                    _builder.newLineIfNotEmpty();
                                  } else {
                                    String _gen_1 = OCL.gen(e);
                                    _builder.append(_gen_1);
                                    _builder.append("->size()>0 implies (");
                                    _builder.newLineIfNotEmpty();
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                    _builder.append("\t");
                    _builder.append("    ");
                    LanguageExpression _ownedSpecification_1 = inv.getOwnedSpecification();
                    String _gen_2 = OCL.gen(((ExpressionInOCL) _ownedSpecification_1).getOwnedBody());
                    _builder.append(_gen_2, "\t    ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("    ");
                    {
                      for(final Integer e_1 : new ExclusiveRange(0, i, true)) {
                        _builder.append(")");
                      }
                    }
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("); ");
                    {
                      OCL2ATL.wdSet.clear();
                      OCL.bvMap.clear();
                    }
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.newLine();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    _builder.newLine();
    Object _xblockexpression_3 = null;
    {
      OCL2ATL.postMode = true;
      _xblockexpression_3 = null;
    }
    _builder.append(_xblockexpression_3);
    _builder.newLineIfNotEmpty();
    {
      List<org.eclipse.ocl.pivot.Class> _ownedClasses_1 = p.getOwnedClasses();
      for(final org.eclipse.ocl.pivot.Class clazz_1 : _ownedClasses_1) {
        {
          List<Constraint> _ownedInvariants_1 = clazz_1.getOwnedInvariants();
          for(final Constraint inv_1 : _ownedInvariants_1) {
            int i_1 = 0;
            _builder.newLineIfNotEmpty();
            {
              boolean _contains_1 = Arrays.<String>asList(OCL2ATL.whiteList).contains(String.format("%s_%s", clazz_1.getName(), inv_1.getName()));
              if (_contains_1) {
                _builder.append("\t");
                {
                  if ((OCLProjector.proj(((ExpressionInOCL) inv_1.getOwnedSpecification()).getOwnedBody()) && (!(((ExpressionInOCL) inv_1.getOwnedSpecification()).getOwnedBody() instanceof NullLiteralExp)))) {
                    LanguageExpression _ownedSpecification_2 = inv_1.getOwnedSpecification();
                    final HashSet<PropertyCallExp> wdExprs_1 = OCLWDGenerator.wd(((ExpressionInOCL) _ownedSpecification_2).getOwnedBody());
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("--@post");
                    _builder.newLine();
                    _builder.append("\t");
                    _builder.append("helper context ");
                    String _xifexpression = null;
                    if (OCL2ATL.postMode) {
                      _xifexpression = OCL2ATL.modelReplacer;
                    } else {
                      _xifexpression = OCL2ATL.model;
                    }
                    _builder.append(_xifexpression, "\t");
                    _builder.append("!");
                    String _name_4 = clazz_1.getName();
                    _builder.append(_name_4, "\t");
                    _builder.append(" def: post_");
                    String _name_5 = inv_1.getName();
                    _builder.append(_name_5, "\t");
                    _builder.append("(): Boolean = --");
                    String _name_6 = clazz_1.getName();
                    _builder.append(_name_6, "\t");
                    _builder.append("_");
                    String _name_7 = inv_1.getName();
                    _builder.append(_name_7, "\t");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("  ");
                    String _xifexpression_1 = null;
                    if (OCL2ATL.postMode) {
                      _xifexpression_1 = OCL2ATL.modelReplacer;
                    } else {
                      _xifexpression_1 = OCL2ATL.model;
                    }
                    _builder.append(_xifexpression_1, "\t  ");
                    _builder.append("!");
                    String _name_8 = clazz_1.getName();
                    _builder.append(_name_8, "\t  ");
                    _builder.append(".allInstances()->forAll(");
                    String _genIteratorName_1 = OCL.genIteratorName(clazz_1.getName());
                    _builder.append(_genIteratorName_1, "\t  ");
                    _builder.append(" |  ");
                    EObject _put_1 = OCL.bvMap.put(OCL.genIteratorName(clazz_1.getName()), null);
                    _builder.append(_put_1, "\t  ");
                    _builder.newLineIfNotEmpty();
                    {
                      for(final PropertyCallExp e_2 : wdExprs_1) {
                        {
                          if ((OCL.printAtHere(e_2, OCL.genIteratorName(clazz_1.getName())) && (!OCL2ATL.wdSet.contains(OCL.gen(e_2))))) {
                            Object _xblockexpression_4 = null;
                            {
                              OCL2ATL.wdSet.add(OCL.gen(e_2));
                              _xblockexpression_4 = null;
                            }
                            _builder.append(_xblockexpression_4);
                            {
                              boolean _isPrimtive_1 = OCL.isPrimtive(e_2);
                              boolean _not_2 = (!_isPrimtive_1);
                              if (_not_2) {
                                Object _xblockexpression_5 = null;
                                {
                                  i_1 = (i_1 + 1);
                                  _xblockexpression_5 = null;
                                }
                                _builder.append(_xblockexpression_5);
                                {
                                  boolean _isCollection_1 = OCL.isCollection(e_2);
                                  boolean _not_3 = (!_isCollection_1);
                                  if (_not_3) {
                                    _builder.newLineIfNotEmpty();
                                    String _xifexpression_2 = null;
                                    if (OCL2ATL.postMode) {
                                      _xifexpression_2 = e_2.getType().toString().replace("::", "!").replace((OCL2ATL.model + "!"), (OCL2ATL.modelReplacer + "!"));
                                    } else {
                                      _xifexpression_2 = e_2.getType().toString().replace("::", "!");
                                    }
                                    _builder.append(_xifexpression_2);
                                    _builder.append(".allInstances()->includes(");
                                    String _gen_3 = OCL.gen(e_2);
                                    _builder.append(_gen_3);
                                    _builder.append(") implies (");
                                    _builder.newLineIfNotEmpty();
                                  } else {
                                    String _gen_4 = OCL.gen(e_2);
                                    _builder.append(_gen_4);
                                    _builder.append("->size()>0 implies (");
                                    _builder.newLineIfNotEmpty();
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                    _builder.append("\t");
                    _builder.append("    ");
                    LanguageExpression _ownedSpecification_3 = inv_1.getOwnedSpecification();
                    String _gen_5 = OCL.gen(((ExpressionInOCL) _ownedSpecification_3).getOwnedBody());
                    _builder.append(_gen_5, "\t    ");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("    ");
                    {
                      for(final Integer e_3 : new ExclusiveRange(0, i_1, true)) {
                        _builder.append(")");
                      }
                    }
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.append("); ");
                    {
                      OCL2ATL.wdSet.clear();
                      OCL.bvMap.clear();
                    }
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t");
                    _builder.newLine();
                  }
                }
              }
            }
          }
        }
      }
    }
    return _builder.toString();
  }
  
  public static String rewrite(final EObject m) {
    if (m instanceof Model) {
      return _rewrite((Model)m);
    } else if (m instanceof org.eclipse.ocl.pivot.Package) {
      return _rewrite((org.eclipse.ocl.pivot.Package)m);
    } else if (m != null) {
      return _rewrite(m);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(m).toString());
    }
  }
}
