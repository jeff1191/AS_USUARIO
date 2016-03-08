/**
 * 
 */
package es.ucm.as_usuario.negocio.factoria.imp;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.imp.SASucesoImp;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.imp.SAUsuarioImp;


public class FactoriaSAImp extends FactoriaSA {

	public SAUsuario nuevoSAUsuario() {
		return  new SAUsuarioImp();
	}


	public SASuceso nuevoSASuceso() {
		return new SASucesoImp();
	}
}