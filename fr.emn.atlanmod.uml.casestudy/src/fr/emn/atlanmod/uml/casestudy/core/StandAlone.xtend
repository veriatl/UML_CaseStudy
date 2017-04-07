
package fr.emn.atlanmod.uml.casestudy.core

import fr.emn.atlanmod.uml.casestudy.rewrite.OCL2ATL
import fr.emn.atlanmod.uml.casestudy.util.URIs
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.ocl.pivot.PivotPackage
import org.eclipse.ocl.pivot.internal.resource.OCLASResourceFactory

class StandAlone{
	
	val static INPUT_PATH = "./resources/inputs/UML.normalize.ocl.oclas"
	val static OUTPUT_PATH = "./resources/outputs/UML2UMLsContract.atl"
	val static ECORE_PATH = "./resources/preludes/UML.ecore.oclas"
	val static OCL_PATH = "./resources/preludes/OCL-2.5.oclstdlib.oclas"
	val static INPUT_ECORE_URI_MAPPING = "../preludes/UML.ecore.oclas"
	val static INPUT_OCL_URI_MAPPING = "http://www.eclipse.org/ocl/2015/Library.oclas"
	
	def static void main(String[] args) {
		val inputURI = URI.createFileURI(INPUT_PATH)
		val outputURI = URI.createFileURI(OUTPUT_PATH)		
		
		val ocl_resource = doEMFSetup(inputURI)
		val output = doCodeGeneration(ocl_resource)
		
		URIs.write(outputURI, output.toString); 
		
	}
	
	
	/**
	 * Code generation for the input ocl resource
	 */
	def static doCodeGeneration (Resource r) '''
	module UMLCopierContract;
	create OUT : UMLs from IN : UML;
	
	«FOR c : r.getContents»
	«OCL2ATL.rewrite(c)»
	«ENDFOR»
	'''
	
	/**
	 * Setup EMF Resource registrations, return the ocl_resouce for the input ocl abstract syntax file.
	 */
	def static doEMFSetup(URI oclPath) {
		// load OCL metamodels	
		EPackage.Registry.INSTANCE.put(PivotPackage.eNS_URI, PivotPackage.eINSTANCE);
		
		// register resource processing factories
		Resource.Factory.Registry.INSTANCE.extensionToFactoryMap.put("oclas", OCLASResourceFactory.getInstance());		
		
		// get resource from input URI
		val rs = new ResourceSetImpl		
		val ocl_resource = rs.getResource(oclPath, true)

		// initialize URI mapping for the input
		val ecore_resource = rs.getResource(URI.createFileURI(ECORE_PATH), true)
		val lib_resource = rs.getResource(URI.createFileURI(OCL_PATH), true)	
		ecore_resource.setURI(URI.createURI(INPUT_ECORE_URI_MAPPING));
		lib_resource.setURI(URI.createURI(INPUT_OCL_URI_MAPPING));
		
		return ocl_resource
	}
	
	

	
}
