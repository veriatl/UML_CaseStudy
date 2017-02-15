package fr.emn.atlanmod.uml.casestudy.rewrite;

import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.BooleanLiteralExp;
import org.eclipse.ocl.pivot.CollectionLiteralExp;
import org.eclipse.ocl.pivot.EnumLiteralExp;
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
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.Type;
import org.eclipse.ocl.pivot.TypeExp;
import org.eclipse.ocl.pivot.UnlimitedNaturalLiteralExp;
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * @author zcheng
 */
@SuppressWarnings("all")
public class OCLProjector {
  protected static boolean _proj(final EObject e) {
    return true;
  }
  
  protected static boolean _proj(final OperationCallExp e) {
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
    boolean r = false;
    OCLExpression _ownedSource = e.getOwnedSource();
    final boolean src = OCLProjector.proj(_ownedSource);
    List<OCLExpression> _ownedArguments = e.getOwnedArguments();
    final Function1<OCLExpression, Boolean> _function = (OCLExpression arg) -> {
      return Boolean.valueOf(OCLProjector.proj(arg));
    };
    final List<Boolean> args = ListExtensions.<OCLExpression, Boolean>map(_ownedArguments, _function);
    boolean args_res = true;
    if (((!Objects.equal(args, null)) && (args.size() > 0))) {
      final Function2<Boolean, Boolean, Boolean> _function_1 = (Boolean res, Boolean arg) -> {
        return Boolean.valueOf(((res).booleanValue() && (arg).booleanValue()));
      };
      Boolean _reduce = IterableExtensions.<Boolean>reduce(args, _function_1);
      args_res = (_reduce).booleanValue();
    }
    boolean _equals_1 = Objects.equal(op, "not");
    if (_equals_1) {
      r = true;
    } else {
      if ((((((((((((((((Objects.equal(op, "+") || Objects.equal(op, "-")) || Objects.equal(op, "*")) || Objects.equal(op, "/")) || Objects.equal(op, "=")) || Objects.equal(op, "<>")) || Objects.equal(op, ">")) || 
        Objects.equal(op, "<")) || Objects.equal(op, ">=")) || Objects.equal(op, "<=")) || Objects.equal(op, "implies")) || Objects.equal(op, "and")) || Objects.equal(op, "or")) || Objects.equal(op, "div")) || 
        Objects.equal(op, "mod")) || Objects.equal(op, "xor"))) {
        r = true;
      } else {
        if ((((((((((Objects.equal(op, "size") || Objects.equal(op, "allInstances")) || Objects.equal(op, "isEmpty")) || Objects.equal(op, "max")) || Objects.equal(op, "min")) || Objects.equal(op, "notEmpty")) || Objects.equal(op, "oclIsUndefined")) || Objects.equal(op, "oclType")) || Objects.equal(op, "first")) || 
          Objects.equal(op, "last"))) {
          r = true;
        } else {
          if (((((Objects.equal(op, "oclIsUndefined") || Objects.equal(op, "toLower")) || Objects.equal(op, "toLowerCase")) || Objects.equal(op, "toUpper")) || Objects.equal(op, "toUpperCase"))) {
            r = true;
          } else {
            if ((((((((Objects.equal(op, "oclIsKindOf") || Objects.equal(op, "oclIsTypeOf")) || Objects.equal(op, "concat")) || 
              Objects.equal(op, "endsWith")) || Objects.equal(op, "indexOf")) || Objects.equal(op, "lastIndexOf")) || Objects.equal(op, "startsWith")) || Objects.equal(op, "substring"))) {
              r = true;
            } else {
              if ((((((((Objects.equal(op, "excluding") || Objects.equal(op, "including")) || 
                Objects.equal(op, "excludes")) || 
                Objects.equal(op, "includes")) || Objects.equal(op, "at")) || 
                Objects.equal(op, "append")) || Objects.equal(op, "prepend")) || 
                Objects.equal(op, "union"))) {
                r = true;
              }
            }
          }
        }
      }
    }
    return ((r && src) && args_res);
  }
  
  protected static boolean _proj(final PropertyCallExp e) {
    OCLExpression _ownedSource = e.getOwnedSource();
    return OCLProjector.proj(_ownedSource);
  }
  
