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
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.utils.Frecuencia;

/**
 * Created by Juan Lu on 27/04/2016.
 */
public class BucleNotificaciones extends BroadcastReceiver {

    private DBHelper mDBHelper;

    private DBHelper getHelper(Context context) {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        List<Tarea> tareas = new ArrayList<Tarea>();
        List<Evento> eventos = new ArrayList<Evento>();
        List<TransferTarea> transferTareas = new ArrayList<TransferTarea>();
        Log.e("BucleNotificaciones", "Se van a cambiar la fecha de las notificaciones");
        try {
            // Se obtienen las tareas a recordar ese dia ordenadas por horas de manera ascendente
            QueryBuilder<Tarea, Integer> qb = getHelper(context).getTareaDao().queryBuilder();
            Date actual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(actual);
            Date todayEnd = c.getTime();
            Calendar a = Calendar.getInstance();
            a.setTime(actual);
            a.add(Calendar.HOUR_OF_DAY, -20);
            Date todayBegin = a.getTime();
            Log.e("BucleNotificaciones", "Entre las " + todayBegin.toString() + " y las " + todayEnd.toString());
            qb.where().between("HORA_ALARMA", todayBegin, todayEnd);
            qb.orderBy("HORA_ALARMA", true);
            tareas = qb.query();
            Log.e("BucleNotificaciones", "Tareas: "+ tareas.size() + "");

            QueryBuilder<Evento, Integer> qb2 = getHelper(context).getEventoDao().queryBuilder();
            qb2.where().between("HORA_EVENTO", todayBegin, todayEnd);
            qb2.orderBy("HORA_EVENTO", true);
            eventos = qb2.query();
            Log.e("BucleNotificaciones", "Eventos: " + eventos.size() + "");
            Dao<Evento,Integer> daoE = getHelper(context).getEventoDao();

            for(int i = 0; i < tareas.size(); i++){
                Tarea tarea = tareas.get(i);

                // Se actualizan las proximas horas de alarma y pregunta de esa tarea en base a la frecuencia
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tarea.getHoraAlarma());
                Date newAlarma = proximaNotificacion(calendar, tarea.getFrecuenciaTarea());
                calendar.setTime(tarea.getHoraPregunta());
                Date newPregunta = proximaNotificacion(calendar, tarea.getFrecuenciaTarea());
                tarea.setHoraAlarma(newAlarma);
                tarea.setHoraPregunta(newPregunta);
                Dao<Tarea, Integer> tareaDao = getHelper(context).getTareaDao();
                tareaDao.update(tarea);
            }

            for(int i = 0; i < eventos.size(); i++){
                daoE.deleteById(eventos.get(i).getId());
            }

            lanzarServicio(context);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lanzarServicio(Context context){
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

    private Date proximaNotificacion(Calendar old, Frecuencia frecuencia) {
        switch (frecuencia) {
            case DIARIA:
                old.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case SEMANAL:
                old.add(Calendar.DAY_OF_MONTH, 7);
                break;
            case MENSUAL:
                old.add(Calendar.DAY_OF_MONTH, 30);
                break;
        }
        return old.getTime();
    }
}
