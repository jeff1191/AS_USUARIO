package es.ucm.as_usuario.presentacion.controlador.comandos;

import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 02/03/2016.
 */
public interface Command {
    //devuelve lo que tenga que poner en los toast o lo que sea, si hay error se manda por excepcion
    //Como seria mejor?¿¿???¿¿¿???
    Object ejecutaComando(Object datos) throws commandException;
}
