package fr.emn.atlanmod.uml.casestudy.rewrite

import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.*


class rewriterOCL2ATL {
	static String model = "UML"
	// dispatcher
	def static dispatch String rewrite(EObject o) '''
		// We don't understand «o.eClass.name»
	'''
	
	def static dispatch String rewrite(Model m) '''
	«FOR pac : m.ownedPackages»
		«rewrite(pac)»	
	«ENDFOR»
	'''
	
	def static dispatch String rewrite(Package p) '''
	«FOR clazz : p.ownedClasses»
		«FOR inv : clazz.ownedInvariants»
		helper context «model»!«clazz.name» def: «inv.name»(): Boolean = 
		  «inv.ownedSpecification.body»
		; 
		
		«ENDFOR»
	«ENDFOR»
	'''
}