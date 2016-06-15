package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class InfoComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        Log.e("finde", "Se ejecuta info");
        // Se crea el mensaje consultando la info en BBDD
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();

        Mensaje info = new Mensaje();
        info.setUsuario(saUsuario.consultarUsuario());
        info.setReto(saSuceso.consultarReto());
        info.setEventos(saSuceso.consultarEventosNotificaciones());

        if(Calendar.SATURDAY != Calendar.getInstance().get(Calendar.DAY_OF_WEEK) &&
                Calendar.SUNDAY != Calendar.getInstance().get(Calendar.DAY_OF_WEEK) )
            info.setTareas(saSuceso.consultarTareasNotificaciones());
        else {
            Log.e("finde", "Es finde!! No hay tareas!");
            info.setTareas(saSuceso.consultarTareasNotificacionesFinde());

        }
        return info;
    }
}
