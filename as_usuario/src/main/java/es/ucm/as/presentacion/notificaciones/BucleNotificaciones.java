package es.ucm.as.presentacion.notificaciones;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.ucm.as.integracion.DBHelper;
import es.ucm.as.integracion.Evento;
import es.ucm.as.integracion.Tarea;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.utils.Frecuencia;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 27/04/2016.
 */
public class BucleNotificaciones extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Controlador.getInstancia().ejecutaComando(ListaComandos.ACTUALIZAR_NOTIFICACIONES, null);
        Log.e("bucleNotifi", "onReceive del bucle");
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AutoArranque.class);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(context, pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, 1);
        horaNotificacionCal.set(Calendar.MINUTE, 11);
        horaNotificacionCal.set(Calendar.SECOND, 00);
        horaNotificacionCal.add(Calendar.DAY_OF_MONTH, 1); //Esto hace que se haga la dia siguiente
        long horaNotificacion = horaNotificacionCal.getTimeInMillis();
        Log.e("bucleNotifi", "autoarranque a las " + horaNotificacionCal.getTime().toString());
        setAlarm(am, horaNotificacion, pi);
    }


    private void setAlarm(AlarmManager am,long ms, PendingIntent pendingIntent){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            am.set(AlarmManager.RTC, ms, pendingIntent);
        } else {
            setAlarmFromKitkat(am, ms, pendingIntent);
        }
    }

    @TargetApi(19)
    private void setAlarmFromKitkat(AlarmManager am, long ms, PendingIntent pi){
        am.setExact(AlarmManager.RTC, ms, pi);
    }

}
