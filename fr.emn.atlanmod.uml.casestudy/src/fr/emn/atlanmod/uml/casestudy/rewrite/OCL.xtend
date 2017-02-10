package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.IntegerLiteralExp
import org.eclipse.ocl.pivot.OCLExpression
import org.eclipse.ocl.pivot.OperationCallExp
import org.eclipse.ocl.pivot.PropertyCallExp
import org.eclipse.ocl.pivot.VariableExp
import org.eclipse.ocl.pivot.Operation

class OCL {
	// dispatcher
	def static dispatch String gen(EObject o, HashMap<String, VariableExp> consistency) '''
		// We don't understand «o.eClass.name»
	'''
	
	// dispatcher
	def static dispatch String gen(OCLExpression o, HashMap<String, VariableExp> consistency) '''
		// We don't understand ocl expression «o.eClass.name»
	'''
	
	// «o.referredOperation.name»
	def static dispatch String gen(OperationCallExp o, HashMap<String, VariableExp> consistency) '''
		«gen(o.ownedSource, consistency)»
		«gen(o.referredOperation, consistency)»
		
	'''
	
	def static dispatch String gen(PropertyCallExp v, HashMap<String, VariableExp> consistency) '''
		name:«v.name»
		self:«v.referredProperty.name»
		type:«if(v.ownedSource instanceof VariableExp) (v.ownedSource as VariableExp).referredVariable»
	'''
	
	def static dispatch String gen(VariableExp v, HashMap<String, VariableExp> consistency) '''
		«v.name»	
	'''
	
	def static dispatch String gen(Operation o, HashMap<String, VariableExp> consistency) '''
		«o.name»	
	'''
	
	def static dispatch String gen(IntegerLiteralExp i, HashMap<String, VariableExp> consistency) '''«i.integerSymbol»'''
}