package fr.emn.atlanmod.uml.casestudy.rewrite

import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.ocl.pivot.ExpressionInOCL
import org.eclipse.ocl.pivot.Model
import org.eclipse.ocl.pivot.Package

class OCL2ATL {
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
	
	//«inv.ownedSpecification.body»
	def static dispatch String rewrite(Package p) '''
	«FOR clazz : p.ownedClasses»
		«FOR inv : clazz.ownedInvariants»
		helper context «model»!«clazz.name» def: «inv.name»(): Boolean = 
		  «model»!«clazz.name».allInstances()->forAll(«genIteratorName(clazz.name)» |
		    «OCL.gen((inv.ownedSpecification as ExpressionInOCL).ownedBody, new HashMap)»
		); 
		
		«ENDFOR»
	«ENDFOR»
	'''
	
	def static genIteratorName(String clazz) {
		var String rtn="";
		
		for(var i = 0; i<clazz.length; i++){
			if(Character.isUpperCase(clazz.charAt(i))){
				rtn += Character.toLowerCase(clazz.charAt(i))
			}
		}
		
		return rtn;
	}
	
}