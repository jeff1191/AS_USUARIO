package es.ucm.as.presentacion.controlador.comandos.exceptions;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class commandException extends Exception {
    public commandException(String msg) {
        super(msg);
    }
    public commandException() {
        super();
    }
}
