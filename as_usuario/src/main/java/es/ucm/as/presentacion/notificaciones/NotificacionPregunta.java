package es.ucm.as.presentacion.notificaciones;

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

import java.util.Date;

import es.ucm.as.R;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class NotificacionPregunta extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        String titulo = bundle.getString("titulo");
        String texto = bundle.getString("texto");
        Integer idTarea = bundle.getInt("idTarea");

        //Hay que mirar lo del SONIDO y la VIBRACION
        //Encontrar una foto mas pequeña para el logo en las notificaciones o poner otra imagen
        //Si los ids dan problemas, generarlos de otra manera y pasarselos aqui
        //Tambien generar los ids de los pending intents??¿¿

        /*
        It gets current system time. Then I'm reading only last 4 digits from it.
         I'm using it to create unique id every time notification is displayed.
         So the probability of getting same or reset of notification id will be avoided.
         */
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);


        Intent resSi = new Intent(context, GestorRespuestas.class);
        resSi.putExtra("respuesta", 1);
        resSi.putExtra("idNotificacion", notificationId);
        resSi.putExtra("idTarea", idTarea);
        PendingIntent contestaSi = PendingIntent.getBroadcast(context,  notificationId+3, resSi, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent resNo = new Intent(context, GestorRespuestas.class);
        resNo.putExtra("respuesta", -1);
        resNo.putExtra("idNotificacion", notificationId);
        resNo.putExtra("idTarea", idTarea);
        PendingIntent contestaNo = PendingIntent.getBroadcast(context,  notificationId-2, resNo, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_notificacion)
                        .setContentTitle(titulo)    //Titulo
                        .setContentText("Revisa las notificaciones anteriores")
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_notificacion))
                        .addAction(R.drawable.ic_done_white, "Si", contestaSi)
                        .addAction(R.drawable.ic_clear_white, "No", contestaNo)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(texto)
                                .setBigContentTitle(titulo));
        //.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.silbido))

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }


}