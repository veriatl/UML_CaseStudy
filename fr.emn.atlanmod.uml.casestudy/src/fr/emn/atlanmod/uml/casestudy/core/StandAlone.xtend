
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
import fr.emn.atlanmod.uml.casestudy.rewrite.rewriterOCL2ATL
import fr.emn.atlanmod.uml.casestudy.util.URIs

class StandAlone{
	
	static Resource	ocl_resource;
	
	
	def static void main(String[] args) {
		val inputURI = URI.createFileURI("./resources/UML.oclas")
		doEMFSetup(inputURI)
		
		var String res = "";
		val outputURI = URI.createFileURI("./resources/UML.ocl.atl")
		
		for (EObject eobject : ocl_resource.getContents()) {
			res += rewriterOCL2ATL.rewrite(eobject);
		}
		
		
		URIs.write(outputURI, res); 
		
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
