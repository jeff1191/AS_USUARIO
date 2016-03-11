package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class GestorRespuestas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Integer respuesta = intent.getExtras().getInt("Respuesta");
        Integer id = intent.getExtras().getInt("IDNotificacion");

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Log.e("prueba", "Va a cerrar la notifiacion ..." + id);

        notificationManager.cancel(id);

        Log.e("prueba", "Va a sumar ..." + respuesta);

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, respuesta);
    }
}
