/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface SASuceso {
    public List<TransferEvento> consultarEventos();
    public TransferReto verReto();
    public Integer responderReto(Integer respuesta);
    public List<TransferTarea> consultarTareas();
    public void cargarTareasBBDD();
    public void cargarRetoBBDD();
    public void cargarEventosBBDD();
    public String generarPDF() throws IOException, DocumentException;
}