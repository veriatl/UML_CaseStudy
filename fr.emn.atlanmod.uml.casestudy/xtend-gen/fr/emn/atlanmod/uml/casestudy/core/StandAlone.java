package fr.emn.atlanmod.uml.casestudy.core;

import fr.emn.atlanmod.uml.casestudy.rewrite.rewriterOCL2ATL;
import fr.emn.atlanmod.uml.casestudy.util.URIs;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.pivot.PivotPackage;
import org.eclipse.ocl.pivot.internal.resource.OCLASResourceFactory;

@SuppressWarnings("all")
public class StandAlone {
  private static Resource ocl_resource;
  
  public static void main(final String[] args) {
    final URI inputURI = URI.createFileURI("./resources/UML.oclas");
    StandAlone.doEMFSetup(inputURI);
    String res = "";
    final URI outputURI = URI.createFileURI("./resources/UML.ocl.atl");
    EList<EObject> _contents = StandAlone.ocl_resource.getContents();
    for (final EObject eobject : _contents) {
      String _res = res;
      String _rewrite = rewriterOCL2ATL.rewrite(eobject);
      res = (_res + _rewrite);
    }
    URIs.write(outputURI, res);
  }
  
  public static Resource doEMFSetup(final URI oclPath) {
    Resource _xblockexpression = null;
    {
      EPackage.Registry.INSTANCE.put(PivotPackage.eNS_URI, PivotPackage.eINSTANCE);
      Map<String, Object> _extensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
      XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
      _extensionToFactoryMap.put("xmi", _xMIResourceFactoryImpl);
      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("oclas", OCLASResourceFactory.getInstance());
      Map<String, Object> _extensionToFactoryMap_1 = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
      EcoreResourceFactoryImpl _ecoreResourceFactoryImpl = new EcoreResourceFactoryImpl();
      _extensionToFactoryMap_1.put("ecore", _ecoreResourceFactoryImpl);
      final ResourceSetImpl rs = new ResourceSetImpl();
      _xblockexpression = StandAlone.ocl_resource = rs.getResource(oclPath, true);
    }
    return _xblockexpression;
  }
}
