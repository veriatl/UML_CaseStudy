package fr.emn.atlanmod.uml.casestudy.rewrite;

import fr.emn.atlanmod.uml.casestudy.rewrite.OCL;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLProjector;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLWDGenerator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LanguageExpression;
import org.eclipse.ocl.pivot.Model;
import org.eclipse.ocl.pivot.NullLiteralExp;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class OCL2ATL {
  private static HashSet<String> wdSet = new HashSet<String>();
  
  public static String model = "UML";
  
  public static String modelReplacer = "UMLs";
  
  public static boolean postMode = false;
  
  protected static String _rewrite(final EObject o) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We don\'t understand ");
    EClass _eClass = o.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected static String _rewrite(final Model m) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<org.eclipse.ocl.pivot.Package> _ownedPackages = m.getOwnedPackages();
      for(final org.eclipse.ocl.pivot.Package pac : _ownedPackages) {
        String _rewrite = OCL2ATL.rewrite(pac);
        _builder.append(_rewrite, "");
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
    _builder.append(_xblockexpression, "");
    _builder.newLineIfNotEmpty();
    {
      List<org.eclipse.ocl.pivot.Class> _ownedClasses = p.getOwnedClasses();
      for(final org.eclipse.ocl.pivot.Class clazz : _ownedClasses) {
        {
          List<Constraint> _ownedInvariants = clazz.getOwnedInvariants();
          for(final Constraint inv : _ownedInvariants) {
            {
              if ((OCLProjector.proj(((ExpressionInOCL) inv.getOwnedSpecification()).getOwnedBody()) && (!(((ExpressionInOCL) inv.getOwnedSpecification()).getOwnedBody() instanceof NullLiteralExp)))) {
                LanguageExpression _ownedSpecification = inv.getOwnedSpecification();
                OCLExpression _ownedBody = ((ExpressionInOCL) _ownedSpecification).getOwnedBody();
                final HashSet<PropertyCallExp> wdExprs = OCLWDGenerator.wd(_ownedBody);
                _builder.newLineIfNotEmpty();
                _builder.append("--@pre");
                _builder.newLine();
                _builder.append("helper context ");
                _builder.append(OCL2ATL.model, "");
                _builder.append("!");
                String _name = clazz.getName();
                _builder.append(_name, "");
                _builder.append(" def: pre_");
                String _name_1 = inv.getName();
                _builder.append(_name_1, "");
                _builder.append("(): Boolean = --");
                String _name_2 = inv.getName();
                _builder.append(_name_2, "");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append(OCL2ATL.model, "  ");
                _builder.append("!");
                String _name_3 = clazz.getName();
                _builder.append(_name_3, "  ");
                _builder.append(".allInstances()->forAll(");
                String _name_4 = clazz.getName();
                String _genIteratorName = OCL.genIteratorName(_name_4);
                _builder.append(_genIteratorName, "  ");
                _builder.append(" |  ");
                String _name_5 = clazz.getName();
                String _genIteratorName_1 = OCL.genIteratorName(_name_5);
                EObject _put = OCL.bvMap.put(_genIteratorName_1, null);
                _builder.append(_put, "  ");
                _builder.newLineIfNotEmpty();
                {
                  for(final PropertyCallExp e : wdExprs) {
                    {
                      if ((OCL.printAtHere(e, OCL.genIteratorName(clazz.getName())) && (!OCL2ATL.wdSet.contains(OCL.gen(e))))) {
                        Object _xblockexpression_1 = null;
                        {
                          String _gen = OCL.gen(e);
                          OCL2ATL.wdSet.add(_gen);
                          _xblockexpression_1 = null;
                        }
                        _builder.append(_xblockexpression_1, "");
                        {
                          boolean _isPrimtive = OCL.isPrimtive(e);
                          boolean _not = (!_isPrimtive);
                          if (_not) {
                            {
                              boolean _isCollection = OCL.isCollection(e);
                              boolean _not_1 = (!_isCollection);
                              if (_not_1) {
                                _builder.newLineIfNotEmpty();
                                Type _type = e.getType();
                                String _string = _type.toString();
                                String _replace = _string.replace("::", "!");
                                _builder.append(_replace, "");
                                _builder.append(".allInstances()->includes(");
                                String _gen = OCL.gen(e);
                                _builder.append(_gen, "");
                                _builder.append(") implies ");
                                _builder.newLineIfNotEmpty();
                              } else {
                                String _gen_1 = OCL.gen(e);
                                _builder.append(_gen_1, "");
                                _builder.append("->size()>0 implies ");
                                _builder.newLineIfNotEmpty();
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
                _builder.append("    ");
                LanguageExpression _ownedSpecification_1 = inv.getOwnedSpecification();
                OCLExpression _ownedBody_1 = ((ExpressionInOCL) _ownedSpecification_1).getOwnedBody();
                String _gen_2 = OCL.gen(_ownedBody_1);
                _builder.append(_gen_2, "    ");
                _builder.newLineIfNotEmpty();
                _builder.append("); ");
                {
                  OCL2ATL.wdSet.clear();
                  OCL.bvMap.clear();
                }
                _builder.newLineIfNotEmpty();
                _builder.newLine();
              }
            }
          }
        }
      }
    }
    _builder.newLine();
    _builder.newLine();
    Object _xblockexpression_2 = null;
    {
      OCL2ATL.postMode = true;
      _xblockexpression_2 = null;
    }
    _builder.append(_xblockexpression_2, "");
    _builder.newLineIfNotEmpty();
    {
      List<org.eclipse.ocl.pivot.Class> _ownedClasses_1 = p.getOwnedClasses();
      for(final org.eclipse.ocl.pivot.Class clazz_1 : _ownedClasses_1) {
        {
          List<Constraint> _ownedInvariants_1 = clazz_1.getOwnedInvariants();
          for(final Constraint inv_1 : _ownedInvariants_1) {
            {
              if ((OCLProjector.proj(((ExpressionInOCL) inv_1.getOwnedSpecification()).getOwnedBody()) && (!(((ExpressionInOCL) inv_1.getOwnedSpecification()).getOwnedBody() instanceof NullLiteralExp)))) {
                LanguageExpression _ownedSpecification_2 = inv_1.getOwnedSpecification();
                OCLExpression _ownedBody_2 = ((ExpressionInOCL) _ownedSpecification_2).getOwnedBody();
                final HashSet<PropertyCallExp> wdExprs_1 = OCLWDGenerator.wd(_ownedBody_2);
                _builder.newLineIfNotEmpty();
                _builder.append("--@post");
                _builder.newLine();
                _builder.append("helper context ");
                String _xifexpression = null;
                if (OCL2ATL.postMode) {
                  _xifexpression = OCL2ATL.modelReplacer;
                } else {
                  _xifexpression = OCL2ATL.model;
                }
                _builder.append(_xifexpression, "");
                _builder.append("!");
                String _name_6 = clazz_1.getName();
                _builder.append(_name_6, "");
                _builder.append(" def: post_");
                String _name_7 = inv_1.getName();
                _builder.append(_name_7, "");
                _builder.append("(): Boolean = --");
                String _name_8 = clazz_1.getName();
                _builder.append(_name_8, "");
                _builder.append("_");
                String _name_9 = inv_1.getName();
                _builder.append(_name_9, "");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                String _xifexpression_1 = null;
                if (OCL2ATL.postMode) {
                  _xifexpression_1 = OCL2ATL.modelReplacer;
                } else {
                  _xifexpression_1 = OCL2ATL.model;
                }
                _builder.append(_xifexpression_1, "  ");
                _builder.append("!");
                String _name_10 = clazz_1.getName();
                _builder.append(_name_10, "  ");
                _builder.append(".allInstances()->forAll(");
                String _name_11 = clazz_1.getName();
                String _genIteratorName_2 = OCL.genIteratorName(_name_11);
                _builder.append(_genIteratorName_2, "  ");
                _builder.append(" |  ");
                String _name_12 = clazz_1.getName();
                String _genIteratorName_3 = OCL.genIteratorName(_name_12);
                EObject _put_1 = OCL.bvMap.put(_genIteratorName_3, null);
                _builder.append(_put_1, "  ");
                _builder.newLineIfNotEmpty();
                {
                  for(final PropertyCallExp e_1 : wdExprs_1) {
                    {
                      if ((OCL.printAtHere(e_1, OCL.genIteratorName(clazz_1.getName())) && (!OCL2ATL.wdSet.contains(OCL.gen(e_1))))) {
                        Object _xblockexpression_3 = null;
                        {
                          String _gen_3 = OCL.gen(e_1);
                          OCL2ATL.wdSet.add(_gen_3);
                          _xblockexpression_3 = null;
                        }
                        _builder.append(_xblockexpression_3, "");
                        {
                          boolean _isPrimtive_1 = OCL.isPrimtive(e_1);
                          boolean _not_2 = (!_isPrimtive_1);
                          if (_not_2) {
                            {
                              boolean _isCollection_1 = OCL.isCollection(e_1);
                              boolean _not_3 = (!_isCollection_1);
                              if (_not_3) {
                                _builder.newLineIfNotEmpty();
                                String _xifexpression_2 = null;
                                if (OCL2ATL.postMode) {
                                  Type _type_1 = e_1.getType();
                                  String _string_1 = _type_1.toString();
                                  String _replace_1 = _string_1.replace("::", "!");
                                  _xifexpression_2 = _replace_1.replace((OCL2ATL.model + "!"), (OCL2ATL.modelReplacer + "!"));
                                } else {
                                  Type _type_2 = e_1.getType();
                                  String _string_2 = _type_2.toString();
                                  _xifexpression_2 = _string_2.replace("::", "!");
                                }
                                _builder.append(_xifexpression_2, "");
                                _builder.append(".allInstances()->contains(");
                                String _gen_3 = OCL.gen(e_1);
                                _builder.append(_gen_3, "");
                                _builder.append(") implies ");
                                _builder.newLineIfNotEmpty();
                              } else {
                                String _gen_4 = OCL.gen(e_1);
                                _builder.append(_gen_4, "");
                                _builder.append("->size()>0 implies ");
                                _builder.newLineIfNotEmpty();
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
                _builder.append("    ");
                LanguageExpression _ownedSpecification_3 = inv_1.getOwnedSpecification();
                OCLExpression _ownedBody_3 = ((ExpressionInOCL) _ownedSpecification_3).getOwnedBody();
                String _gen_5 = OCL.gen(_ownedBody_3);
                _builder.append(_gen_5, "    ");
                _builder.newLineIfNotEmpty();
                _builder.append("); ");
                {
                  OCL2ATL.wdSet.clear();
                  OCL.bvMap.clear();
                }
                _builder.newLineIfNotEmpty();
                _builder.newLine();
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
