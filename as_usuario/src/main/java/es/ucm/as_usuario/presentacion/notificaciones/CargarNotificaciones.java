package es.ucm.as_usuario.presentacion.notificaciones;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;

/**
 * Created by Juan Lu on 08/04/2016.
 */
public class CargarNotificaciones extends BroadcastReceiver {

    private static final int MS_DIA = 86400000;
    private static final int MS_SEMANA = 604800000;
    private static final long MS_MES = 2592000000L;

    private DBHelper mDBHelper;

        private DBHelper getHelper(Context context) {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Lee las tareas de bbdd
        List<Tarea> tareas = new ArrayList<Tarea>();
        List<TransferTarea> transferTareas = new ArrayList<TransferTarea>();
        try {
            // Se obtienen las tareas a recordar ese dia ordenadas por horas de manera ascendente
            QueryBuilder<Tarea, Integer> qb = getHelper(context).getTareaDao().queryBuilder();
            Date actual = new Date();
            Date tomorrow = new Date(actual.getTime() + MS_DIA);
            qb.where().between("HORA_ALARMA",actual, tomorrow );
            qb.orderBy("HORA_ALARMA", true);
            tareas = qb.query();

            String tituloAlarma = "Alarma";
            String tituloPregunta = "Pregunta";
            for(int i = 0; i < tareas.size(); i++){
                Tarea tarea = tareas.get(i);
                Log.e("notificaciones", tarea.getTextoAlarma());
                Log.e("notificaciones", tarea.getHoraAlarma().toString());
                //Esto es para dividir el date en horas y minutos
                SimpleDateFormat horasMinutos = new SimpleDateFormat("HH:mm");
                StringTokenizer tokensAlarma = new StringTokenizer(horasMinutos.format
                        (tarea.getHoraAlarma()),":");
                StringTokenizer tokensPregunta = new StringTokenizer(horasMinutos.format
                        (tarea.getHoraPregunta()),":");

                Integer horaAlarmaNotif = Integer.parseInt(tokensAlarma.nextToken());
                Integer minutosAlarmaNotif =  Integer.parseInt(tokensAlarma.nextToken());
                Integer horaPreguntaNotif = Integer.parseInt(tokensPregunta.nextToken());
                Integer minutosPreguntaNotif =  Integer.parseInt(tokensPregunta.nextToken());

                lanzarSuceso(context, horaAlarmaNotif, minutosAlarmaNotif, tituloAlarma,
                        tarea.getTextoAlarma(), "alarma", tarea.getId());
                lanzarSuceso(context, horaPreguntaNotif, minutosPreguntaNotif, tituloPregunta,
                        tarea.getTextoPregunta(), "pregunta",tarea.getId());

                if(i == tareas.size() - 1){//Guarda una alarma para el siguiente dia que vuelva a arrancar el servicio
                    lanzarServicio(context, horaPreguntaNotif, minutosPreguntaNotif);
                }

                // Se actualizan las proximas horas de alarma y pregunta de esa tarea en base a la frecuencia
                Date newAlarma = new Date(proximaNotificacion(tarea.getHoraAlarma(), tarea.getFrecuenciaTarea()));
                Date newPregunta = new Date(proximaNotificacion(tarea.getHoraPregunta(), tarea.getFrecuenciaTarea()));
                tarea.setHoraAlarma(newAlarma);
                tarea.setHoraPregunta(newPregunta);
                Dao<Tarea, Integer> tareaDao = getHelper(context).getTareaDao();
                tareaDao.update(tarea);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void lanzarSuceso (Context context, Integer hora, Integer minutos, String titulo,
                              String texto, String tipo, Integer idTarea) {

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i;
        if(tipo.equals("pregunta"))
            i = new Intent(context, NotificacionPregunta.class);
        else // alarma
            i = new Intent(context, NotificacionAlarma.class);

        i.putExtra("titulo", titulo);
        i.putExtra("texto", texto);
        i.putExtra("idTarea", idTarea);

         /*
        It gets current system time. Then I'm reading only last 4 digits from it.
         I'm using it to create unique id every time notification is displayed.
         So the probability of getting same or reset of notification id will be avoided.
         */
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);

        PendingIntent pi = PendingIntent.getBroadcast(context, pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        Calendar horaActualCal = Calendar.getInstance();
        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, hora);
        horaNotificacionCal.set(Calendar.MINUTE, minutos);
        horaNotificacionCal.set(Calendar.SECOND, 0);
        horaNotificacionCal.set(Calendar.MILLISECOND, 0);

        long horaActual = horaActualCal.getTimeInMillis();
        long horaNotificacion = horaNotificacionCal.getTimeInMillis();

        //Si ya se ha pasado de la hora actual, para que no se lance inmeditamente
        //se pone que empieze a partir de mañana
        if(horaNotificacion < horaActual){
            horaNotificacionCal.add(Calendar.DAY_OF_MONTH, 1);
            horaNotificacion = horaNotificacionCal.getTimeInMillis();
        }

        setAlarm(am,horaNotificacion,pi);

    }

    public void lanzarServicio(Context context, Integer hora, Integer minutos){
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AutoArranque.class);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(context, pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, hora);
        horaNotificacionCal.set(Calendar.MINUTE, minutos+15);
        horaNotificacionCal.set(Calendar.SECOND, 00);
        horaNotificacionCal.add(Calendar.DAY_OF_MONTH, 1); //Esto hace que se haga la dia siguiente
        long horaNotificacion = horaNotificacionCal.getTimeInMillis();

        Log.e("notificaciones", "Activara el auto arranque " + horaNotificacionCal.getTime().toString());

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


    private long proximaNotificacion(Date old, Frecuencia frecuencia) {
        long nuevo = old.getTime();
        switch (frecuencia) {
            case DIARIA:
                nuevo += MS_DIA;
            case SEMANAL:
                nuevo += MS_SEMANA;
            case MENSUAL:
                nuevo += MS_MES;
        }
        return nuevo;
    }

}