package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
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
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "preguntaNotificacion");
        wl.acquire();
/*
        Bundle bundle = intent.getExtras();

        String titulo = bundle.getString("titulo");
        String texto = bundle.getString("texto");
*/
        //Hay que mirar lo del sonido
        //Sustituir el titulo y el texto por las variables

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

        Log.e("prueba", "Con el ID..." + notificationId);

        Intent resSi = new Intent(context, GestorRespuestas.class);
        resSi.putExtra("Respuesta", 1);
        resSi.putExtra("IDNotificacion", notificationId);
        PendingIntent contestaSi = PendingIntent.getBroadcast(context, 1, resSi, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent resNo = new Intent(context, GestorRespuestas.class);
        resNo.putExtra("Respuesta", -1);
        resNo.putExtra("IDNotificacion", notificationId);
        PendingIntent contestaNo = PendingIntent.getBroadcast(context, 2, resNo, PendingIntent.FLAG_UPDATE_CURRENT);

        //Encontrar una foto mas pequeña para el logo en las notificaciones
        //O poner otra imagen
        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("holaa")
                        .setContentText("Esto es una pruebaaa con botones")
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                        .addAction(R.drawable.ic_done_white, "Si", contestaSi)
                        .addAction(R.drawable.ic_clear_white, "No", contestaNo)
                        .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());



        wl.release();
    }

    public void lanzarPregunta(Context context, Integer hora, Integer minutos)
    {
        Log.e("prueba", "Guarda la notificacion pregunta...");

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificacionPregunta.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.e("prueba", "La hora es..." + hora + ":" + minutos);

        Calendar horaNotificacion = Calendar.getInstance();
        horaNotificacion.set(Calendar.HOUR_OF_DAY, hora);
        horaNotificacion.set(Calendar.MINUTE, minutos);
        horaNotificacion.set(Calendar.SECOND, 00);

        am.setRepeating(AlarmManager.RTC_WAKEUP, horaNotificacion.getTimeInMillis(), 24 * 60 * 60 * 1000, pi);
    }
}
