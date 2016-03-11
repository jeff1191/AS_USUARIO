package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import es.ucm.as_usuario.presentacion.Contexto;
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

        String respuestaTarea = respuesta + "_" + idTarea;

        Log.e("prueba", "Lo que se le pasa al programa es ..." + respuestaTarea);

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, respuestaTarea);

        //Toast de feedback (Poner esto igual al final de comando??)
        String texto;
        if(respuesta == 1) texto = "Has contestado SI a la tarea";
        else texto = "Has contestado NO a la tarea";

        Toast toastFeedbackRespuesta = Toast.makeText(Contexto.getInstancia().getContext(),texto, Toast.LENGTH_LONG);
        toastFeedbackRespuesta.show();
    }
}
