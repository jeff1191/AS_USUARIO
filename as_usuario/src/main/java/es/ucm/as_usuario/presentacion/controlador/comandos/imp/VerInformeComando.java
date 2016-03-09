package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 05/03/2016.
 */
public class VerInformeComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss= FactoriaSA.getInstancia().nuevoSASuceso();
        List<TransferTarea> tareas= ss.consultarTareas();
        return tareas;
    }
}
