package fr.emn.atlanmod.uml.casestudy.core;

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
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class OCL2ATLHelper {
  private static Resource ocl_resource;
  
  public static void rewriteEntry(final URI input) {
    final URI inputURI = URI.createFileURI("./resources/uml.2.5.full.ocl.oclas");
    OCL2ATLHelper.doEMFSetup(inputURI);
    EList<EObject> _contents = OCL2ATLHelper.ocl_resource.getContents();
    for (final EObject content : _contents) {
      InputOutput.<String>println(content.eClass().toString());
    }
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
      _xblockexpression = OCL2ATLHelper.ocl_resource = rs.getResource(oclPath, true);
    }
    return _xblockexpression;
  }
}
