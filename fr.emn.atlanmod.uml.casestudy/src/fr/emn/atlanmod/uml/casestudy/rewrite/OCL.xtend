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
	static private HashSet<String> wdSetInner = new HashSet<String>
	static public HashMap<String, EObject> bvMap = new HashMap<String, EObject>
	
	// dispatcher
	def static dispatch String gen(EObject e) '''
		// We dont understand «e.eClass.name»'''
	
	// dispatcher
	def static dispatch String gen(OCLExpression e) '''
		// We dont understand ocl expression «e.eClass.name»'''
	
	// TODO size, tostring, =(isocltype) some operation depends on type print differently
	// TODO some collection is not printing properly, e.g. union
	def static dispatch String gen(OperationCallExp e) '''
	«val op = if (e.referredOperation.name =="") e.name else e.referredOperation.name»
	«val src = gen(e.ownedSource)»
	«val args = if(e.ownedArguments.size!=0 && op!=null) e.ownedArguments.map(arg |  gen(arg) ).join(op) else ""»
	«val args_dot = if(e.ownedArguments.size!=0 && op!=null) e.ownedArguments.map(arg |  gen(arg) ).join(',') else ""»«
	IF op=="not" || op=="abs"
	»«op»(«gen(e.ownedSource)»)«
	ELSEIF op=="+" || op =="-"  || op =="*" || op =="/" 
	|| op =="=" || op =="<>" || op ==">" || op =="<" || op ==">=" || op =="<=" 
	|| op =="implies"  || op =="and"  || op =="or" || op =="div" || op =="mod" || op=="xor" 
	»«src» «op» «args»«
	ELSEIF op=="size" || op=="flatten"  || op=="allInstances"   || op=="asBag"  || op=="asOrderedSet"  
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
	|| op=="at"    
	|| op=="append" || op=="appendAll"  || op=="prepend"  || op=="prependAll"  || op=="reverse" || op=="union" 
	|| op=="is" || op=="excludesAll" 
	»«src»->«op»(«args_dot»)«
	ELSE»«src».«op»(«args_dot»)«
	ENDIF»'''
	
	def static dispatch String gen(PropertyCallExp e) '''«gen(e.ownedSource)».«e.referredProperty.name»'''
	
	def static dispatch String gen(OppositePropertyCallExp e) '''«gen(e.ownedSource)».«e.referredProperty.name»'''
	
	

	
	def static dispatch String gen(VariableExp e) '''
	«val itName = OCL2ATL.genIteratorName(gen(e.type))»«
		if (e.isImplicit) {if (!bvMap.keySet.contains(itName)) {itName} else itName+e.hashCode} else if (e.referredVariable.name=="self") itName else e.referredVariable.name
	»'''
	
	// OCL2ATL.genIteratorName(gen(e.type))
	//TODO var name shadowing: Bug: redefinition_context_valid, deployment_target, non_leaf_redefinition
	def static dispatch String gen(IteratorVariable e) '''
	«val itName = e.name»
	«val hashName = itName+e.hashCode»«
	IF (bvMap.keySet.contains(itName) && bvMap.get(itName) == e)»«itName»«
	ELSEIF (bvMap.keySet.contains(itName) && bvMap.get(itName) != e)»«
		IF bvMap.keySet.contains(hashName)»
		«hashName»«
		ELSE»
		«hashName»«{bvMap.put(hashName, e);null}»«
		ENDIF»«
	ELSEIF !(bvMap.keySet.contains(itName))»«itName»«{bvMap.put(e.name, e);null}»«
	ENDIF
	»'''
	
	def static dispatch String gen(Operation e) '''«e.name»'''
	
	def static dispatch String gen(Type e) '''«e.name»'''
	
	def static dispatch String gen(IntegerLiteralExp e) '''«e.integerSymbol»'''
	
	def static dispatch String gen(BooleanLiteralExp e) '''«e.booleanSymbol»'''
	
	// TODO where is the model name before its type?
	def static dispatch String gen(EnumLiteralExp e) '''«e.type.name».«e.referredLiteral.name»'''
	
	def static dispatch String gen(NullLiteralExp e) '''OclUndefined'''
	
	def static dispatch String gen(TypeExp e) '''«e.referredType.toString().replace("::", "!")»'''
	
	// TODO ATL supported?
	def static dispatch String gen(CollectionLiteralExp e) '''Sequence{}'''
	
	// TODO dont know what is this means, put it to *. since sometimes is set to 1, see `multiplicity_of_output`
	def static dispatch String gen(UnlimitedNaturalLiteralExp e) '''*'''
	
	def static dispatch String gen(LetExp e) '''let «e.ownedVariable.name» : «e.ownedVariable.type.toString().replace("::", "!")» = 
  «gen(e.ownedVariable.ownedInit)» in 
    «gen(e.ownedIn)»'''

	def static dispatch String gen(IteratorExp e) '''
	«val args_dot = if(e.ownedIterators.size!=0) e.ownedIterators.map(arg |  gen(arg) ).join(',') else ""»
	«val wdExprs = OCLWDGenerator.wd(e.ownedBody)»
	«gen(e.ownedSource)»->«e.referredIteration.name»(«if (e.ownedIterators.size!=0) args_dot + "|" else ""»
	«FOR expr: wdExprs»
		«FOR itor : e.ownedIterators»
		  	«IF OCL2ATL.printAtHere(expr, gen(itor)) && !wdSetInner.contains(OCL.gen(expr))»«
		  		{wdSetInner.add(gen(expr));null}»«
		  		IF !OCL.isPrimtive(expr)»«
		  			IF !isCollection(expr)»
		  			«expr.type.toString.replace("::", "!")».allInstances()->contains(«OCL.gen(expr)») implies 
		  			«ELSE»
		  			«gen(expr)»->size()>0 implies 
		  			«ENDIF»«
		  		ENDIF»«
		  	ENDIF»«
	  	ENDFOR»«
  	ENDFOR»«{wdSetInner.clear();null}» 
  «gen(e.ownedBody)»)'''
  
    def static dispatch String gen(IfExp e) '''
if («gen(e.ownedCondition)») then 
  «gen(e.ownedThen)»
else 
  «gen(e.ownedElse)»
endif'''

	
	def static isConsistentVariable(HashMap<String, EObject> map, EObject exp) {
		if(exp instanceof VariableExp){
			val itName = OCL2ATL.genIteratorName(gen(exp.type))
			return  !map.containsKey(itName) || map.get(itName) == exp
		}else if ( exp instanceof IteratorVariable){
			val itName = OCL2ATL.genIteratorName(gen(exp.type))
			return  !map.containsKey(itName) || map.get(itName) == exp
		}else{
			return true;
		}
		
	}
	
	
	def static boolean isPrimtive(PropertyCallExp e) {

		if(OCL.gen(e.type) == "String" || OCL.gen(e.type) == "Integer" 
			|| OCL.gen(e.type) == "Boolean" || OCL.gen(e.type) == "Real"
		){
			return true
		}else if(e.type instanceof Enumeration){
			return true
		}else{
			return false
		}
	}
	
	def static boolean isCollection(PropertyCallExp e) {

		if(OCL.gen(e.type) == "Set" || OCL.gen(e.type) == "OrderedSet" 
		){
			return true
		}else{
			return false
		}
	}
	
}