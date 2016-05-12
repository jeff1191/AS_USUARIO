/**
 * 
 */
package es.ucm.as.negocio.usuario;

public interface SAUsuario {
	TransferUsuario editarUsuario(TransferUsuario datos);

	Integer calcularPuntuacion();

	void crearUsuario(TransferUsuario transferUsuario);

	TransferUsuario consultarUsuario();

	void enviarCorreo();
}