  protected static boolean _proj(final OppositePropertyCallExp e) {
    OCLExpression _ownedSource = e.getOwnedSource();
    return OCLProjector.proj(_ownedSource);
  }
  
  protected static boolean _proj(final VariableExp e) {
    return true;
  }
  
  protected static boolean _proj(final IteratorVariable e) {
    return true;
  }
  
  protected static boolean _proj(final Operation e) {
    return true;
  }
  
  protected static boolean _proj(final Type e) {
    return true;
  }
  
  protected static boolean _proj(final IntegerLiteralExp e) {
    return true;
  }
  
  protected static boolean _proj(final BooleanLiteralExp e) {
    return true;
  }
  
  protected static boolean _proj(final EnumLiteralExp e) {
    return false;
  }
  
  protected static boolean _proj(final NullLiteralExp e) {
    return false;
  }
  
  protected static boolean _proj(final TypeExp e) {
    return true;
  }
  
  protected static boolean _proj(final CollectionLiteralExp e) {
    return false;
  }
  
  protected static boolean _proj(final UnlimitedNaturalLiteralExp e) {
    return false;
  }
  
  protected static boolean _proj(final LetExp e) {
    return false;
  }
  
  protected static boolean _proj(final IteratorExp e) {
    String _xifexpression = null;
    Iteration _referredIteration = e.getReferredIteration();
    String _name = _referredIteration.getName();
    boolean _equals = Objects.equal(_name, "");
    if (_equals) {
      _xifexpression = e.getName();
    } else {
      Iteration _referredIteration_1 = e.getReferredIteration();
      _xifexpression = _referredIteration_1.getName();
    }
    final String op = _xifexpression;
    boolean r = true;
    OCLExpression _ownedSource = e.getOwnedSource();
    final boolean src = OCLProjector.proj(_ownedSource);
    OCLExpression _ownedBody = e.getOwnedBody();
    final boolean body = OCLProjector.proj(_ownedBody);
    if ((Objects.equal(op, null) || Objects.equal(op, ""))) {
      r = false;
    } else {
      if (((((Objects.equal(op, "collect") || Objects.equal(op, "iterate")) || Objects.equal(op, "closure")) || Objects.equal(op, "exists")) || Objects.equal(op, "select"))) {
        r = false;
      }
    }
    return ((r && src) && body);
  }
  
  protected static boolean _proj(final IfExp e) {
    return (((false && OCLProjector.proj(e.getOwnedCondition())) && OCLProjector.proj(e.getOwnedThen())) && OCLProjector.proj(e.getOwnedElse()));
  }
  
  public static boolean proj(final EObject e) {
    if (e instanceof IntegerLiteralExp) {
      return _proj((IntegerLiteralExp)e);
    } else if (e instanceof OppositePropertyCallExp) {
      return _proj((OppositePropertyCallExp)e);
    } else if (e instanceof PropertyCallExp) {
      return _proj((PropertyCallExp)e);
    } else if (e instanceof UnlimitedNaturalLiteralExp) {
      return _proj((UnlimitedNaturalLiteralExp)e);
    } else if (e instanceof BooleanLiteralExp) {
      return _proj((BooleanLiteralExp)e);
    } else if (e instanceof IteratorExp) {
      return _proj((IteratorExp)e);
    } else if (e instanceof NullLiteralExp) {
      return _proj((NullLiteralExp)e);
    } else if (e instanceof OperationCallExp) {
      return _proj((OperationCallExp)e);
    } else if (e instanceof CollectionLiteralExp) {
      return _proj((CollectionLiteralExp)e);
    } else if (e instanceof EnumLiteralExp) {
      return _proj((EnumLiteralExp)e);
    } else if (e instanceof IteratorVariable) {
      return _proj((IteratorVariable)e);
    } else if (e instanceof IfExp) {
      return _proj((IfExp)e);
    } else if (e instanceof LetExp) {
      return _proj((LetExp)e);
    } else if (e instanceof Operation) {
      return _proj((Operation)e);
    } else if (e instanceof TypeExp) {
      return _proj((TypeExp)e);
    } else if (e instanceof VariableExp) {
      return _proj((VariableExp)e);
    } else if (e instanceof Type) {
      return _proj((Type)e);
    } else if (e != null) {
      return _proj(e);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(e).toString());
    }
  }
}
