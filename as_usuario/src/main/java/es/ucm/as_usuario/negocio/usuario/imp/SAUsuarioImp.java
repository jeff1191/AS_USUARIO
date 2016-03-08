/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario.imp;

import android.util.Log;

import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;


public class SAUsuarioImp implements SAUsuario {


	public void editarUsuario() {

	}

	/*public String ayudaUsuario() {
		// esto hay que pensar bien como se mete el pdf de ayuda por primera vez
		Usuario.getInstancia().setAyuda("/sdcard/Download/AS/ayuda.pdf");
		return Usuario.getInstancia().getAyuda();
	}*/

	public String consultarInforme() {
		return Usuario.getInstancia().getInforme();
	}

	public void sincronizar() {

	}
}