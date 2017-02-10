package fr.emn.atlanmod.uml.casestudy.core;

import fr.emn.atlanmod.uml.casestudy.rewrite.OCL2ATL;
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
    final URI inputURI = URI.createFileURI("./resources/UML.ocl.oclas");
    StandAlone.doEMFSetup(inputURI);
    String res = "";
    final URI outputURI = URI.createFileURI("./resources/UML.ocl.atl");
    EList<EObject> _contents = StandAlone.ocl_resource.getContents();
    for (final EObject eobject : _contents) {
      String _res = res;
      String _rewrite = OCL2ATL.rewrite(eobject);
      res = (_res + _rewrite);
    }
    URIs.write(outputURI, res);
  }
  
  public static void doEMFSetup(final URI oclPath) {
    EPackage.Registry.INSTANCE.put(PivotPackage.eNS_URI, PivotPackage.eINSTANCE);
    Map<String, Object> _extensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put("xmi", _xMIResourceFactoryImpl);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("oclas", OCLASResourceFactory.getInstance());
    final ResourceSetImpl rs = new ResourceSetImpl();
    StandAlone.ocl_resource = rs.getResource(oclPath, true);
    final Resource ecore_resource = rs.getResource(URI.createFileURI("./resources/UML.ecore.oclas"), true);
    final Resource lib_resource = rs.getResource(URI.createFileURI("./resources/OCL-2.5.oclas"), true);
    ecore_resource.setURI(URI.createURI("UML.ecore.oclas"));
    lib_resource.setURI(URI.createURI("http://www.eclipse.org/ocl/2015/Library.oclas"));
  }
  
  public static EPackage loadEcore(final String metamodelPath) throws Exception {
    final ResourceSetImpl rs = new ResourceSetImpl();
    Map<String, Object> _extensionToFactoryMap = rs.getResourceFactoryRegistry().getExtensionToFactoryMap();
    EcoreResourceFactoryImpl _ecoreResourceFactoryImpl = new EcoreResourceFactoryImpl();
    _extensionToFactoryMap.put("ecore", _ecoreResourceFactoryImpl);
    final Resource r = rs.getResource(URI.createFileURI(metamodelPath), true);
    final EObject eObject = r.getContents().get(0);
    if ((eObject instanceof EPackage)) {
      final EPackage p = ((EPackage) eObject);
      return p;
    }
    throw new Exception("reading metamodel fails hard! abort...");
  }
}
