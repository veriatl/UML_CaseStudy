package fr.emn.atlanmod.uml.casestudy.rewrite;

import com.google.common.base.Objects;
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
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class OCL2ATL {
  private static HashSet<String> wdSet = new HashSet<String>();
  
  public static String model = "UML";
  
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
                _builder.append("helper context ");
                _builder.append(OCL2ATL.model, "");
                _builder.append("!");
                String _name = clazz.getName();
                _builder.append(_name, "");
                _builder.append(" def: ");
                String _name_1 = inv.getName();
                _builder.append(_name_1, "");
                _builder.append("(): Boolean = ");
                _builder.newLineIfNotEmpty();
                _builder.append("  ");
                _builder.append(OCL2ATL.model, "  ");
                _builder.append("!");
                String _name_2 = clazz.getName();
                _builder.append(_name_2, "  ");
                _builder.append(".allInstances()->forAll(");
                String _name_3 = clazz.getName();
                String _genIteratorName = OCL2ATL.genIteratorName(_name_3);
                _builder.append(_genIteratorName, "  ");
                _builder.append(" |  ");
                String _name_4 = clazz.getName();
                String _genIteratorName_1 = OCL2ATL.genIteratorName(_name_4);
                EObject _put = OCL.bvMap.put(_genIteratorName_1, null);
                _builder.append(_put, "  ");
                _builder.newLineIfNotEmpty();
                {
                  for(final PropertyCallExp e : wdExprs) {
                    {
                      if ((OCL2ATL.printAtHere(e, OCL2ATL.genIteratorName(clazz.getName())) && (!OCL2ATL.wdSet.contains(OCL.gen(e))))) {
                        Object _xblockexpression = null;
                        {
                          String _gen = OCL.gen(e);
                          OCL2ATL.wdSet.add(_gen);
                          _xblockexpression = null;
                        }
                        _builder.append(_xblockexpression, "");
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
                                _builder.append(".allInstances()->contains(");
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
    return _builder.toString();
  }
  
  public static String genIteratorName(final String clazz) {
    String rtn = "";
    for (int i = 0; (i < clazz.length()); i++) {
      char _charAt = clazz.charAt(i);
      boolean _isUpperCase = Character.isUpperCase(_charAt);
      if (_isUpperCase) {
        String _rtn = rtn;
        char _charAt_1 = clazz.charAt(i);
        char _lowerCase = Character.toLowerCase(_charAt_1);
        rtn = (_rtn + Character.valueOf(_lowerCase));
      }
    }
    return rtn;
  }
  
  public static boolean printAtHere(final PropertyCallExp e, final String v) {
    boolean r = false;
    OCLExpression _ownedSource = e.getOwnedSource();
    if ((_ownedSource instanceof VariableExp)) {
      OCLExpression _ownedSource_1 = e.getOwnedSource();
      String _gen = OCL.gen(_ownedSource_1);
      boolean _equals = Objects.equal(_gen, v);
      if (_equals) {
        r = true;
      }
    } else {
      OCLExpression _ownedSource_2 = e.getOwnedSource();
      if ((_ownedSource_2 instanceof PropertyCallExp)) {
        OCLExpression _ownedSource_3 = e.getOwnedSource();
        boolean _printAtHere = OCL2ATL.printAtHere(((PropertyCallExp) _ownedSource_3), v);
        r = _printAtHere;
      }
    }
    return r;
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
