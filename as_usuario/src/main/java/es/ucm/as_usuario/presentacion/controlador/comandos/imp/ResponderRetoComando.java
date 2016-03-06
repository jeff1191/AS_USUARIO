package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class ResponderRetoComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
        Integer nuevoContador = ss.responderReto((Integer) datos);
        return nuevoContador;
    }
}
