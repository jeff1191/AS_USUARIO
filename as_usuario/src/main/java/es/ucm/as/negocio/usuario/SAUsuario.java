/**
 * 
 */
package es.ucm.as.negocio.usuario;

public interface SAUsuario {
	TransferUsuario editarUsuario(TransferUsuario datos);

	public void sincronizar();

	Integer calcularPuntuacion();

	void crearUsuario(TransferUsuario transferUsuario);

	TransferUsuario consultarUsuario();

	void enviarCorreo();
}