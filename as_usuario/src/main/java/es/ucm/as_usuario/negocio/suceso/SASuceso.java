/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import java.util.List;

public interface SASuceso {
    void editarUsuario();
    void ayudaUsuario();
    void consultarInforme();
    List<Evento> consultarEventos();
    void consultarReto();
    void mostrarAlarma();
    void responderPregunta();
    void responderReto(Integer respuesta);
    void sincronizar();
}