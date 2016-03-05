/**
 * 
 */
package es.ucm.as_usuario.negocio.tarea;

import java.util.List;

public interface SASuceso {
    public void editarUsuario();
    public void ayudaUsuario();
    public void consultarInforme();
    public List<Evento> consultarEventos();
    public void consultarReto();
    public void mostrarAlarma();
    public void responderPregunta();
    public void responderReto(Integer respuesta);
    public void sincronizar();
}