package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import java.util.List;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.TransferEvento;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 14/04/2016.
 */
public class GuardarEventosComando implements Command {

    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        List<TransferEvento> eventosRespuesta= (List<TransferEvento>) datos;
        saSuceso.guardarEventos(eventosRespuesta);
        return null;
    }
}