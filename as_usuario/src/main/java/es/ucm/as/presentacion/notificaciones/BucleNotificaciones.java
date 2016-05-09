package es.ucm.as.presentacion.notificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Juan Lu on 27/04/2016.
 */
public class BucleNotificaciones extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //context.stopService();
        Log.e("notificaciones", "arranca el servicio desde buclenotificaciones");
        Intent service = new Intent(context, ServicioNotificaciones.class);
        context.startService(service);
    }
}
