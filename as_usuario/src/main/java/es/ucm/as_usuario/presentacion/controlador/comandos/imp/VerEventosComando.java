package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.TransferEvento;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 09/03/2016.
 */
public class VerEventosComando implements Command {
    @Override
     public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso= FactoriaSA.getInstancia().nuevoSASuceso();
        List<Evento> eventosList= saSuceso.consultarEventos();
        return eventosList;
    }
}
