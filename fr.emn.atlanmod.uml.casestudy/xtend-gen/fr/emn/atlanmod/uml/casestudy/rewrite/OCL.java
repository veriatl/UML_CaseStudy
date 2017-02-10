package fr.emn.atlanmod.uml.casestudy.rewrite;

import java.util.Arrays;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.OperationCallExp;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class OCL {
  protected static String _gen(final EObject o) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We don\'t understand ");
    EClass _eClass = o.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected static String _gen(final OCLExpression o) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// We don\'t understand ocl expression ");
    EClass _eClass = o.eClass();
    String _name = _eClass.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected static String _gen(final OperationCallExp o) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = o.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder.toString();
  }
  
  public static String gen(final EObject o) {
    if (o instanceof OperationCallExp) {
      return _gen((OperationCallExp)o);
    } else if (o instanceof OCLExpression) {
      return _gen((OCLExpression)o);
    } else if (o != null) {
      return _gen(o);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(o).toString());
    }
  }
}
