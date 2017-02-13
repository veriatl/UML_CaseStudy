/**
 * 
 */
package fr.emn.atlanmod.uml.casestudy.rewrite

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
import org.eclipse.ocl.pivot.Operation
import org.eclipse.ocl.pivot.OperationCallExp
import org.eclipse.ocl.pivot.OppositePropertyCallExp
import org.eclipse.ocl.pivot.PropertyCallExp
import org.eclipse.ocl.pivot.Type
import org.eclipse.ocl.pivot.TypeExp
import org.eclipse.ocl.pivot.UnlimitedNaturalLiteralExp
import org.eclipse.ocl.pivot.VariableExp

/**
 * @author zcheng
 * 
 */
class OCLProjector {
	// dispatcher
	def static dispatch boolean proj(EObject e) {
		return true
	}

	def static dispatch boolean proj(OperationCallExp e) {
		val op = if(e.referredOperation.name == "") e.name else e.referredOperation.name
		var r = true
		val src = proj(e.ownedSource)
		val args = e.ownedArguments.map(arg|proj(arg))
		var args_res = true
		
		if(args!=null && args.size>0){
			args_res = args.reduce(res, arg|res && arg)
		}

		if (op == null || op == "") {
			r = false
		} else if (op == "flatten" || op == "asBag" || op == "asOrderedSet" || op == "asSequence" || op == "asSet" ||
			op == "oclAsSet") {
			r = false
		} else if (op == "count") {
			r = false
		}

		return r && src && args_res
	}

	def static dispatch boolean proj(PropertyCallExp e) {
		return proj(e.ownedSource)
	}

	def static dispatch boolean proj(OppositePropertyCallExp e) {
		return proj(e.ownedSource)
	}

	def static dispatch boolean proj(VariableExp e) {
		return true
	}

	def static dispatch boolean proj(IteratorVariable e) {
		return true
	}

	// TODO double check
	def static dispatch boolean proj(Operation e) {
		return true
	}

	def static dispatch boolean proj(Type e) {
		return true
	}

	def static dispatch boolean proj(IntegerLiteralExp e) {
		return true
	}

	def static dispatch boolean proj(BooleanLiteralExp e) {
		return true
	}

	def static dispatch boolean proj(EnumLiteralExp e) {
		return true
	}

	def static dispatch boolean proj(NullLiteralExp e) {
		return true
	}

	def static dispatch boolean proj(TypeExp e) {
		return true
	}

	def static dispatch boolean proj(CollectionLiteralExp e) {
		return false
	}

	def static dispatch boolean proj(UnlimitedNaturalLiteralExp e) {
		return false
	}

	def static dispatch boolean proj(LetExp e) {
		return false
	}

	def static dispatch boolean proj(IteratorExp e) {
		val op = if(e.referredIteration.name == "") e.name else e.referredIteration.name
		var r = true
		val src = proj(e.ownedSource)
		val body = proj(e.ownedBody)

		if (op == null || op == "") {
			r = false
		} else if (op == "collect") {
			r = false
		}

		return r && src && body
	}

	def static dispatch boolean proj(IfExp e) {
		return proj(e.ownedCondition) && proj(e.ownedThen) && proj(e.ownedElse)
	}

}
