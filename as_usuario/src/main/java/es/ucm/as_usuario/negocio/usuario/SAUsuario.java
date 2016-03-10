/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario;

public interface SAUsuario {

	public void editarUsuario();

	public void sincronizar();

	Integer calcularPuntuacion();

	void crearUsuario(TransferUsuario transferUsuario);
}