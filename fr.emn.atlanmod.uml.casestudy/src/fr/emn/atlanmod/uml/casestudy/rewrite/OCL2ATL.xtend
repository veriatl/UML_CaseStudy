package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.ExpressionInOCL
import org.eclipse.ocl.pivot.Model
import org.eclipse.ocl.pivot.NullLiteralExp
import org.eclipse.ocl.pivot.Package
import org.eclipse.ocl.pivot.PropertyCallExp
import org.eclipse.ocl.pivot.VariableExp
import java.util.HashSet

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
			helper context «model»!«clazz.name» def: «inv.name»(): Boolean = 
			  «model»!«clazz.name».allInstances()->forAll(«genIteratorName(clazz.name)» |  «OCL.bvMap.put(genIteratorName(clazz.name), null)»
			  	«FOR e: wdExprs»
				  	«IF printAtHere(e, genIteratorName(clazz.name)) && !wdSet.contains(OCL.gen(e))»«
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
	
	def static String genIteratorName(String clazz) {
		var String rtn="";
		
		for(var i = 0; i<clazz.length; i++){
			if(Character.isUpperCase(clazz.charAt(i))){
				rtn += Character.toLowerCase(clazz.charAt(i))
			}
		}
		
		return rtn;
	}
	
	
	def static boolean printAtHere(PropertyCallExp e, String v) {
		var boolean r = false;
		
		if (e.ownedSource instanceof VariableExp ){
			if (OCL.gen(e.ownedSource) == v){
				r = true
			}
		}else if(e.ownedSource instanceof PropertyCallExp){
			r = printAtHere(e.ownedSource as PropertyCallExp, v)
		}
		
		return r;
	}
	

	
}