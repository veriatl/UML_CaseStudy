package fr.emn.atlanmod.uml.casestudy.rewrite;

import com.google.common.base.Objects;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCL2ATL;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLWDGenerator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.BooleanLiteralExp;
import org.eclipse.ocl.pivot.CollectionLiteralExp;
import org.eclipse.ocl.pivot.EnumLiteralExp;
import org.eclipse.ocl.pivot.Enumeration;
import org.eclipse.ocl.pivot.EnumerationLiteral;
import org.eclipse.ocl.pivot.IfExp;
import org.eclipse.ocl.pivot.IntegerLiteralExp;
import org.eclipse.ocl.pivot.Iteration;
import org.eclipse.ocl.pivot.IteratorExp;
import org.eclipse.ocl.pivot.IteratorVariable;
import org.eclipse.ocl.pivot.LetExp;
import org.eclipse.ocl.pivot.NullLiteralExp;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.Operation;
import org.eclipse.ocl.pivot.OperationCallExp;
import org.eclipse.ocl.pivot.OppositePropertyCallExp;
import org.eclipse.ocl.pivot.Property;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.ocl.pivot.TypeExp;
import org.eclipse.ocl.pivot.UnlimitedNaturalLiteralExp;
import org.eclipse.ocl.pivot.Variable;
import org.eclipse.ocl.pivot.VariableDeclaration;
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class OCL {
  private static HashSet<String> wdSet = new HashSet<String>();
  
  protected static String _gen(final EObject e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We dont understand ");
    EClass _eClass = e.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final OCLExpression e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We dont understand ocl expression ");
    EClass _eClass = e.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final OperationCallExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    Operation _referredOperation = e.getReferredOperation();
    String _name = _referredOperation.getName();
    boolean _equals = Objects.equal(_name, "");
    if (_equals) {
      _xifexpression = e.getName();
    } else {
      Operation _referredOperation_1 = e.getReferredOperation();
      _xifexpression = _referredOperation_1.getName();
    }
    final String op = _xifexpression;
    _builder.newLineIfNotEmpty();
    OCLExpression _ownedSource = e.getOwnedSource();
    final String src = OCL.gen(_ownedSource, consistency);
    _builder.newLineIfNotEmpty();
    String _xifexpression_1 = null;
    if (((e.getOwnedArguments().size() != 0) && (!Objects.equal(op, null)))) {
      List<OCLExpression> _ownedArguments = e.getOwnedArguments();
      final Function1<OCLExpression, String> _function = (OCLExpression arg) -> {
        return OCL.gen(arg, consistency);
      };
      List<String> _map = ListExtensions.<OCLExpression, String>map(_ownedArguments, _function);
      _xifexpression_1 = IterableExtensions.join(_map, op);
    } else {
      _xifexpression_1 = "";
    }
    final String args = _xifexpression_1;
    _builder.newLineIfNotEmpty();
    String _xifexpression_2 = null;
    if (((e.getOwnedArguments().size() != 0) && (!Objects.equal(op, null)))) {
      List<OCLExpression> _ownedArguments_1 = e.getOwnedArguments();
      final Function1<OCLExpression, String> _function_1 = (OCLExpression arg) -> {
        return OCL.gen(arg, consistency);
      };
      List<String> _map_1 = ListExtensions.<OCLExpression, String>map(_ownedArguments_1, _function_1);
      _xifexpression_2 = IterableExtensions.join(_map_1, ",");
    } else {
      _xifexpression_2 = "";
    }
    final String args_dot = _xifexpression_2;
    {
      if ((Objects.equal(op, "not") || Objects.equal(op, "abs"))) {
        _builder.append(op, "");
        _builder.append("(");
        OCLExpression _ownedSource_1 = e.getOwnedSource();
        String _gen = OCL.gen(_ownedSource_1, consistency);
        _builder.append(_gen, "");
        _builder.append(")");
      } else {
        if ((((((((((((((((Objects.equal(op, "+") || Objects.equal(op, "-")) || Objects.equal(op, "*")) || Objects.equal(op, "/")) || Objects.equal(op, "=")) || Objects.equal(op, "<>")) || Objects.equal(op, ">")) || Objects.equal(op, "<")) || Objects.equal(op, ">=")) || Objects.equal(op, "<=")) || Objects.equal(op, "implies")) || Objects.equal(op, "and")) || Objects.equal(op, "or")) || Objects.equal(op, "div")) || Objects.equal(op, "mod")) || Objects.equal(op, "xor"))) {
          _builder.append(src, "");
          _builder.append(" ");
          _builder.append(op, "");
          _builder.append(" ");
          _builder.append(args, "");
        } else {
          if ((((((((((((((((Objects.equal(op, "size") || Objects.equal(op, "flatten")) || Objects.equal(op, "allInstances")) || Objects.equal(op, "asBag")) || Objects.equal(op, "asOrderedSet")) || Objects.equal(op, "asSequence")) || Objects.equal(op, "asSet")) || Objects.equal(op, "isEmpty")) || Objects.equal(op, "max")) || Objects.equal(op, "min")) || Objects.equal(op, "notEmpty")) || Objects.equal(op, "oclIsUndefined")) || Objects.equal(op, "oclType")) || Objects.equal(op, "first")) || Objects.equal(op, "last")) || Objects.equal(op, "oclAsSet"))) {
            _builder.append(src, "");
            _builder.append("->");
            _builder.append(op, "");
            _builder.append("()");
          } else {
            if (((((((((Objects.equal(op, "oclIsUndefined") || Objects.equal(op, "oclIsInvalid")) || Objects.equal(op, "toBoolean")) || Objects.equal(op, "toInteger")) || Objects.equal(op, "toLower")) || Objects.equal(op, "toLowerCase")) || Objects.equal(op, "toString")) || Objects.equal(op, "toUpper")) || Objects.equal(op, "toUpperCase"))) {
              _builder.append(src, "");
              _builder.append(".");
              _builder.append(op, "");
              _builder.append("()");
            } else {
              if ((((((((((Objects.equal(op, "oclAsType") || Objects.equal(op, "oclIsKindOf")) || Objects.equal(op, "oclIsTypeOf")) || Objects.equal(op, "concat")) || Objects.equal(op, "endsWith")) || Objects.equal(op, "indexOf")) || Objects.equal(op, "lastIndexOf")) || Objects.equal(op, "startsWith")) || Objects.equal(op, "substring")) || Objects.equal(op, "conformsTo"))) {
                _builder.append(src, "");
                _builder.append(".");
                _builder.append(op, "");
                _builder.append("(");
                _builder.append(args_dot, "");
                _builder.append(")");
              } else {
                if (((((((((((((((((((((Objects.equal(op, "excluding") || Objects.equal(op, "excludingAll")) || Objects.equal(op, "including")) || Objects.equal(op, "includingAll")) || Objects.equal(op, "selectByKind")) || Objects.equal(op, "selectByKind")) || Objects.equal(op, "selectByType")) || Objects.equal(op, "count")) || Objects.equal(op, "excludes")) || Objects.equal(op, "includes")) || Objects.equal(op, "includesAll")) || Objects.equal(op, "intersection")) || Objects.equal(op, "at")) || Objects.equal(op, "append")) || Objects.equal(op, "appendAll")) || Objects.equal(op, "prepend")) || Objects.equal(op, "prependAll")) || Objects.equal(op, "reverse")) || Objects.equal(op, "union")) || Objects.equal(op, "is")) || Objects.equal(op, "excludesAll"))) {
                  _builder.append(src, "");
                  _builder.append("->");
                  _builder.append(op, "");
                  _builder.append("(");
                  _builder.append(args_dot, "");
                  _builder.append(")");
                } else {
                  _builder.append(src, "");
                  _builder.append(".");
                  _builder.append(op, "");
                  _builder.append("(");
                  _builder.append(args_dot, "");
                  _builder.append(")");
                }
              }
            }
          }
        }
      }
    }
    return _builder.toString();
  }
  
  protected static String _gen(final PropertyCallExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    OCLExpression _ownedSource = e.getOwnedSource();
    String _gen = OCL.gen(_ownedSource, consistency);
    _builder.append(_gen, "");
    _builder.append(".");
    Property _referredProperty = e.getReferredProperty();
    String _name = _referredProperty.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final OppositePropertyCallExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    OCLExpression _ownedSource = e.getOwnedSource();
    String _gen = OCL.gen(_ownedSource, consistency);
    _builder.append(_gen, "");
    _builder.append(".");
    Property _referredProperty = e.getReferredProperty();
    String _name = _referredProperty.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final VariableExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    boolean _isIsImplicit = e.isIsImplicit();
    if (_isIsImplicit) {
      String _xifexpression_1 = null;
      boolean _isConsistentVariable = OCL.isConsistentVariable(consistency, e);
      if (_isConsistentVariable) {
        _xifexpression_1 = OCL.getIteratorName(e);
      } else {
        String _iteratorName = OCL.getIteratorName(e);
        int _hashCode = e.hashCode();
        _xifexpression_1 = (_iteratorName + Integer.valueOf(_hashCode));
      }
      _xifexpression = _xifexpression_1;
    } else {
      String _xifexpression_2 = null;
      VariableDeclaration _referredVariable = e.getReferredVariable();
      String _name = _referredVariable.getName();
      boolean _equals = Objects.equal(_name, "self");
      if (_equals) {
        _xifexpression_2 = OCL.getIteratorName(e);
      } else {
        VariableDeclaration _referredVariable_1 = e.getReferredVariable();
        _xifexpression_2 = _referredVariable_1.getName();
      }
      _xifexpression = _xifexpression_2;
    }
    _builder.append(_xifexpression, "");
    return _builder.toString();
  }
  
  protected static String _gen(final IteratorVariable e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    boolean _isIsImplicit = e.isIsImplicit();
    if (_isIsImplicit) {
      Type _type = e.getType();
      String _gen = OCL.gen(_type, null);
      _xifexpression = OCL2ATL.genIteratorName(_gen);
    } else {
      _xifexpression = e.getName();
    }
    _builder.append(_xifexpression, "");
    return _builder.toString();
  }
  
  protected static String _gen(final Operation e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final Type e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final IntegerLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    Number _integerSymbol = e.getIntegerSymbol();
    _builder.append(_integerSymbol, "");
    return _builder.toString();
  }
  
  protected static String _gen(final BooleanLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    boolean _isBooleanSymbol = e.isBooleanSymbol();
    _builder.append(_isBooleanSymbol, "");
    return _builder.toString();
  }
  
  protected static String _gen(final EnumLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    Type _type = e.getType();
    String _name = _type.getName();
    _builder.append(_name, "");
    _builder.append(".");
    EnumerationLiteral _referredLiteral = e.getReferredLiteral();
    String _name_1 = _referredLiteral.getName();
    _builder.append(_name_1, "");
    return _builder.toString();
  }
  
  protected static String _gen(final NullLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("OclUndefined");
    return _builder.toString();
  }
  
  protected static String _gen(final TypeExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    Type _referredType = e.getReferredType();
    String _string = _referredType.toString();
    String _replace = _string.replace("::", "!");
    _builder.append(_replace, "");
    return _builder.toString();
  }
  
  protected static String _gen(final CollectionLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Sequence{}");
    return _builder.toString();
  }
  
  protected static String _gen(final UnlimitedNaturalLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("*");
    return _builder.toString();
  }
  
  protected static String _gen(final LetExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("let ");
    Variable _ownedVariable = e.getOwnedVariable();
    String _name = _ownedVariable.getName();
    _builder.append(_name, "");
    _builder.append(" : ");
    Variable _ownedVariable_1 = e.getOwnedVariable();
    Type _type = _ownedVariable_1.getType();
    String _string = _type.toString();
    String _replace = _string.replace("::", "!");
    _builder.append(_replace, "");
    _builder.append(" = ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    Variable _ownedVariable_2 = e.getOwnedVariable();
    OCLExpression _ownedInit = _ownedVariable_2.getOwnedInit();
    String _gen = OCL.gen(_ownedInit, consistency);
    _builder.append(_gen, "  ");
    _builder.append(" in ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    OCLExpression _ownedIn = e.getOwnedIn();
    String _gen_1 = OCL.gen(_ownedIn, consistency);
    _builder.append(_gen_1, "    ");
    return _builder.toString();
  }
  
  protected static String _gen(final IteratorExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    List<Variable> _ownedIterators = e.getOwnedIterators();
    int _size = _ownedIterators.size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      List<Variable> _ownedIterators_1 = e.getOwnedIterators();
      final Function1<Variable, String> _function = (Variable arg) -> {
        return OCL.gen(arg, consistency);
      };
      List<String> _map = ListExtensions.<Variable, String>map(_ownedIterators_1, _function);
      _xifexpression = IterableExtensions.join(_map, ",");
    } else {
      _xifexpression = "";
    }
    final String args_dot = _xifexpression;
    _builder.newLineIfNotEmpty();
    OCLExpression _ownedBody = e.getOwnedBody();
    final HashSet<PropertyCallExp> wdExprs = OCLWDGenerator.wd(_ownedBody);
    _builder.newLineIfNotEmpty();
    OCLExpression _ownedSource = e.getOwnedSource();
    String _gen = OCL.gen(_ownedSource, consistency);
    _builder.append(_gen, "");
    _builder.append("->");
    Iteration _referredIteration = e.getReferredIteration();
    String _name = _referredIteration.getName();
    _builder.append(_name, "");
    _builder.append("(");
    String _xifexpression_1 = null;
    List<Variable> _ownedIterators_2 = e.getOwnedIterators();
    int _size_1 = _ownedIterators_2.size();
    boolean _notEquals_1 = (_size_1 != 0);
    if (_notEquals_1) {
      _xifexpression_1 = (args_dot + "|");
    } else {
      _xifexpression_1 = "";
    }
    _builder.append(_xifexpression_1, "");
    _builder.newLineIfNotEmpty();
    {
      for(final PropertyCallExp expr : wdExprs) {
        {
          List<Variable> _ownedIterators_3 = e.getOwnedIterators();
          for(final Variable itor : _ownedIterators_3) {
            {
              if ((OCL2ATL.printAtHere(expr, OCL.gen(itor, consistency)) && (!OCL.wdSet.contains(OCL.gen(expr, new HashMap<String, VariableExp>()))))) {
                Object _xblockexpression = null;
                {
                  HashMap<String, VariableExp> _hashMap = new HashMap<String, VariableExp>();
                  String _gen_1 = OCL.gen(expr, _hashMap);
                  OCL.wdSet.add(_gen_1);
                  _xblockexpression = null;
                }
                _builder.append(_xblockexpression, "");
                {
                  boolean _isPrimtive = OCL.isPrimtive(expr);
                  boolean _not = (!_isPrimtive);
                  if (_not) {
                    {
                      boolean _isCollection = OCL.isCollection(expr);
                      boolean _not_1 = (!_isCollection);
                      if (_not_1) {
                        _builder.newLineIfNotEmpty();
                        Type _type = expr.getType();
                        String _string = _type.toString();
                        String _replace = _string.replace("::", "!");
                        _builder.append(_replace, "");
                        _builder.append(".allInstances()->contains(");
                        HashMap<String, VariableExp> _hashMap = new HashMap<String, VariableExp>();
                        String _gen_1 = OCL.gen(expr, _hashMap);
                        _builder.append(_gen_1, "");
                        _builder.append(") implies ");
                        _builder.newLineIfNotEmpty();
                      } else {
                        HashMap<String, VariableExp> _hashMap_1 = new HashMap<String, VariableExp>();
                        String _gen_2 = OCL.gen(expr, _hashMap_1);
                        _builder.append(_gen_2, "");
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
      }
    }
    Object _xblockexpression_1 = null;
    {
      OCL.wdSet.clear();
      _xblockexpression_1 = null;
    }
    _builder.append(_xblockexpression_1, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    OCLExpression _ownedBody_1 = e.getOwnedBody();
    String _gen_3 = OCL.gen(_ownedBody_1, consistency);
    _builder.append(_gen_3, "  ");
    _builder.append(")");
    return _builder.toString();
  }
  
  protected static String _gen(final IfExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    OCLExpression _ownedCondition = e.getOwnedCondition();
    String _gen = OCL.gen(_ownedCondition, consistency);
    _builder.append(_gen, "");
    _builder.append(") then ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    OCLExpression _ownedThen = e.getOwnedThen();
    String _gen_1 = OCL.gen(_ownedThen, consistency);
    _builder.append(_gen_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("else ");
    _builder.newLine();
    _builder.append("  ");
    OCLExpression _ownedElse = e.getOwnedElse();
    String _gen_2 = OCL.gen(_ownedElse, consistency);
    _builder.append(_gen_2, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("endif");
    return _builder.toString();
  }
  
  public static boolean isConsistentVariable(final HashMap<String, VariableExp> map, final VariableExp exp) {
    final String itName = OCL.getIteratorName(exp);
    return ((!map.containsKey(itName)) || Objects.equal(map.get(itName), exp));
  }
  
  public static String getIteratorName(final VariableExp exp) {
    final Type tp = exp.getType();
    final String clazz = OCL.gen(tp, null);
    final String itName = OCL2ATL.genIteratorName(clazz);
    return itName;
  }
  
  public static boolean isPrimtive(final PropertyCallExp e) {
    if ((((Objects.equal(OCL.gen(e.getType(), null), "String") || Objects.equal(OCL.gen(e.getType(), null), "Integer")) || Objects.equal(OCL.gen(e.getType(), null), "Boolean")) || Objects.equal(OCL.gen(e.getType(), null), "Real"))) {
      return true;
    } else {
      Type _type = e.getType();
      if ((_type instanceof Enumeration)) {
        return true;
      } else {
        return false;
      }
    }
  }
  
  public static boolean isCollection(final PropertyCallExp e) {
    if ((Objects.equal(OCL.gen(e.getType(), null), "Set") || Objects.equal(OCL.gen(e.getType(), null), "OrderedSet"))) {
      return true;
    } else {
      return false;
    }
  }
  
  public static String gen(final EObject e, final HashMap<String, VariableExp> consistency) {
    if (e instanceof IntegerLiteralExp) {
      return _gen((IntegerLiteralExp)e, consistency);
    } else if (e instanceof OppositePropertyCallExp) {
      return _gen((OppositePropertyCallExp)e, consistency);
    } else if (e instanceof PropertyCallExp) {
      return _gen((PropertyCallExp)e, consistency);
    } else if (e instanceof UnlimitedNaturalLiteralExp) {
      return _gen((UnlimitedNaturalLiteralExp)e, consistency);
    } else if (e instanceof BooleanLiteralExp) {
      return _gen((BooleanLiteralExp)e, consistency);
    } else if (e instanceof IteratorExp) {
      return _gen((IteratorExp)e, consistency);
    } else if (e instanceof NullLiteralExp) {
      return _gen((NullLiteralExp)e, consistency);
    } else if (e instanceof OperationCallExp) {
      return _gen((OperationCallExp)e, consistency);
    } else if (e instanceof CollectionLiteralExp) {
      return _gen((CollectionLiteralExp)e, consistency);
    } else if (e instanceof EnumLiteralExp) {
      return _gen((EnumLiteralExp)e, consistency);
    } else if (e instanceof IteratorVariable) {
      return _gen((IteratorVariable)e, consistency);
    } else if (e instanceof IfExp) {
      return _gen((IfExp)e, consistency);
    } else if (e instanceof LetExp) {
      return _gen((LetExp)e, consistency);
    } else if (e instanceof Operation) {
      return _gen((Operation)e, consistency);
    } else if (e instanceof TypeExp) {
      return _gen((TypeExp)e, consistency);
    } else if (e instanceof VariableExp) {
      return _gen((VariableExp)e, consistency);
    } else if (e instanceof OCLExpression) {
      return _gen((OCLExpression)e, consistency);
    } else if (e instanceof Type) {
      return _gen((Type)e, consistency);
    } else if (e != null) {
      return _gen(e, consistency);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(e, consistency).toString());
    }
  }
}
