package es.ucm.as.presentacion.controlador.comandos.imp;

import java.util.ArrayList;
import java.util.List;

import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

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
