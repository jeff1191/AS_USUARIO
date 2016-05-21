package es.ucm.as.presentacion.controlador.comandos.imp;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class ResponderTareaComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
        ss.responderTarea((TransferTarea) datos);
        return null;
    }
}
