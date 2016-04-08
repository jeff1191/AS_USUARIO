package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

        String respuestaTarea = respuesta + "___idTarea:" + idTarea;

        Log.e("prueba", "Lo que se le pasa al programa es ..." + respuestaTarea);

        //Ponerle la funcionalidad
        //Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, respuestaTarea);

    }

}
