package fr.emn.atlanmod.uml.casestudy.rewrite;

import java.util.Arrays;
import java.util.HashMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.IntegerLiteralExp;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.OperationCallExp;
import org.eclipse.ocl.pivot.Property;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.VariableDeclaration;
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class OCL {
  protected static String _gen(final EObject o, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We don\'t understand ");
    EClass _eClass = o.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected static String _gen(final OCLExpression o, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We don\'t understand ocl expression ");
    EClass _eClass = o.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected static String _gen(final OperationCallExp o, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    OCLExpression _ownedSource = o.getOwnedSource();
    String _gen = OCL.gen(_ownedSource, consistency);
    _builder.append(_gen, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    return _builder.toString();
  }
  
  protected static String _gen(final PropertyCallExp v, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("name:");
    String _name = v.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    _builder.append("self:");
    Property _referredProperty = v.getReferredProperty();
    String _name_1 = _referredProperty.getName();
    _builder.append(_name_1, "");
    _builder.newLineIfNotEmpty();
    _builder.append("type:");
    VariableDeclaration _xifexpression = null;
    OCLExpression _ownedSource = v.getOwnedSource();
    if ((_ownedSource instanceof VariableExp)) {
      OCLExpression _ownedSource_1 = v.getOwnedSource();
      _xifexpression = ((VariableExp) _ownedSource_1).getReferredVariable();
    }
    _builder.append(_xifexpression, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder.toString();
  }
  
  protected static String _gen(final VariableExp v, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = v.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder.toString();
  }
  
  protected static String _gen(final IntegerLiteralExp i, final HashMap<String, VariableExp> consistency) {
    StringConcatenation _builder = new StringConcatenation();
    Number _integerSymbol = i.getIntegerSymbol();
    _builder.append(_integerSymbol, "");
    return _builder.toString();
  }
  
  public static String gen(final EObject i, final HashMap<String, VariableExp> consistency) {
    if (i instanceof IntegerLiteralExp) {
      return _gen((IntegerLiteralExp)i, consistency);
    } else if (i instanceof PropertyCallExp) {
      return _gen((PropertyCallExp)i, consistency);
    } else if (i instanceof OperationCallExp) {
      return _gen((OperationCallExp)i, consistency);
    } else if (i instanceof VariableExp) {
      return _gen((VariableExp)i, consistency);
    } else if (i instanceof OCLExpression) {
      return _gen((OCLExpression)i, consistency);
    } else if (i != null) {
      return _gen(i, consistency);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(i, consistency).toString());
    }
  }
}
