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
    final URI inputURI = URI.createFileURI("./resources/UML.normalize.ocl.oclas");
    StandAlone.doEMFSetup(inputURI);
    String res = "";
    final URI outputURI = URI.createFileURI("./resources/UML.normalize.ocl.atl");
    String _res = res;
    res = (_res + "module UMLCopierContract;\n");
    String _res_1 = res;
    res = (_res_1 + "create OUT : UMLs from IN : UML;\n");
    EList<EObject> _contents = StandAlone.ocl_resource.getContents();
    for (final EObject eobject : _contents) {
      String _res_2 = res;
      String _rewrite = OCL2ATL.rewrite(eobject);
      res = (_res_2 + _rewrite);
    }
    URIs.write(outputURI, res);
  }
  
  public static void doEMFSetup(final URI oclPath) {
    EPackage.Registry.INSTANCE.put(PivotPackage.eNS_URI, PivotPackage.eINSTANCE);
    Map<String, Object> _extensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put("xmi", _xMIResourceFactoryImpl);
    Map<String, Object> _extensionToFactoryMap_1 = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
    OCLASResourceFactory _instance = OCLASResourceFactory.getInstance();
    _extensionToFactoryMap_1.put("oclas", _instance);
    final ResourceSetImpl rs = new ResourceSetImpl();
    Resource _resource = rs.getResource(oclPath, true);
    StandAlone.ocl_resource = _resource;
    URI _createFileURI = URI.createFileURI("./resources/UML.ecore.oclas");
    final Resource ecore_resource = rs.getResource(_createFileURI, true);
    URI _createFileURI_1 = URI.createFileURI("./resources/OCL-2.5.oclas");
    final Resource lib_resource = rs.getResource(_createFileURI_1, true);
    URI _createURI = URI.createURI("UML.ecore.oclas");
    ecore_resource.setURI(_createURI);
    URI _createURI_1 = URI.createURI("http://www.eclipse.org/ocl/2015/Library.oclas");
    lib_resource.setURI(_createURI_1);
  }
  
  public static EPackage loadEcore(final String metamodelPath) throws Exception {
    final ResourceSetImpl rs = new ResourceSetImpl();
    Resource.Factory.Registry _resourceFactoryRegistry = rs.getResourceFactoryRegistry();
    Map<String, Object> _extensionToFactoryMap = _resourceFactoryRegistry.getExtensionToFactoryMap();
    EcoreResourceFactoryImpl _ecoreResourceFactoryImpl = new EcoreResourceFactoryImpl();
    _extensionToFactoryMap.put("ecore", _ecoreResourceFactoryImpl);
    URI _createFileURI = URI.createFileURI(metamodelPath);
    final Resource r = rs.getResource(_createFileURI, true);
    EList<EObject> _contents = r.getContents();
    final EObject eObject = _contents.get(0);
    if ((eObject instanceof EPackage)) {
      final EPackage p = ((EPackage) eObject);
      return p;
    }
    throw new Exception("reading metamodel fails hard! abort...");
  }
}
