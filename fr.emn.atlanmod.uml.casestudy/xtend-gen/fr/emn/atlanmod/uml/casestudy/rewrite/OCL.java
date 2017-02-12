package fr.emn.atlanmod.uml.casestudy.rewrite;

import com.google.common.base.Objects;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCL2ATL;
import java.util.Arrays;
import java.util.HashMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.BooleanLiteralExp;
import org.eclipse.ocl.pivot.CollectionLiteralExp;
import org.eclipse.ocl.pivot.EnumLiteralExp;
import org.eclipse.ocl.pivot.IfExp;
import org.eclipse.ocl.pivot.IntegerLiteralExp;
import org.eclipse.ocl.pivot.IteratorExp;
import org.eclipse.ocl.pivot.IteratorVariable;
import org.eclipse.ocl.pivot.LetExp;
import org.eclipse.ocl.pivot.NullLiteralExp;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.Operation;
import org.eclipse.ocl.pivot.OperationCallExp;
import org.eclipse.ocl.pivot.OppositePropertyCallExp;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.ocl.pivot.TypeExp;
import org.eclipse.ocl.pivot.UnlimitedNaturalLiteralExp;
import org.eclipse.ocl.pivot.Variable;
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class OCL {
  protected static String _gen(final EObject e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We dont understand ");
    String _name = e.eClass().getName();
    _builder.append(_name);
    return _builder.toString();
  }
  
  protected static String _gen(final OCLExpression e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We dont understand ocl expression ");
    String _name = e.eClass().getName();
    _builder.append(_name);
    return _builder.toString();
  }
  
  protected static String _gen(final OperationCallExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    String _name = e.getReferredOperation().getName();
    boolean _equals = Objects.equal(_name, "");
    if (_equals) {
      _xifexpression = e.getName();
    } else {
      _xifexpression = e.getReferredOperation().getName();
    }
    final String op = _xifexpression;
    _builder.newLineIfNotEmpty();
    final String src = OCL.gen(e.getOwnedSource(), consistency);
    _builder.newLineIfNotEmpty();
    String _xifexpression_1 = null;
    if (((e.getOwnedArguments().size() != 0) && (!Objects.equal(op, null)))) {
      final Function1<OCLExpression, String> _function = (OCLExpression arg) -> {
        return OCL.gen(arg, consistency);
      };
      _xifexpression_1 = IterableExtensions.join(ListExtensions.<OCLExpression, String>map(e.getOwnedArguments(), _function), op);
    } else {
      _xifexpression_1 = "";
    }
    final String args = _xifexpression_1;
    _builder.newLineIfNotEmpty();
    String _xifexpression_2 = null;
    if (((e.getOwnedArguments().size() != 0) && (!Objects.equal(op, null)))) {
      final Function1<OCLExpression, String> _function_1 = (OCLExpression arg) -> {
        return OCL.gen(arg, consistency);
      };
      _xifexpression_2 = IterableExtensions.join(ListExtensions.<OCLExpression, String>map(e.getOwnedArguments(), _function_1), ",");
    } else {
      _xifexpression_2 = "";
    }
    final String args_dot = _xifexpression_2;
    {
      if ((Objects.equal(op, "not") || Objects.equal(op, "abs"))) {
        _builder.append(op);
        _builder.append("(");
        String _gen = OCL.gen(e.getOwnedSource(), consistency);
        _builder.append(_gen);
        _builder.append(")");
      } else {
        if ((((((((((((((((((((((Objects.equal(op, "+") || Objects.equal(op, "-")) || Objects.equal(op, "*")) || Objects.equal(op, "/")) || Objects.equal(op, "=")) || Objects.equal(op, "<>")) || Objects.equal(op, ">")) || Objects.equal(op, "<")) || Objects.equal(op, ">=")) || Objects.equal(op, "<=")) || Objects.equal(op, "implies")) || Objects.equal(op, "and")) || Objects.equal(op, "or")) || Objects.equal(op, "div")) || Objects.equal(op, "mod")) || Objects.equal(op, "implies")) || Objects.equal(op, "implies")) || Objects.equal(op, "and")) || Objects.equal(op, "or")) || Objects.equal(op, "implies")) || Objects.equal(op, "implies")) || Objects.equal(op, "implies"))) {
          _builder.append(src);
          _builder.append(" ");
          _builder.append(op);
          _builder.append(" ");
          _builder.append(args);
        } else {
          if (((((((((((((((((Objects.equal(op, "size") || Objects.equal(op, "flatten")) || Objects.equal(op, "allInstances")) || Objects.equal(op, "xor")) || Objects.equal(op, "asBag")) || Objects.equal(op, "asOrderedSet")) || Objects.equal(op, "asSequence")) || Objects.equal(op, "asSet")) || Objects.equal(op, "isEmpty")) || Objects.equal(op, "max")) || Objects.equal(op, "min")) || Objects.equal(op, "notEmpty")) || Objects.equal(op, "oclIsUndefined")) || Objects.equal(op, "oclType")) || Objects.equal(op, "first")) || Objects.equal(op, "last")) || Objects.equal(op, "oclAsSet"))) {
            _builder.append(src);
            _builder.append("->");
            _builder.append(op);
            _builder.append("()");
          } else {
            if (((((((((Objects.equal(op, "oclIsUndefined") || Objects.equal(op, "oclIsInvalid")) || Objects.equal(op, "toBoolean")) || Objects.equal(op, "toInteger")) || Objects.equal(op, "toLower")) || Objects.equal(op, "toLowerCase")) || Objects.equal(op, "toString")) || Objects.equal(op, "toUpper")) || Objects.equal(op, "toUpperCase"))) {
              _builder.append(src);
              _builder.append(".");
              _builder.append(op);
              _builder.append("()");
            } else {
              if ((((((((((Objects.equal(op, "oclAsType") || Objects.equal(op, "oclIsKindOf")) || Objects.equal(op, "oclIsTypeOf")) || Objects.equal(op, "concat")) || Objects.equal(op, "endsWith")) || Objects.equal(op, "indexOf")) || Objects.equal(op, "lastIndexOf")) || Objects.equal(op, "startsWith")) || Objects.equal(op, "substring")) || Objects.equal(op, "conformsTo"))) {
                _builder.append(src);
                _builder.append(".");
                _builder.append(op);
                _builder.append("(");
                _builder.append(args_dot);
                _builder.append(")");
              } else {
                if ((((((((((((((((((((((Objects.equal(op, "excluding") || Objects.equal(op, "excludingAll")) || Objects.equal(op, "including")) || Objects.equal(op, "includingAll")) || Objects.equal(op, "selectByKind")) || Objects.equal(op, "selectByKind")) || Objects.equal(op, "selectByType")) || Objects.equal(op, "count")) || Objects.equal(op, "excludes")) || Objects.equal(op, "includes")) || Objects.equal(op, "includesAll")) || Objects.equal(op, "intersection")) || Objects.equal(op, "at")) || Objects.equal(op, "indexOf")) || Objects.equal(op, "append")) || Objects.equal(op, "appendAll")) || Objects.equal(op, "prepend")) || Objects.equal(op, "prependAll")) || Objects.equal(op, "reverse")) || Objects.equal(op, "union")) || Objects.equal(op, "is")) || Objects.equal(op, "excludesAll"))) {
                  _builder.append(src);
                  _builder.append("->");
                  _builder.append(op);
                  _builder.append("(");
                  _builder.append(args_dot);
                  _builder.append(")");
                } else {
                  _builder.append(src);
                  _builder.append(".");
                  _builder.append(op);
                  _builder.append("(");
                  _builder.append(args_dot);
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
    String _gen = OCL.gen(e.getOwnedSource(), consistency);
    _builder.append(_gen);
    _builder.append(".");
    String _name = e.getReferredProperty().getName();
    _builder.append(_name);
    return _builder.toString();
  }
  
  protected static String _gen(final OppositePropertyCallExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _gen = OCL.gen(e.getOwnedSource(), consistency);
    _builder.append(_gen);
    _builder.append(".");
    String _name = e.getReferredProperty().getName();
    _builder.append(_name);
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
      String _name = e.getReferredVariable().getName();
      boolean _equals = Objects.equal(_name, "self");
      if (_equals) {
        _xifexpression_2 = OCL.getIteratorName(e);
      } else {
        _xifexpression_2 = e.getReferredVariable().getName();
      }
      _xifexpression = _xifexpression_2;
    }
    _builder.append(_xifexpression);
    return _builder.toString();
  }
  
  protected static String _gen(final IteratorVariable e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    boolean _isIsImplicit = e.isIsImplicit();
    if (_isIsImplicit) {
      _xifexpression = OCL2ATL.genIteratorName(OCL.gen(e.getType(), null));
    } else {
      _xifexpression = e.getName();
    }
    _builder.append(_xifexpression);
    return _builder.toString();
  }
  
  protected static String _gen(final Operation e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getName();
    _builder.append(_name);
    return _builder.toString();
  }
  
  protected static String _gen(final Type e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getName();
    _builder.append(_name);
    return _builder.toString();
  }
  
  protected static String _gen(final IntegerLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    Number _integerSymbol = e.getIntegerSymbol();
    _builder.append(_integerSymbol);
    return _builder.toString();
  }
  
  protected static String _gen(final BooleanLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    boolean _isBooleanSymbol = e.isBooleanSymbol();
    _builder.append(_isBooleanSymbol);
    return _builder.toString();
  }
  
  protected static String _gen(final EnumLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = e.getType().getName();
    _builder.append(_name);
    _builder.append(".");
    String _name_1 = e.getReferredLiteral().getName();
    _builder.append(_name_1);
    return _builder.toString();
  }
  
  protected static String _gen(final NullLiteralExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("OclUndefined");
    return _builder.toString();
  }
  
  protected static String _gen(final TypeExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _replace = e.getReferredType().toString().replace("::", "!");
    _builder.append(_replace);
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
    String _name = e.getOwnedVariable().getName();
    _builder.append(_name);
    _builder.append(" : ");
    String _replace = e.getOwnedVariable().getType().toString().replace("::", "!");
    _builder.append(_replace);
    _builder.append(" = ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    String _gen = OCL.gen(e.getOwnedVariable().getOwnedInit(), consistency);
    _builder.append(_gen, "  ");
    _builder.append(" in ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _gen_1 = OCL.gen(e.getOwnedIn(), consistency);
    _builder.append(_gen_1, "    ");
    return _builder.toString();
  }
  
  protected static String _gen(final IteratorExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _xifexpression = null;
    int _size = e.getOwnedIterators().size();
    boolean _notEquals = (_size != 0);
    if (_notEquals) {
      final Function1<Variable, String> _function = (Variable arg) -> {
        return OCL.gen(arg, consistency);
      };
      _xifexpression = IterableExtensions.join(ListExtensions.<Variable, String>map(e.getOwnedIterators(), _function), ",");
    } else {
      _xifexpression = "";
    }
    final String args_dot = _xifexpression;
    _builder.newLineIfNotEmpty();
    String _gen = OCL.gen(e.getOwnedSource(), consistency);
    _builder.append(_gen);
    _builder.append("->");
    String _name = e.getReferredIteration().getName();
    _builder.append(_name);
    _builder.append("(");
    String _xifexpression_1 = null;
    int _size_1 = e.getOwnedIterators().size();
    boolean _notEquals_1 = (_size_1 != 0);
    if (_notEquals_1) {
      _xifexpression_1 = (args_dot + "|");
    } else {
      _xifexpression_1 = "";
    }
    _builder.append(_xifexpression_1);
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    String _gen_1 = OCL.gen(e.getOwnedBody(), consistency);
    _builder.append(_gen_1, "  ");
    _builder.append(")");
    return _builder.toString();
  }
  
  protected static String _gen(final IfExp e, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    String _gen = OCL.gen(e.getOwnedCondition(), consistency);
    _builder.append(_gen);
    _builder.append(") then ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    String _gen_1 = OCL.gen(e.getOwnedThen(), consistency);
    _builder.append(_gen_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("else ");
    _builder.newLine();
    _builder.append("  ");
    String _gen_2 = OCL.gen(e.getOwnedElse(), consistency);
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
