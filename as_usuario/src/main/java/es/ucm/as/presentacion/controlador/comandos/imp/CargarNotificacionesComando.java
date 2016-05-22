package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.util.List;

import es.ucm.as.negocio.conexion.msg.Mensaje;
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
public class CargarNotificacionesComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
        SAUsuario su = FactoriaSA.getInstancia().nuevoSAUsuario();
        List<TransferTarea> tareas = ss.consultarTareasHoy((TransferUsuario)datos);
        TransferUsuario usuario = su.consultarUsuario();

        Mensaje msg = new Mensaje();
        msg.setTareas(tareas);
        msg.setUsuario(usuario);
        return msg;
    }
}
