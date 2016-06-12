package es.ucm.as.presentacion.notificaciones;

/**
 *
 * Esta clase lanza el servicio de notificaciones cuando se enciende el dispositivo
 *
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

    }
}
