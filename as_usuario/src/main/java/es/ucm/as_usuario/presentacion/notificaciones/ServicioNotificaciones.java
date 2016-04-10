package es.ucm.as_usuario.presentacion.notificaciones;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class ServicioNotificaciones extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        Log.d(this.getClass().getSimpleName(),"onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Tendriamos que pensar en tener to*do en una cola que la tendria que haber cogido de base de datos - esto es IMPORTANTE
        Log.d(this.getClass().getSimpleName(),"onStartCommand() SERVICIO");

        AlarmManager am =( AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), CargarNotificaciones.class);
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        // Establecer la hora a la que cargara de BBDD las notificaciones
        Integer h = 17;
        Integer m = 50;
        Log.e("prueba", "Va a activar CARGARNOTIFICACIONES A LAS " + h +":" + m);

        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, h);
        horaNotificacionCal.set(Calendar.MINUTE, m);
        horaNotificacionCal.set(Calendar.SECOND, 00);
        long horaNotificacion = horaNotificacionCal.getTimeInMillis();

        setAlarm(am, horaNotificacion, pi);

        /////////////////////////////////////////////////
/*
        String tituloAlarmaNotificacion = "Alarma";
        String tituloPreguntaNotificacion = "Pregunta";
        //obtener la siguiente tarea
        Tarea a = new Tarea();
        a.setTextoAlarma("Esto es la alarma 0");
        a.setTextoPregunta("Esto es la pregunta 0");
        Tarea a1 = new Tarea();
        a1.setTextoAlarma("Esto es la alarma 1");
        a1.setTextoPregunta("Esto es la pregunta 1");
        Tarea a2 = new Tarea();
        a2.setTextoAlarma("Esto es la alarma 2");
        a2.setTextoPregunta("Esto es la pregunta 2");
        Tarea a3 = new Tarea();
        a3.setTextoAlarma("Esto es la alarma 3");
        a3.setTextoPregunta("Esto es la pregunta 3");

        lanzarSuceso(getApplicationContext(), 19, 21, tituloAlarmaNotificacion, a.getTextoAlarma(), "alarma", 1);
        lanzarSuceso(getApplicationContext(), 19, 21, tituloPreguntaNotificacion, a.getTextoPregunta(), "pregunta", 1);
        //lanzarSuceso(getApplicationContext(), 19, 18, tituloAlarmaNotificacion, a1.getTextoAlarma(), "alarma");
        //lanzarSuceso(getApplicationContext(), 19, 18, tituloPreguntaNotificacion, a1.getTextoPregunta(), "pregunta");
*/
        return START_NOT_STICKY;
    }
/*

    public long lanzarSuceso(Context context, Integer hora, Integer minutos, String titulo, String texto, String tipo, Integer idTarea)
    {
        Log.e("prueba", "Guarda la notificacion pregunta...");
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent i;
        if(tipo.equals("pregunta"))
            i = new Intent(context, NotificacionPregunta.class);
        else // alarma
            i = new Intent(context, NotificacionAlarma.class);

        i.putExtra("titulo", titulo);
        i.putExtra("texto", texto);
        i.putExtra("idTarea", idTarea);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);

        Log.e("prueba", "Pending con el ID..." + pendingId);

        PendingIntent pi = PendingIntent.getBroadcast(context, pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        Log.e("prueba", "La hora es..." + hora + ":" + minutos);

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
            Log.e("prueba", "La hora de la pregunta se pasa al dia ..." + horaActualCal.getTime().toString());
            horaNotificacion = horaNotificacionCal.getTimeInMillis();
        }

        setAlarm(am,horaNotificacion,pi);

        return horaNotificacion;
    }
*/
    @TargetApi(19)
    private void setAlarmFromKitkat(AlarmManager am, long ms, PendingIntent pi){
        am.setExact(AlarmManager.RTC, ms, pi);
    }

    private void setAlarm(AlarmManager am,long ms, PendingIntent pendingIntent){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            am.set(AlarmManager.RTC, ms, pendingIntent);
        } else {
            setAlarmFromKitkat(am, ms, pendingIntent);
        }
    }

}
