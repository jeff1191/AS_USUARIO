package es.ucm.as.presentacion.controlador.comandos.imp;

import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 08/03/2016.
 */
public class VerAyudaComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        return datos;
    }
}
