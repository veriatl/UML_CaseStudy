package fr.emn.atlanmod.uml.casestudy.core;

import java.util.Map;
import org.eclipse.emf.common.util.URI;
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
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field rewriterOCL2ATL is undefined"
      + "\nrewrite cannot be resolved");
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
