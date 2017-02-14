package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.ExpressionInOCL
import org.eclipse.ocl.pivot.Model
import org.eclipse.ocl.pivot.NullLiteralExp
import org.eclipse.ocl.pivot.Package

class OCL2ATL {
	
	static private HashSet<String> wdSet = new HashSet<String>
	
	
	
	public static String model = "UML"
	// dispatcher
	def static dispatch String rewrite(EObject o) '''
		// We don't understand «o.eClass.name»
	'''
	
	def static dispatch String rewrite(Model m) '''
	«FOR pac : m.ownedPackages»
		«rewrite(pac)»	
	«ENDFOR»
	'''
	
	/**
	 * ps: we don't generate helper if the invariant body is {@code null} or it has been filter out by the projector {@code OCLProjector}
	 */
	def static dispatch String rewrite(Package p) '''	
	«FOR clazz : p.ownedClasses»
		«FOR inv : clazz.ownedInvariants»
			«IF (OCLProjector.proj((inv.ownedSpecification as ExpressionInOCL).ownedBody)
				&& !((inv.ownedSpecification as ExpressionInOCL).ownedBody instanceof NullLiteralExp)
			)»«
			val wdExprs = OCLWDGenerator.wd((inv.ownedSpecification as ExpressionInOCL).ownedBody)
			»
			--@pre
			helper context «model»!«clazz.name» def: pre_«inv.name»(): Boolean = --«inv.name»
			  «model»!«clazz.name».allInstances()->forAll(«OCL.genIteratorName(clazz.name)» |  «OCL.bvMap.put(OCL.genIteratorName(clazz.name), null)»
			  	«FOR e: wdExprs»
				  	«IF OCL.printAtHere(e, OCL.genIteratorName(clazz.name)) && !wdSet.contains(OCL.gen(e))»«
				  		{wdSet.add(OCL.gen(e));null}»«
				  		IF !OCL.isPrimtive(e)»«
				  			IF !OCL.isCollection(e)»
				  			«e.type.toString().replace("::", "!")».allInstances()->includes(«OCL.gen(e)») implies 
				  			«ELSE»
				  			«OCL.gen(e)»->size()>0 implies 
				  			«ENDIF»«
				  		ENDIF»«
				  	ENDIF»«
			  	ENDFOR»
			    «OCL.gen((inv.ownedSpecification as ExpressionInOCL).ownedBody)»
			); «{wdSet.clear(); OCL.bvMap.clear()}»
			
			«ENDIF»
		«ENDFOR»
	«ENDFOR»
	
	«FOR clazz : p.ownedClasses»
		«FOR inv : clazz.ownedInvariants»
			«IF (OCLProjector.proj((inv.ownedSpecification as ExpressionInOCL).ownedBody)
				&& !((inv.ownedSpecification as ExpressionInOCL).ownedBody instanceof NullLiteralExp)
			)»«
			val wdExprs = OCLWDGenerator.wd((inv.ownedSpecification as ExpressionInOCL).ownedBody)
			»
			--@post
			helper context «model»!«clazz.name» def: post_«inv.name»(): Boolean = --«inv.name»
			  «model»!«clazz.name».allInstances()->forAll(«OCL.genIteratorName(clazz.name)» |  «OCL.bvMap.put(OCL.genIteratorName(clazz.name), null)»
			  	«FOR e: wdExprs»
				  	«IF OCL.printAtHere(e, OCL.genIteratorName(clazz.name)) && !wdSet.contains(OCL.gen(e))»«
				  		{wdSet.add(OCL.gen(e));null}»«
				  		IF !OCL.isPrimtive(e)»«
				  			IF !OCL.isCollection(e)»
				  			«e.type.toString().replace("::", "!")».allInstances()->contains(«OCL.gen(e)») implies 
				  			«ELSE»
				  			«OCL.gen(e)»->size()>0 implies 
				  			«ENDIF»«
				  		ENDIF»«
				  	ENDIF»«
			  	ENDFOR»
			    «OCL.gen((inv.ownedSpecification as ExpressionInOCL).ownedBody)»
			); «{wdSet.clear(); OCL.bvMap.clear()}»
			
			«ENDIF»
		«ENDFOR»
	«ENDFOR»
	'''
	

	
	

	

	
}