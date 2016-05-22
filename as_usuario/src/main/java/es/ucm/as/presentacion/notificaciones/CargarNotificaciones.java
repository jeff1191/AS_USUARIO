package es.ucm.as.presentacion.notificaciones;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import es.ucm.as.R;

import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.presentacion.vista.Contexto;

/**
 * Created by Juan Lu on 08/04/2016.
 */
public class CargarNotificaciones extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // Se modifica el contexto para poder usar el singleton para responder desde una notificacion
        // con un comando
        Contexto.getInstancia().setContext(context);
        Mensaje info = (Mensaje) intent.getExtras().getSerializable("info");
        if (info == null)
            Log.e("CargarNotificaciones", "Info nula");
        else{
            Log.e("CargarNotificaciones", "Info NO nula");

        Integer tono = info.getUsuario().getTono();
        //Coge las tareas de bbdd
        List<TransferTarea> tareas = info.getTareas();
        //Coge los eventos de bbdd
        List<TransferEvento> eventos = info.getEventos();

        String tituloAlarma = "Alarma";
        String tituloPregunta = "Pregunta";
        String tituloEvento = "Evento";

        Log.e("CargarNotificaciones", "Tareas: " + tareas.size() + "");

        for (int i = 0; i < tareas.size(); i++) {
            TransferTarea tarea = tareas.get(i);

            //Esto es para dividir el date en horas y minutos
            SimpleDateFormat horasMinutos = new SimpleDateFormat("HH:mm");
            StringTokenizer tokensAlarma = new StringTokenizer(horasMinutos.format
                    (tarea.getHoraAlarma()), ":");
            StringTokenizer tokensPregunta = new StringTokenizer(horasMinutos.format
                    (tarea.getHoraPregunta()), ":");

            Integer horaAlarmaNotif = Integer.parseInt(tokensAlarma.nextToken());
            Integer minutosAlarmaNotif = Integer.parseInt(tokensAlarma.nextToken());
            Integer horaPreguntaNotif = Integer.parseInt(tokensPregunta.nextToken());
            Integer minutosPreguntaNotif = Integer.parseInt(tokensPregunta.nextToken());

            lanzarSuceso(context, horaAlarmaNotif, minutosAlarmaNotif, tituloAlarma,
                    tarea.getTextoAlarma(), "alarma", tarea.getId(), tono);
            Log.e("CargarNotificaciones", "Se guarda la alarma " + tarea.getTextoAlarma() + " a las " + horaAlarmaNotif + ":" + minutosAlarmaNotif);
            lanzarSuceso(context, horaPreguntaNotif, minutosPreguntaNotif, tituloPregunta,
                    tarea.getTextoPregunta(), "pregunta", tarea.getId(), tono);
            Log.e("CargarNotificaciones", "Se guarda la pregunta " + tarea.getTextoPregunta() + " a las " + horaPreguntaNotif + ":" + minutosPreguntaNotif);

        }

        lanzarBucle(context);

        Log.e("CargarNotificaciones", "Eventos: " + tareas.size() + "");

        for (int i = 0; i < eventos.size(); i++) {
            TransferEvento evento = eventos.get(i);
            if (evento.getAsistencia().equals("SI")) {
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
                        mensajeEvento, "evento", 0, tono);
                Log.e("CargarEventos", "Se guarda el evento " + mensajeEvento +
                        " a las " + horaAlarmaNotifE + ":" + minutosAlarmaNotifE);
            }
        }
    }
    }


    public void lanzarSuceso (Context context, Integer hora, Integer minutos, String titulo,
                              String texto, String tipo, Integer idTarea, Integer tono) {

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i;

        if(tipo.equals("pregunta"))
            i = new Intent(context, NotificacionPregunta.class);
        else if (tipo.equals("alarma"))
            i = new Intent(context, NotificacionAlarma.class);
        else // evento
            i = new Intent(context, NotificacionEvento.class);

        i.putExtra("tono", tono );
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