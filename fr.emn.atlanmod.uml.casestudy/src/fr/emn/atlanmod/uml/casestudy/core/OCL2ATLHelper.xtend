
package fr.emn.atlanmod.uml.casestudy.core

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.ocl.pivot.PivotPackage
import org.eclipse.ocl.pivot.internal.resource.OCLASResourceFactory
import org.eclipse.emf.ecore.EObject

class OCL2ATLHelper {
	
	static Resource	ocl_resource;
	
	def static void rewriteEntry(URI input) {
		val inputURI = URI.createFileURI("./resources/uml.2.5.full.ocl.oclas")
		doEMFSetup(inputURI)
		
		for (EObject content : ocl_resource.getContents()) {
			println(content.eClass().toString());
		}
	}
	
	def static doEMFSetup(URI oclPath) {
		// load metamodels	
		EPackage.Registry.INSTANCE.put(PivotPackage.eNS_URI, PivotPackage.eINSTANCE);
		
		// register resource processors
		Resource.Factory.Registry.INSTANCE.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl);
		Resource.Factory.Registry.INSTANCE.extensionToFactoryMap.put("oclas", OCLASResourceFactory.getInstance());
		Resource.Factory.Registry.INSTANCE.extensionToFactoryMap.put("ecore", new EcoreResourceFactoryImpl());
		
		// get resource from input URI
		val rs = new ResourceSetImpl
		ocl_resource = rs.getResource(oclPath, true)
	}
	

	
}
