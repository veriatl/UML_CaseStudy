package fr.emn.atlanmod.uml.casestudy.rewrite;

import fr.emn.atlanmod.uml.casestudy.rewrite.OCL;
import fr.emn.atlanmod.uml.casestudy.rewrite.OCLProjector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.LanguageExpression;
import org.eclipse.ocl.pivot.Model;
import org.eclipse.ocl.pivot.OCLExpression;
import org.eclipse.ocl.pivot.VariableExp;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class OCL2ATL {
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
  
  protected static String _rewrite(final org.eclipse.ocl.pivot.Package p) {
    StringConcatenation _builder = new StringConcatenation();
    {
      List<org.eclipse.ocl.pivot.Class> _ownedClasses = p.getOwnedClasses();
      for(final org.eclipse.ocl.pivot.Class clazz : _ownedClasses) {
        {
          List<Constraint> _ownedInvariants = clazz.getOwnedInvariants();
          for(final Constraint inv : _ownedInvariants) {
            {
              LanguageExpression _ownedSpecification = inv.getOwnedSpecification();
              OCLExpression _ownedBody = ((ExpressionInOCL) _ownedSpecification).getOwnedBody();
              boolean _proj = OCLProjector.proj(_ownedBody);
              if (_proj) {
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
                _builder.append(" |");
                _builder.newLineIfNotEmpty();
                _builder.append("    ");
                LanguageExpression _ownedSpecification_1 = inv.getOwnedSpecification();
                OCLExpression _ownedBody_1 = ((ExpressionInOCL) _ownedSpecification_1).getOwnedBody();
                HashMap<String, VariableExp> _hashMap = new HashMap<String, VariableExp>();
                String _gen = OCL.gen(_ownedBody_1, _hashMap);
                _builder.append(_gen, "    ");
                _builder.newLineIfNotEmpty();
                _builder.append("); ");
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
