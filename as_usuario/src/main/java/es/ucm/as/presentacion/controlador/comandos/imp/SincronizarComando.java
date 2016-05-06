package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.util.ArrayList;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class SincronizarComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {

        Mensaje mensaje = new Mensaje();
        TransferUsuario user = new TransferUsuario();
        user.setNombre("Juanlu");
        user.setCodigoSincronizacion("VIC01");

        TransferTarea tarea = new TransferTarea();
        tarea.setTextoAlarma("Sacar un 10 en el TFG");
        tarea.setTextoPregunta("¿María tiene hambre?");
        ArrayList<TransferTarea> tareas = new ArrayList<>();
        tareas.add(tarea);

        mensaje.setUsuario(user);
        mensaje.setTareas(tareas);

        ConectionManager conectionManager = new ConectionManager(mensaje);
        if(conectionManager.getResponse() != null) {
            String ret = conectionManager.getResponse().getUsuario().getNombre();
            conectionManager.reset();
            Log.e("Sincronizacion", ret);
        }
        return null;
    }
}
