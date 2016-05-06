package es.ucm.as.presentacion.controlador.comandos.imp;

import java.util.List;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 09/03/2016.
 */
public class VerEventosComando implements Command {
    @Override
     public Object ejecutaComando(Object datos) throws commandException {
        SASuceso saSuceso= FactoriaSA.getInstancia().nuevoSASuceso();
        List<TransferEvento> eventosList= saSuceso.consultarEventos();
        return eventosList;
    }
}
