package es.ucm.as_usuario.presentacion.notificaciones;

/**
 * Created by msalitu on 08/04/2016.
 *
 * Esta clase lanza el servicio de notificaciones cuando se enciende el dispositivo
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoArranque extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("prueba", "Autoarranque recibido");
        Intent service = new Intent(context,  ServicioNotificaciones.class);
        context.startService(service);
    }
}
