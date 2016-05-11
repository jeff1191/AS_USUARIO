/**
 * 
 */
package es.ucm.as.negocio.suceso;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface SASuceso {
    public List<TransferEvento> consultarEventos();
    public TransferReto consultarReto();
    public Integer responderReto(Integer respuesta);
    public List<TransferTarea> consultarTareas();
    public void cargarTareasBBDD();
    public void cargarRetoBBDD();
   // public void cargarEventosBBDD();
    public String generarPDF() throws IOException, DocumentException;
    public void guardarEventos(List<TransferEvento> eventosRespuesta);
    public void gestionarRetoBBDD(TransferReto r);
    public void eliminarRetoBBDD();
}