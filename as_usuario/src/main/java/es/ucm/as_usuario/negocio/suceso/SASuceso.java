/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import java.util.List;

public interface SASuceso {
    public List<Evento> consultarEventos();
    public Reto verReto();
    public void mostrarAlarma();
    public void responderPregunta();
    public Integer responderReto(Integer respuesta);
    public void sincronizar();
}