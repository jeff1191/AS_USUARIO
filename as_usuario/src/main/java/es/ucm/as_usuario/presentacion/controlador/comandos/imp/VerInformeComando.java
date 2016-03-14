package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
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
        List<Object> ret = new ArrayList<Object>();
        SAUsuario su = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario tu = su.consultarUsuario();
        List<TransferTarea> tareas = ss.consultarTareas();
        ret.add(0, tu);
        for (int i = 1 ; i <= tareas.size(); i++)
            ret.add(i, tareas.get(i-1));
        return ret;
    }
}
