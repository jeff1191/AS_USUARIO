package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.util.List;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class ConsultarTareasHoyComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso su = FactoriaSA.getInstancia().nuevoSASuceso();
        List<TransferTarea> tareas = su.consultarTareasHoy((TransferUsuario)datos);
        return tareas;
    }
}
