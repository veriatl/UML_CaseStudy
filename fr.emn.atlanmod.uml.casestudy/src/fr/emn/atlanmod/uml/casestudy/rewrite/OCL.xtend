package fr.emn.atlanmod.uml.casestudy.rewrite

import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.OCLExpression
import org.eclipse.ocl.pivot.OperationCallExp

class OCL {
	// dispatcher
	def static dispatch String gen(EObject o) '''
		// We don't understand «o.eClass.name»
	'''
	
	// dispatcher
	def static dispatch String gen(OCLExpression o) '''
		// We don't understand ocl expression «o.eClass.name»
	'''
	
	// «o.referredOperation.name»
	def static dispatch String gen(OperationCallExp o) '''
		«o.name»
		
	'''
}