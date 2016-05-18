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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import es.ucm.as.integracion.DBHelper;
import es.ucm.as.integracion.Evento;
import es.ucm.as.integracion.Tarea;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferTarea;

/**
 * Created by Juan Lu on 08/04/2016.
 */
public class CargarNotificaciones extends BroadcastReceiver {

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
        Log.e("CargarNotificaciones", "Se cargan las notificaciones de tareas");
        //Lee los eventos de bbdd
        List<Evento> eventos = new ArrayList<Evento>();
        List<TransferEvento> transferEventos = new ArrayList<TransferEvento>();
        Log.e("CargarNotificaciones", "Se cargan las notificaciones de los eventos");
        try {

            // Se obtienen las tareas a recordar ese dia ordenadas por horas de manera ascendente
            QueryBuilder<Tarea, Integer> qb = getHelper(context).getTareaDao().queryBuilder();
            Date actual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(actual);
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = c.getTime();
            Log.e("CargarNotificaciones", "Entre las "+ actual.toString() + " y las " + tomorrow.toString());
            qb.where().between("HORA_ALARMA", actual, tomorrow);
            qb.orderBy("HORA_ALARMA", true);
            tareas = qb.query();

            String tituloAlarma = "Alarma";
            String tituloPregunta = "Pregunta";
            String tituloEvento = "Evento";

            Log.e("CargarNotificaciones", "Tareas: " + tareas.size() + "");

            for(int i = 0; i < tareas.size(); i++){
                Tarea tarea = tareas.get(i);

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
                Log.e("CargarNotificaciones", "Se guarda la alarma " + tarea.getTextoAlarma() + " a las " + horaAlarmaNotif + ":" + minutosAlarmaNotif);
                lanzarSuceso(context, horaPreguntaNotif, minutosPreguntaNotif, tituloPregunta,
                        tarea.getTextoPregunta(), "pregunta", tarea.getId());
                Log.e("CargarNotificaciones", "Se guarda la pregunta " + tarea.getTextoPregunta() + " a las " + horaPreguntaNotif + ":" + minutosPreguntaNotif);

            }

            lanzarBucle(context);

            Dao<Evento,Integer> daoE = getHelper(context).getEventoDao();
            for(int i = 0; i < daoE.queryForAll().size(); i++)
                Log.e("CargarNotificaciones", daoE.queryForAll().get(i).getHoraAlarma().toString());

            // Se obtienen los eventos a recordar ese dia ordenadas por horas de manera ascendente
            QueryBuilder<Evento, Integer> qbEvento = getHelper(context).getEventoDao().queryBuilder();
            qbEvento.where().between("HORA_ALARMA",actual, tomorrow);
            qbEvento.orderBy("HORA_ALARMA", true);
            eventos = qbEvento.query();

            Log.e("CargarNotificaciones", "Eventos: "+eventos.size()+"");

            for(int i = 0; i < eventos.size(); i++) {
                Evento evento = eventos.get(i);
                if(evento.getAsistencia().equals("SI")) {
                    //Esto es para dividir el date en horas y minutos
                    SimpleDateFormat horasMinutosE = new SimpleDateFormat("HH:mm");
                    StringTokenizer tokensAlarmaE = new StringTokenizer(horasMinutosE.format
                            (evento.getHoraAlarma()), ":");
                    StringTokenizer tokensHoraE = new StringTokenizer(horasMinutosE.format
                            (evento.getHoraEvento()), ":");

                    Integer horaAlarmaNotifE = Integer.parseInt(tokensAlarmaE.nextToken());
                    Integer minutosAlarmaNotifE = Integer.parseInt(tokensAlarmaE.nextToken());
                    Integer horaE = Integer.parseInt(tokensHoraE.nextToken());
                    Integer minutosE = Integer.parseInt(tokensHoraE.nextToken());

                    String mensajeEvento = evento.getNombre() + " a las " + horaE + ":" + minutosE;

                    lanzarSuceso(context, horaAlarmaNotifE, minutosAlarmaNotifE, tituloEvento,
                            mensajeEvento, "evento", 0);
                    Log.e("CargarEventos", "Se guarda el evento " + mensajeEvento +
                            " a las " + horaAlarmaNotifE + ":" + minutosAlarmaNotifE);
                }
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
        else if (tipo.equals("alarma"))
            i = new Intent(context, NotificacionAlarma.class);
        else // evento
            i = new Intent(context, NotificacionEvento.class);

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

    public void lanzarBucle(Context context){
        Log.e("lanzarBucle", "Se mete para guardar la ultima not");

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, BucleNotificaciones.class);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(context, pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, 23);
        horaNotificacionCal.set(Calendar.MINUTE, 47);
        horaNotificacionCal.set(Calendar.SECOND, 00);
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

}