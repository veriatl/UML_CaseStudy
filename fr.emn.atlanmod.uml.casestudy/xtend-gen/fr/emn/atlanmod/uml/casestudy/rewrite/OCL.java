package fr.emn.atlanmod.uml.casestudy.rewrite;

import com.google.common.base.Objects;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCL2ATL;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLWDGenerator;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class OCL {
  private static HashSet<String> wdSetInner = new HashSet<String>();
  
  public static HashMap<String, EObject> bvMap = new HashMap<String, EObject>();
  
  private static String[] keywords = { "true", "false", "Bag", "Set", "OrderedSet", "Sequence", "Tuple", "Integer", "Real", "Boolean", "String", "TupleType", "Map", "not", "and", "or", "xor", "implies", "module", "create", "from", "uses", "helper", "def", "context", "rule", "using", "derived", "to", "mapsTo", "distinct", "foreach", "in", "do", "if", "then", "else", "endif", "let", "library", "query", "for", "div", "refining", "entrypoint" };
  
  protected static String _gen(final EObject e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We dont understand ");
    EClass _eClass = e.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final OCLExpression e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We dont understand ocl expression ");
    EClass _eClass = e.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final OperationCallExp e) {
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
    final String src = OCL.gen(_ownedSource);
    _builder.newLineIfNotEmpty();
    String _xifexpression_1 = null;
    if (((e.getOwnedArguments().size() != 0) && (!Objects.equal(op, null)))) {
      List<OCLExpression> _ownedArguments = e.getOwnedArguments();
      final Function1<OCLExpression, String> _function = (OCLExpression arg) -> {
        return OCL.gen(arg);
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
        return OCL.gen(arg);
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
        String _gen = OCL.gen(_ownedSource_1);
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
  
  protected static String _gen(final PropertyCallExp e) {
    StringConcatenation _builder = new StringConcatenation();
    OCLExpression _ownedSource = e.getOwnedSource();
    String _gen = OCL.gen(_ownedSource);
    _builder.append(_gen, "");
    _builder.append(".");
    Property _referredProperty = e.getReferredProperty();
    String _name = _referredProperty.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final OppositePropertyCallExp e) {
    StringConcatenation _builder = new StringConcatenation();
    OCLExpression _ownedSource = e.getOwnedSource();
    String _gen = OCL.gen(_ownedSource);
    _builder.append(_gen, "");
    _builder.append(".");
    Property _referredProperty = e.getReferredProperty();
    String _name = _referredProperty.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final VariableExp e) {
    StringConcatenation _builder = new StringConcatenation();
    Type _type = e.getType();
    String _gen = OCL.gen(_type);
    final String itName = OCL.genIteratorName(_gen);
    _builder.newLineIfNotEmpty();
    final VariableDeclaration ref = e.getReferredVariable();
    {
      Collection<EObject> _values = OCL.bvMap.values();
      boolean _contains = _values.contains(ref);
      if (_contains) {
        _builder.newLineIfNotEmpty();
        String _keyByValue = OCL.getKeyByValue(OCL.bvMap, ref);
        _builder.append(_keyByValue, "");
      } else {
        {
          Set<String> _keySet = OCL.bvMap.keySet();
          boolean _contains_1 = _keySet.contains(itName);
          if (_contains_1) {
            final EObject candidate = OCL.bvMap.get(itName);
            {
              boolean _equals = Objects.equal(candidate, null);
              if (_equals) {
                _builder.newLineIfNotEmpty();
                _builder.append(itName, "");
                _builder.newLineIfNotEmpty();
                Object _xblockexpression = null;
                {
                  OCL.bvMap.put(itName, ref);
                  _xblockexpression = null;
                }
                _builder.append(_xblockexpression, "");
              } else {
                boolean _equals_1 = Objects.equal(candidate, ref);
                if (_equals_1) {
                  _builder.newLineIfNotEmpty();
                  _builder.append(itName, "");
                } else {
                  _builder.newLineIfNotEmpty();
                  String _name = ref.getName();
                  _builder.append(_name, "");
                }
              }
            }
          } else {
            _builder.newLineIfNotEmpty();
            _builder.append(itName, "");
            _builder.newLineIfNotEmpty();
            Object _xblockexpression_1 = null;
            {
              OCL.bvMap.put(itName, e);
              _xblockexpression_1 = null;
            }
            _builder.append(_xblockexpression_1, "");
          }
        }
      }
    }
    return _builder.toString();
  }
  
  protected static String _gen(final IteratorVariable e) {
    StringConcatenation _builder = new StringConcatenation();
    Type _type = e.getType();
    String _gen = OCL.gen(_type);
    final String clazz = OCL.genIteratorName(_gen);
    _builder.newLineIfNotEmpty();
    String _name = e.getName();
    final String itName = (clazz + _name);
    _builder.newLineIfNotEmpty();
    int _hashCode = e.hashCode();
    final String hashName = ((clazz + itName) + Integer.valueOf(_hashCode));
    {
      if ((OCL.bvMap.keySet().contains(itName) && Objects.equal(OCL.bvMap.get(itName), e))) {
        _builder.append(itName, "");
      } else {
        if ((OCL.bvMap.keySet().contains(itName) && (!Objects.equal(OCL.bvMap.get(itName), e)))) {
          {
            Set<String> _keySet = OCL.bvMap.keySet();
            boolean _contains = _keySet.contains(hashName);
            if (_contains) {
              _builder.newLineIfNotEmpty();
              _builder.append(hashName, "");
            } else {
              _builder.newLineIfNotEmpty();
              _builder.append(hashName, "");
              Object _xblockexpression = null;
              {
                OCL.bvMap.put(hashName, e);
                _xblockexpression = null;
              }
              _builder.append(_xblockexpression, "");
            }
          }
        } else {
          Set<String> _keySet_1 = OCL.bvMap.keySet();
          boolean _contains_1 = _keySet_1.contains(itName);
          boolean _not = (!_contains_1);
          if (_not) {
            _builder.append(itName, "");
            Object _xblockexpression_1 = null;
            {
              OCL.bvMap.put(itName, e);
              _xblockexpression_1 = null;
            }
            _builder.append(_xblockexpression_1, "");
          }
        }
      }
    }
    return _builder.toString();
  }
  
  protected static String _gen(final Operation e) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final Type e) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  protected static String _gen(final IntegerLiteralExp e) {
    StringConcatenation _builder = new StringConcatenation();
    Number _integerSymbol = e.getIntegerSymbol();
    _builder.append(_integerSymbol, "");
    return _builder.toString();
  }
  
  protected static String _gen(final BooleanLiteralExp e) {
    StringConcatenation _builder = new StringConcatenation();
    boolean _isBooleanSymbol = e.isBooleanSymbol();
    _builder.append(_isBooleanSymbol, "");
    return _builder.toString();
  }
  
  protected static String _gen(final EnumLiteralExp e) {
    StringConcatenation _builder = new StringConcatenation();
    EnumerationLiteral _referredLiteral = e.getReferredLiteral();
    final String n = _referredLiteral.getName();
    _builder.newLineIfNotEmpty();
    String _xifexpression = null;
    boolean _contains = ((List<String>)Conversions.doWrapArray(OCL.keywords)).contains(n);
    if (_contains) {
      _xifexpression = ("#_" + n);
    } else {
      _xifexpression = ("#" + n);
    }
    _builder.append(_xifexpression, "");
    return _builder.toString();
  }
  
  protected static String _gen(final NullLiteralExp e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("OclUndefined");
    return _builder.toString();
  }
  
  protected static String _gen(final TypeExp e) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    if (OCL2ATL.postMode) {
      Type _referredType = e.getReferredType();
      String _string = _referredType.toString();
      String _replace = _string.replace("::", "!");
      _xifexpression = _replace.replace((OCL2ATL.model + "!"), (OCL2ATL.modelReplacer + "!"));
    } else {
      Type _referredType_1 = e.getReferredType();
      String _string_1 = _referredType_1.toString();
      _xifexpression = _string_1.replace("::", "!");
    }
    _builder.append(_xifexpression, "");
    return _builder.toString();
  }
  
  protected static String _gen(final CollectionLiteralExp e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Sequence{}");
    return _builder.toString();
  }
  
  protected static String _gen(final UnlimitedNaturalLiteralExp e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("*");
    return _builder.toString();
  }
  
  protected static String _gen(final LetExp e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("let ");
    Variable _ownedVariable = e.getOwnedVariable();
    String _name = _ownedVariable.getName();
    _builder.append(_name, "");
    _builder.append(" : ");
    String _xifexpression = null;
    if (OCL2ATL.postMode) {
      Variable _ownedVariable_1 = e.getOwnedVariable();
      Type _type = _ownedVariable_1.getType();
      String _string = _type.toString();
      String _replace = _string.replace("::", "!");
      _xifexpression = _replace.replace((OCL2ATL.model + "!"), (OCL2ATL.modelReplacer + "!"));
    } else {
      Variable _ownedVariable_2 = e.getOwnedVariable();
      Type _type_1 = _ownedVariable_2.getType();
      String _string_1 = _type_1.toString();
      _xifexpression = _string_1.replace("::", "!");
    }
    _builder.append(_xifexpression, "");
    _builder.append(" = ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    Variable _ownedVariable_3 = e.getOwnedVariable();
    OCLExpression _ownedInit = _ownedVariable_3.getOwnedInit();
    String _gen = OCL.gen(_ownedInit);
    _builder.append(_gen, "  ");
    _builder.append(" in ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    OCLExpression _ownedIn = e.getOwnedIn();
    String _gen_1 = OCL.gen(_ownedIn);
    _builder.append(_gen_1, "    ");
    return _builder.toString();
  }
  
  protected static String _gen(final IteratorExp e) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    List<Variable> _ownedIterators = e.getOwnedIterators();
    int _size = _ownedIterators.size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      List<Variable> _ownedIterators_1 = e.getOwnedIterators();
      final Function1<Variable, String> _function = (Variable arg) -> {
        return OCL.gen(arg);
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
    String _gen = OCL.gen(_ownedSource);
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
              if ((OCL.printAtHere(expr, OCL.gen(itor)) && (!OCL.wdSetInner.contains(OCL.gen(expr))))) {
                Object _xblockexpression = null;
                {
                  String _gen_1 = OCL.gen(expr);
                  OCL.wdSetInner.add(_gen_1);
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
                        String _xifexpression_2 = null;
                        if (OCL2ATL.postMode) {
                          Type _type = expr.getType();
                          String _string = _type.toString();
                          String _replace = _string.replace("::", "!");
                          _xifexpression_2 = _replace.replace((OCL2ATL.model + "!"), (OCL2ATL.modelReplacer + "!"));
                        } else {
                          Type _type_1 = expr.getType();
                          String _string_1 = _type_1.toString();
                          _xifexpression_2 = _string_1.replace("::", "!");
                        }
                        _builder.append(_xifexpression_2, "");
                        _builder.append(".allInstances()->includes(");
                        String _gen_1 = OCL.gen(expr);
                        _builder.append(_gen_1, "");
                        _builder.append(") implies ");
                        _builder.newLineIfNotEmpty();
                      } else {
                        String _gen_2 = OCL.gen(expr);
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
      OCL.wdSetInner.clear();
      _xblockexpression_1 = null;
    }
    _builder.append(_xblockexpression_1, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    OCLExpression _ownedBody_1 = e.getOwnedBody();
    String _gen_3 = OCL.gen(_ownedBody_1);
    _builder.append(_gen_3, "  ");
    _builder.append(")");
    return _builder.toString();
  }
  
  protected static String _gen(final IfExp e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    OCLExpression _ownedCondition = e.getOwnedCondition();
    String _gen = OCL.gen(_ownedCondition);
    _builder.append(_gen, "");
    _builder.append(") then ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    OCLExpression _ownedThen = e.getOwnedThen();
    String _gen_1 = OCL.gen(_ownedThen);
    _builder.append(_gen_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("else ");
    _builder.newLine();
    _builder.append("  ");
    OCLExpression _ownedElse = e.getOwnedElse();
    String _gen_2 = OCL.gen(_ownedElse);
    _builder.append(_gen_2, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("endif");
    return _builder.toString();
  }
  
  public static boolean isConsistentVariable(final HashMap<String, EObject> map, final EObject exp) {
    if ((exp instanceof VariableExp)) {
      Type _type = ((VariableExp)exp).getType();
      String _gen = OCL.gen(_type);
      final String itName = OCL.genIteratorName(_gen);
      return ((!map.containsKey(itName)) || Objects.equal(map.get(itName), exp));
    } else {
      if ((exp instanceof IteratorVariable)) {
        Type _type_1 = ((IteratorVariable)exp).getType();
        String _gen_1 = OCL.gen(_type_1);
        final String itName_1 = OCL.genIteratorName(_gen_1);
        return ((!map.containsKey(itName_1)) || Objects.equal(map.get(itName_1), exp));
      } else {
        return true;
      }
    }
  }
  
  public static boolean isPrimtive(final PropertyCallExp e) {
    Type _type = e.getType();
    final String s = OCL.gen(_type);
    if ((((Objects.equal(s, "String") || Objects.equal(s, "Integer")) || Objects.equal(s, "Boolean")) || Objects.equal(s, "Real"))) {
      return true;
    } else {
      Type _type_1 = e.getType();
      if ((_type_1 instanceof Enumeration)) {
        return true;
      } else {
        return false;
      }
    }
  }
  
  public static boolean isCollection(final PropertyCallExp e) {
    Type _type = e.getType();
    final String s = OCL.gen(_type);
    if (((Objects.equal(s, "Set") || Objects.equal(s, "OrderedSet")) || Objects.equal(s, "Sequence"))) {
      return true;
    } else {
      return false;
    }
  }
  
  public static String getKeyByValue(final HashMap<String, EObject> map, final VariableDeclaration exp) {
    Set<String> _keySet = map.keySet();
    for (final String key : _keySet) {
      EObject _get = map.get(key);
      boolean _equals = Objects.equal(_get, exp);
      if (_equals) {
        return key;
      }
    }
    return "no such key";
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
    boolean _contains = ((List<String>)Conversions.doWrapArray(OCL.keywords)).contains(rtn);
    if (_contains) {
      rtn = ("_" + rtn);
    }
    return rtn;
  }
  
  public static boolean printAtHere(final PropertyCallExp e, final String v) {
    boolean r = false;
    OCLExpression _ownedSource = e.getOwnedSource();
    if ((_ownedSource instanceof VariableExp)) {
      OCLExpression _ownedSource_1 = e.getOwnedSource();
      String _gen = OCL.gen(_ownedSource_1);
      String _trim = _gen.trim();
      boolean _equals = Objects.equal(_trim, v);
      if (_equals) {
        r = true;
      }
    } else {
      OCLExpression _ownedSource_2 = e.getOwnedSource();
      if ((_ownedSource_2 instanceof PropertyCallExp)) {
        OCLExpression _ownedSource_3 = e.getOwnedSource();
        boolean _printAtHere = OCL.printAtHere(((PropertyCallExp) _ownedSource_3), v);
        r = _printAtHere;
      }
    }
    return r;
  }
  
  public static String gen(final EObject e) {
    if (e instanceof IntegerLiteralExp) {
      return _gen((IntegerLiteralExp)e);
    } else if (e instanceof OppositePropertyCallExp) {
      return _gen((OppositePropertyCallExp)e);
    } else if (e instanceof PropertyCallExp) {
      return _gen((PropertyCallExp)e);
    } else if (e instanceof UnlimitedNaturalLiteralExp) {
      return _gen((UnlimitedNaturalLiteralExp)e);
    } else if (e instanceof BooleanLiteralExp) {
      return _gen((BooleanLiteralExp)e);
    } else if (e instanceof IteratorExp) {
      return _gen((IteratorExp)e);
    } else if (e instanceof NullLiteralExp) {
      return _gen((NullLiteralExp)e);
    } else if (e instanceof OperationCallExp) {
      return _gen((OperationCallExp)e);
    } else if (e instanceof CollectionLiteralExp) {
      return _gen((CollectionLiteralExp)e);
    } else if (e instanceof EnumLiteralExp) {
      return _gen((EnumLiteralExp)e);
    } else if (e instanceof IteratorVariable) {
      return _gen((IteratorVariable)e);
    } else if (e instanceof IfExp) {
      return _gen((IfExp)e);
    } else if (e instanceof LetExp) {
      return _gen((LetExp)e);
    } else if (e instanceof Operation) {
      return _gen((Operation)e);
    } else if (e instanceof TypeExp) {
      return _gen((TypeExp)e);
    } else if (e instanceof VariableExp) {
      return _gen((VariableExp)e);
    } else if (e instanceof OCLExpression) {
      return _gen((OCLExpression)e);
    } else if (e instanceof Type) {
      return _gen((Type)e);
    } else if (e != null) {
      return _gen(e);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(e).toString());
    }
  }
}
