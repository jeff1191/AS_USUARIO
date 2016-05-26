package es.ucm.as.presentacion.notificaciones;

/**
 * Created by msalitu on 08/04/2016.
 *
 * Esta clase lanza el servicio de notificaciones cuando se enciende el dispositivo
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.Contexto;

public class AutoArranque extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Contexto.getInstancia().setContext(context);

        Controlador.getInstancia().ejecutaComando(ListaComandos.INFO, null);
        /*
        Mensaje info = (Mensaje) intent.getExtras().getSerializable("info");

        Intent service = new Intent(context,  ServicioNotificaciones.class);
        Command c = new InfoComando();
        try {
            Mensaje info = (Mensaje) c.ejecutaComando(null);
            service.putExtra("info", info);
        } catch (commandException e) {
            e.printStackTrace();
        }
        Log.e("servicio", "InfoComando carga la info ");
        context.startService(service);*/
    }
}
