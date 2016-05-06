/**
 * 
 */
package es.ucm.as.negocio.factoria.imp;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.imp.SASucesoImp;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.imp.SAUsuarioImp;


public class FactoriaSAImp extends FactoriaSA {

	public SAUsuario nuevoSAUsuario() {
		return  new SAUsuarioImp();
	}
	
	public SASuceso nuevoSASuceso() {
		return new SASucesoImp();
	}
}