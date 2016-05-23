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
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
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

        // Se accede a la informacion de BBDD a traves de un bundle
        Mensaje info = (Mensaje) intent.getExtras().getSerializable("info");
        if (info != null){
            Integer tono = info.getUsuario().getTono();
            List<TransferTarea> tareas = info.getTareas();
            List<TransferEvento> eventos = info.getEventos();

            String tituloAlarma = "Alarma";
            String tituloPregunta = "Pregunta";
            String tituloEvento = "Evento";

            Log.e("CargarNotificaciones", "Tareas: " + tareas.size() + "");

            for (int i = 0; i < tareas.size(); i++) {
                TransferTarea tarea = tareas.get(i);
                Log.e("CargarNot", "id tarea " + tarea.getId().toString());
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
                Log.e("CargarNotificaciones", "Se guarda la alarma " + tarea.getTextoAlarma() +
                        " a las " + horaAlarmaNotif + ":" + minutosAlarmaNotif);
                lanzarSuceso(context, horaPreguntaNotif, minutosPreguntaNotif, tituloPregunta,
                        tarea.getTextoPregunta(), "pregunta", tarea.getId(), tono);
                Log.e("CargarNotificaciones", "Se guarda la pregunta " + tarea.getTextoPregunta() +
                        " a las " + horaPreguntaNotif + ":" + minutosPreguntaNotif);

            }

            // Esto carga la clase BucleNotificaciones, importante para que la fecha de las tareas
            // se actualice a ultima hora
            lanzarBucle(context, info);

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

        /* Coge los 4 ultimos digitos de la fecha actual del sistema para formar el pending id
        con ellos, de esta manera se asegura que son unicos */
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);

        // Se ejecuta este comando para que se guarde en BBDD el pendingIntent ID
        TransferTarea transferTarea = new TransferTarea();
        transferTarea.setId(idTarea);
        if(tipo.equals("alarma"))
            transferTarea.setNotificacionAlarma(pendingId);
        else if(tipo.equals("pregunta"))
            transferTarea.setNotificacionPregunta(pendingId);
        Controlador.getInstancia().ejecutaComando(ListaComandos.ACTUALIZAR_NOTIFICACION_TAREA, transferTarea);

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

    public void lanzarBucle(Context context, Mensaje info){
        Log.e("lanzarBucle", "Se mete para guardar la ultima not");

        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
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
        Log.e("cargarNot", "A las 23:47 se actualizaran las horas de notificacion");


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