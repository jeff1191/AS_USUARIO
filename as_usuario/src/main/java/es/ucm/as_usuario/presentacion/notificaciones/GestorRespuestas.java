package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class GestorRespuestas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Integer respuesta = intent.getExtras().getInt("respuesta");
        Integer idNot = intent.getExtras().getInt("idNotificacion");
        Integer idTarea = intent.getExtras().getInt("idTarea");

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Log.e("prueba", "Va a cerrar la notifiacion ..." + idNot);

        notificationManager.cancel(idNot);

        ArrayList<Integer> objetoRespuesta = new ArrayList<Integer>();
        objetoRespuesta.add(0, idTarea);
        objetoRespuesta.add(1, respuesta);
        Log.e("prueba", "Responde a la tarea " + idTarea + " con respuesta " + respuesta);
        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, objetoRespuesta);

    }

}
