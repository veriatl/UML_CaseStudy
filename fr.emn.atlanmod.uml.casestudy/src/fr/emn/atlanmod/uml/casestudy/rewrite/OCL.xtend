package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.BooleanLiteralExp
import org.eclipse.ocl.pivot.CollectionLiteralExp
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
import org.eclipse.ocl.pivot.OppositePropertyCallExp
import org.eclipse.ocl.pivot.PropertyCallExp
import org.eclipse.ocl.pivot.Type
import org.eclipse.ocl.pivot.TypeExp
import org.eclipse.ocl.pivot.UnlimitedNaturalLiteralExp
import org.eclipse.ocl.pivot.VariableExp
import java.util.HashSet
import org.eclipse.ocl.pivot.Enumeration

class OCL {
	static private HashSet<String> wdSet = new HashSet<String>
	
	// dispatcher
	def static dispatch String gen(EObject e, HashMap<String, VariableExp> consistency) '''
		// We dont understand «e.eClass.name»'''
	
	// dispatcher
	def static dispatch String gen(OCLExpression e, HashMap<String, VariableExp> consistency) '''
		// We dont understand ocl expression «e.eClass.name»'''
	
	// TODO size, tostring, =(isocltype) some operation depends on type print differently
	// TODO some collection is not printing properly, e.g. union
	def static dispatch String gen(OperationCallExp e, HashMap<String, VariableExp> consistency) '''
	«val op = if (e.referredOperation.name =="") e.name else e.referredOperation.name»
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
	|| op=="first"  || op=="last" 
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
	|| op=="at"  || op=="indexOf"  
	|| op=="append" || op=="appendAll"  || op=="prepend"  || op=="prependAll"  || op=="reverse" || op=="union" 
	|| op=="is" || op=="excludesAll" 
	»«src»->«op»(«args_dot»)«
	ELSE»«src».«op»(«args_dot»)«
	ENDIF»'''
	
	def static dispatch String gen(PropertyCallExp e, HashMap<String, VariableExp> consistency) '''«gen(e.ownedSource, consistency)».«e.referredProperty.name»'''
	
	def static dispatch String gen(OppositePropertyCallExp e, HashMap<String, VariableExp> consistency) '''«gen(e.ownedSource, consistency)».«e.referredProperty.name»'''
	
	
	//TODO use a systematic approach to generate new bound variable
	//TODO Bug: redefinition_context_valid
	def static dispatch String gen(VariableExp e, HashMap<String, VariableExp> consistency) '''«
		if (e.isIsImplicit) {if (isConsistentVariable(consistency, e)) {getIteratorName(e)} else getIteratorName(e)+e.hashCode} else if (e.referredVariable.name=="self") getIteratorName(e) else e.referredVariable.name
	»'''
	
	def static dispatch String gen(IteratorVariable e, HashMap<String, VariableExp> consistency) '''«
		if (e.isIsImplicit) OCL2ATL.genIteratorName(gen(e.type, null)) else e.name
	»'''
	
	def static dispatch String gen(Operation e, HashMap<String, VariableExp> consistency) '''«e.name»'''
	
	def static dispatch String gen(Type e, HashMap<String, VariableExp> consistency) '''«e.name»'''
	
	def static dispatch String gen(IntegerLiteralExp e, HashMap<String, VariableExp> consistency) '''«e.integerSymbol»'''
	
	def static dispatch String gen(BooleanLiteralExp e, HashMap<String, VariableExp> consistency) '''«e.booleanSymbol»'''
	
	// TODO where is the model name before its type?
	def static dispatch String gen(EnumLiteralExp e, HashMap<String, VariableExp> consistency) '''«e.type.name».«e.referredLiteral.name»'''
	
	def static dispatch String gen(NullLiteralExp e, HashMap<String, VariableExp> consistency) '''OclUndefined'''
	
	def static dispatch String gen(TypeExp e, HashMap<String, VariableExp> consistency) '''«e.referredType.toString().replace("::", "!")»'''
	
	// TODO ATL supported?
	def static dispatch String gen(CollectionLiteralExp e, HashMap<String, VariableExp> consistency) '''Sequence{}'''
	
	// TODO dont know what is this means, put it to *. since sometimes is set to 1, see `multiplicity_of_output`
	def static dispatch String gen(UnlimitedNaturalLiteralExp e, HashMap<String, VariableExp> consistency) '''*'''
	
	def static dispatch String gen(LetExp e, HashMap<String, VariableExp> consistency) '''let «e.ownedVariable.name» : «e.ownedVariable.type.toString().replace("::", "!")» = 
  «gen(e.ownedVariable.ownedInit, consistency)» in 
    «gen(e.ownedIn, consistency)»'''

	def static dispatch String gen(IteratorExp e, HashMap<String, VariableExp> consistency) '''
	«val args_dot = if(e.ownedIterators.size!=0) e.ownedIterators.map(arg |  gen(arg, consistency) ).join(',') else ""»
	«val wdExprs = OCLWDGenerator.wd(e.ownedBody)»
	«gen(e.ownedSource, consistency)»->«e.referredIteration.name»(«if (e.ownedIterators.size!=0) args_dot + "|" else ""»
	«FOR expr: wdExprs»
		«FOR itor : e.ownedIterators»
		  	«IF OCL2ATL.printAtHere(expr, gen(itor, consistency)) && !wdSet.contains(OCL.gen(expr, new HashMap))»«
		  		{wdSet.add(OCL.gen(expr, new HashMap));null}»«
		  		IF !OCL.isPrimtive(expr)»«
		  			IF !OCL.isCollection(expr)»
		  			«expr.type.toString.replace("::", "!")».allInstances()->contains(«OCL.gen(expr, new HashMap)») implies 
		  			«ELSE»
		  			«OCL.gen(expr, new HashMap)»->size()>0 implies 
		  			«ENDIF»«
		  		ENDIF»«
		  	ENDIF»«
	  	ENDFOR»«
  	ENDFOR»«{wdSet.clear();null}» 
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
	
	def static boolean isPrimtive(PropertyCallExp e) {

		if(OCL.gen(e.type, null) == "String" || OCL.gen(e.type, null) == "Integer" 
			|| OCL.gen(e.type, null) == "Boolean" || OCL.gen(e.type, null) == "Real"
		){
			return true
		}else if(e.type instanceof Enumeration){
			return true
		}else{
			return false
		}
	}
	
	def static boolean isCollection(PropertyCallExp e) {

		if(OCL.gen(e.type, null) == "Set" || OCL.gen(e.type, null) == "OrderedSet" 
		){
			return true
		}else{
			return false
		}
	}
	
}