package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.BooleanLiteralExp
import org.eclipse.ocl.pivot.EnumLiteralExp
import org.eclipse.ocl.pivot.IfExp
import org.eclipse.ocl.pivot.IntegerLiteralExp
import org.eclipse.ocl.pivot.IteratorExp
import org.eclipse.ocl.pivot.IteratorVariable
import org.eclipse.ocl.pivot.LetExp
import org.eclipse.ocl.pivot.NullLiteralExp
import org.eclipse.ocl.pivot.OCLExpression
import org.eclipse.ocl.pivot.Operation
import org.eclipse.ocl.pivot.OperationCallExp
import org.eclipse.ocl.pivot.PropertyCallExp
import org.eclipse.ocl.pivot.Type
import org.eclipse.ocl.pivot.TypeExp
import org.eclipse.ocl.pivot.VariableExp

class OCL {
	// dispatcher
	def static dispatch String gen(EObject e, HashMap<String, VariableExp> consistency) '''
		// We dont understand «e.eClass.name»'''
	
	// dispatcher
	def static dispatch String gen(OCLExpression e, HashMap<String, VariableExp> consistency) '''
		// We dont understand ocl expression «e.eClass.name»'''
	
	// TODO size, tostring, some operation depends on type print differentlyy
	def static dispatch String gen(OperationCallExp e, HashMap<String, VariableExp> consistency) '''
	«val op = e.referredOperation.name»
	«val src = gen(e.ownedSource, consistency)»
	«val args = if(e.ownedArguments.size!=0 && op!=null) e.ownedArguments.map(arg |  gen(arg, consistency) ).join(op) else ""»
	«val args_dot = if(e.ownedArguments.size!=0 && op!=null) e.ownedArguments.map(arg |  gen(arg, consistency) ).join(',') else ""»«
	IF op=="not" || op=="abs"
	»«op»(«gen(e.ownedSource, consistency)»)«
	ELSEIF op=="+" || op =="-"  || op =="*" || op =="/" 
	|| op =="=" || op =="<>" || op ==">" || op =="<" || op ==">=" || op =="<=" 
	|| op =="implies"  || op =="and"  || op =="or" || op =="div" || op =="mod" || op =="implies"
	|| op =="implies"  || op =="and"  || op =="or" || op =="implies" || op =="implies" || op =="implies"
	»«src» «op» «args»«
	ELSEIF op=="size" || op=="flatten"  || op=="allInstances"  || op=="xor"  || op=="asBag"  || op=="asOrderedSet"  
	|| op=="asSequence"  || op=="asSet"  || op=="isEmpty"  || op=="max" || op=="min"  || op=="notEmpty"  || op=="oclIsUndefined"  || op=="oclType" 
	|| op=="first"  
	|| op=="oclAsSet" 
	»«src»->«op»()«
	ELSEIF op=="oclIsUndefined"  || op=="oclIsInvalid" 
	|| op=="toBoolean"  || op=="toInteger"  || op=="toLower"  || op=="toLowerCase" 
	|| op=="toString"  || op=="toUpper"  || op=="toUpperCase"  
	»«src».«op»()«
	ELSEIF op=="oclAsType" || op=="oclIsKindOf" || op=="oclIsTypeOf" 
	|| op=="concat" || op=="endsWith"  || op=="indexOf"  || op=="lastIndexOf"  || op=="startsWith" || op=="substring" 	
	|| op=="conformsTo"
	»«src».«op»(«args_dot»)«
	ELSEIF op=="excluding" || op=="excludingAll" || op=="including" || op=="includingAll"  || op=="selectByKind"  || op=="selectByKind"  || op=="selectByType"  
	|| op=="count"  || op=="excludes"  || op=="includes"  || op=="includesAll" || op=="intersection"  
	|| op=="at"  || op=="indexOf"  || op=="last" 
	|| op=="append" || op=="appendAll"  || op=="prepend"  || op=="prependAll"  || op=="reverse" 
	|| op=="is" || op=="excludesAll" 
	»«src»->«op»(«args_dot»)«
	ELSE»«src».«op»(«args_dot»)«
	ENDIF»'''
	
	def static dispatch String gen(PropertyCallExp e, HashMap<String, VariableExp> consistency) '''«gen(e.ownedSource, consistency)».«e.referredProperty.name»'''
	
	//TODO use a systematic approach to generate new bound variable
	def static dispatch String gen(VariableExp e, HashMap<String, VariableExp> consistency) '''«
		if (e.isIsImplicit) {if (isConsistentVariable(consistency, e)) {getIteratorName(e)} else getIteratorName(e)+e.hashCode} else e.referredVariable.name
	»'''
	
	def static dispatch String gen(IteratorVariable e, HashMap<String, VariableExp> consistency) '''«
		if (e.isIsImplicit) OCL2ATL.genIteratorName(gen(e.type, null)) else e.name
	»'''
	
	def static dispatch String gen(Operation e, HashMap<String, VariableExp> consistency) '''«e.name»'''
	
	def static dispatch String gen(Type e, HashMap<String, VariableExp> consistency) '''«e.name»'''
	
	def static dispatch String gen(IntegerLiteralExp e, HashMap<String, VariableExp> consistency) '''«e.integerSymbol»'''
	
	def static dispatch String gen(BooleanLiteralExp e, HashMap<String, VariableExp> consistency) '''«e.booleanSymbol»'''
	
	def static dispatch String gen(EnumLiteralExp e, HashMap<String, VariableExp> consistency) '''«e.type.name».«e.referredLiteral.name»'''
	
	def static dispatch String gen(NullLiteralExp e, HashMap<String, VariableExp> consistency) '''OclUndefined'''
	
	def static dispatch String gen(TypeExp e, HashMap<String, VariableExp> consistency) '''«e.referredType.toString().replace("::", "!")»'''
	
	def static dispatch String gen(LetExp e, HashMap<String, VariableExp> consistency) '''let «e.ownedVariable.name» : «e.ownedVariable.type.toString().replace("::", "!")» = 
  «gen(e.ownedVariable.ownedInit, consistency)» in 
    «gen(e.ownedIn, consistency)»'''


	def static dispatch String gen(IteratorExp e, HashMap<String, VariableExp> consistency) '''
	«val args_dot = if(e.ownedIterators.size!=0) e.ownedIterators.map(arg |  gen(arg, consistency) ).join(',') else ""»
	«gen(e.ownedSource, consistency)»->«e.referredIteration.name»(«if (e.ownedIterators.size!=0) args_dot + "|" else ""» 
  «gen(e.ownedBody, consistency)»)'''
  
    def static dispatch String gen(IfExp e, HashMap<String, VariableExp> consistency) '''
if («gen(e.ownedCondition, consistency)») then 
  «gen(e.ownedThen, consistency)»
else 
  «gen(e.ownedElse, consistency)»
endif'''

	
	def static isConsistentVariable(HashMap<String, VariableExp> map, VariableExp exp) {
		val itName = getIteratorName(exp)
		return  !map.containsKey(itName) || map.get(itName) == exp
	}
	
	def static getIteratorName(VariableExp exp) {
		val tp = exp.type
		val clazz = gen(tp, null)
		val itName = OCL2ATL.genIteratorName(clazz)
		return itName
	}
	
}