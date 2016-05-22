package es.ucm.as.presentacion.notificaciones;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
        Integer tono = bundle.getInt("tono");

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
                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + tono))
                        .addAction(R.drawable.ic_done_white, "Si", contestaSi)
                        .addAction(R.drawable.ic_clear_white, "No", contestaNo)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(texto)
                                .setBigContentTitle(titulo));

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }


}
