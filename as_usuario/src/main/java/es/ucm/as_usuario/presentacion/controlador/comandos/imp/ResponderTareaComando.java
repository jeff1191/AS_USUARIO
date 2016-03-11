package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Juan Lu on 11/03/2016.
 */
public class ResponderTareaComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
        ss.responderTarea((Integer) datos);
        return datos;
    }
}
