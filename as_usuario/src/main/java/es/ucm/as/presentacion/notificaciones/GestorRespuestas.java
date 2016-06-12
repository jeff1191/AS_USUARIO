package es.ucm.as.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.Contexto;

public class GestorRespuestas extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Contexto.getInstancia().setContext(context);

        Integer respuesta = intent.getExtras().getInt("respuesta");
        Integer idNot = intent.getExtras().getInt("idNotificacion");
        Integer idTarea = intent.getExtras().getInt("idTarea");

        TransferTarea transferTarea = new TransferTarea();
        transferTarea.setId(idTarea);
        //Si responde que si
        if(respuesta == 1) {
            transferTarea.setNumSi(1);
            transferTarea.setNumNo(0);
        }
        //Si responde que no
        else {
            transferTarea.setNumNo(1);
            transferTarea.setNumSi(0);
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(idNot);

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, transferTarea);
    }
}
