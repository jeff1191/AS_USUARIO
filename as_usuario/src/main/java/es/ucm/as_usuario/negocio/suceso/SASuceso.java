/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import java.util.List;

public interface SASuceso {
    public List<Evento> consultarEventos();
    public TransferReto verReto();
    public Integer responderReto(Integer respuesta);
    public List<TransferTarea> consultarTareas();
    public void responderTarea(String respuestaTarea);
}