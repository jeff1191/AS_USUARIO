package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import es.ucm.as_usuario.R;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class NotificacionPregunta extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        String titulo = bundle.getString("titulo");
        String texto = "Has hecho...?";

        //Hay que mirar lo del SONIDO y la VIBRACION
        //Encontrar una foto mas pequeña para el logo en las notificaciones o poner otra imagen
        //Si los ids dan problemas, generarlos de otra manera y pasarselos aqui
        //Tambien generar los ids de los pending intents??¿¿

        Log.e("prueba", "Empieza a crear la notificacion pregunta...");
        /*
        It gets current system time. Then I'm reading only last 4 digits from it.
         I'm using it to create unique id every time notification is displayed.
         So the probability of getting same or reset of notification id will be avoided.
         */
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        Log.e("prueba", "Notificacion con el ID..." + notificationId);

        Intent mostrarPregunta = new Intent(context, GestorRespuestas.class);
        mostrarPregunta.putExtra("titulo", intent.getExtras().getString("titulo"));
        mostrarPregunta.putExtra("texto", intent.getExtras().getString("texto"));
        mostrarPregunta.putExtra("idNotificacion", notificationId);
        PendingIntent aux = PendingIntent.getActivity(context, notificationId + 4, mostrarPregunta, PendingIntent.FLAG_UPDATE_CURRENT);

        //Encontrar una foto mas pequeña para el logo en las notificaciones
        //O poner otra imagen
        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Recordatorio para...")
                        .setContentText(intent.getExtras().getString("titulo"))
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                        .setContentIntent(aux)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }

    public void lanzarPregunta(Context context, Integer hora, Integer minutos, String titulo, String texto)
    {
        Log.e("prueba", "Guarda la notificacion pregunta...");

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificacionPregunta.class);
        i.putExtra("titulo", titulo);
        i.putExtra("texto", texto);
         /*
        It gets current system time. Then I'm reading only last 4 digits from it.
         I'm using it to create unique id every time notification is displayed.
         So the probability of getting same or reset of notification id will be avoided.
         */
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

        //Si ya se ha pasado de la hora actual, para que no pete
        //se pone que empieze a partir de mañana
        if(horaNotificacion < horaActual){
            horaNotificacionCal.add(Calendar.DAY_OF_MONTH, 1);
            Log.e("prueba", "La hora de la pregunta se pasa al dia ..." + horaActualCal.getTime().toString());
            horaNotificacion = horaNotificacionCal.getTimeInMillis();
        }

        am.set(AlarmManager.RTC_WAKEUP, horaNotificacion, pi);
        // am.setRepeating(AlarmManager.RTC_WAKEUP, horaNotificacion, AlarmManager.INTERVAL_DAY, pi);
    }
}
