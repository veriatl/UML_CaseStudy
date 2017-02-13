package fr.emn.atlanmod.uml.casestudy.rewrite;

import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * @author zcheng
 */
@SuppressWarnings("all")
public class OCLWDGenerator {
  protected static HashSet<PropertyCallExp> _wd(final EObject e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final OperationCallExp e) {
    HashSet<PropertyCallExp> r = new HashSet<PropertyCallExp>();
    OCLExpression _ownedSource = e.getOwnedSource();
    final HashSet<PropertyCallExp> src = OCLWDGenerator.wd(_ownedSource);
    List<OCLExpression> _ownedArguments = e.getOwnedArguments();
    final Function1<OCLExpression, HashSet<PropertyCallExp>> _function = (OCLExpression arg) -> {
      return OCLWDGenerator.wd(arg);
    };
    final List<HashSet<PropertyCallExp>> args = ListExtensions.<OCLExpression, HashSet<PropertyCallExp>>map(_ownedArguments, _function);
    HashSet<PropertyCallExp> args_res = new HashSet<PropertyCallExp>();
    if (((!Objects.equal(args, null)) && (args.size() > 0))) {
      for (final HashSet<PropertyCallExp> arg : args) {
        args_res.addAll(arg);
      }
    }
    r.addAll(src);
    r.addAll(args_res);
    return r;
  }
  
  protected static HashSet<PropertyCallExp> _wd(final PropertyCallExp e) {
    HashSet<PropertyCallExp> r = new HashSet<PropertyCallExp>();
    r.add(e);
    OCLExpression _ownedSource = e.getOwnedSource();
    HashSet<PropertyCallExp> _wd = OCLWDGenerator.wd(_ownedSource);
    r.addAll(_wd);
    return r;
  }
  
  protected static HashSet<PropertyCallExp> _wd(final OppositePropertyCallExp e) {
    HashSet<PropertyCallExp> r = new HashSet<PropertyCallExp>();
    OCLExpression _ownedSource = e.getOwnedSource();
    HashSet<PropertyCallExp> _wd = OCLWDGenerator.wd(_ownedSource);
    r.addAll(_wd);
    return r;
  }
  
  protected static HashSet<PropertyCallExp> _wd(final VariableExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final IteratorVariable e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final Operation e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final Type e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final IntegerLiteralExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final BooleanLiteralExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final EnumLiteralExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final NullLiteralExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final TypeExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final CollectionLiteralExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final UnlimitedNaturalLiteralExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final LetExp e) {
    return new HashSet<PropertyCallExp>();
  }
  
  protected static HashSet<PropertyCallExp> _wd(final IteratorExp e) {
    OCLExpression _ownedSource = e.getOwnedSource();
    final HashSet<PropertyCallExp> src = OCLWDGenerator.wd(_ownedSource);
    OCLExpression _ownedBody = e.getOwnedBody();
    final HashSet<PropertyCallExp> body = OCLWDGenerator.wd(_ownedBody);
    HashSet<PropertyCallExp> r = src;
    r.addAll(body);
    return r;
  }
  
  protected static HashSet<PropertyCallExp> _wd(final IfExp e) {
    OCLExpression _ownedCondition = e.getOwnedCondition();
    HashSet<PropertyCallExp> r = OCLWDGenerator.wd(_ownedCondition);
    OCLExpression _ownedThen = e.getOwnedThen();
    HashSet<PropertyCallExp> _wd = OCLWDGenerator.wd(_ownedThen);
    r.addAll(_wd);
    OCLExpression _ownedElse = e.getOwnedElse();
    HashSet<PropertyCallExp> _wd_1 = OCLWDGenerator.wd(_ownedElse);
    r.addAll(_wd_1);
    return r;
  }
  
  public static HashSet<PropertyCallExp> wd(final EObject e) {
    if (e instanceof IntegerLiteralExp) {
      return _wd((IntegerLiteralExp)e);
    } else if (e instanceof OppositePropertyCallExp) {
      return _wd((OppositePropertyCallExp)e);
    } else if (e instanceof PropertyCallExp) {
      return _wd((PropertyCallExp)e);
    } else if (e instanceof UnlimitedNaturalLiteralExp) {
      return _wd((UnlimitedNaturalLiteralExp)e);
    } else if (e instanceof BooleanLiteralExp) {
      return _wd((BooleanLiteralExp)e);
    } else if (e instanceof IteratorExp) {
      return _wd((IteratorExp)e);
    } else if (e instanceof NullLiteralExp) {
      return _wd((NullLiteralExp)e);
    } else if (e instanceof OperationCallExp) {
      return _wd((OperationCallExp)e);
    } else if (e instanceof CollectionLiteralExp) {
      return _wd((CollectionLiteralExp)e);
    } else if (e instanceof EnumLiteralExp) {
      return _wd((EnumLiteralExp)e);
    } else if (e instanceof IteratorVariable) {
      return _wd((IteratorVariable)e);
    } else if (e instanceof IfExp) {
      return _wd((IfExp)e);
    } else if (e instanceof LetExp) {
      return _wd((LetExp)e);
    } else if (e instanceof Operation) {
      return _wd((Operation)e);
    } else if (e instanceof TypeExp) {
      return _wd((TypeExp)e);
    } else if (e instanceof VariableExp) {
      return _wd((VariableExp)e);
    } else if (e instanceof Type) {
      return _wd((Type)e);
    } else if (e != null) {
      return _wd(e);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(e).toString());
    }
  }
}
