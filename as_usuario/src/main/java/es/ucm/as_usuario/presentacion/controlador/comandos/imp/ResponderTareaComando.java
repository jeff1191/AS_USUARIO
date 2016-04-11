package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import java.util.ArrayList;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 09/04/2016.
 */
public class ResponderTareaComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
        ArrayList<Integer> datosArray = (ArrayList<Integer>) datos;
        Integer idTarea = datosArray.get(0);
        Integer respuesta = datosArray.get(1);
        ss.responderPregunta(idTarea, respuesta);
        return null;
    }
}
