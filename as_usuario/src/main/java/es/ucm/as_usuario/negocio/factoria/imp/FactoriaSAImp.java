/**
 * 
 */
package es.ucm.as_usuario.negocio.factoria.imp;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.tarea.SASuceso;
import es.ucm.as_usuario.negocio.tarea.imp.SATareaImp;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Jeffer
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FactoriaSAImp extends FactoriaSA {
	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SAUsuario nuevoSAUsuario() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @see FactoriaSA#nuevoSATarea()
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public SASuceso nuevoSATarea() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return new SATareaImp();
		// end-user-code
	}
}