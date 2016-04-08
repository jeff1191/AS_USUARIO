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
import android.widget.RemoteViews;

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
        String texto = bundle.getString("texto");

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

        Intent mostrarPregunta = new Intent();
        mostrarPregunta.putExtra("titulo", titulo);
        mostrarPregunta.putExtra("texto", texto);
        mostrarPregunta.putExtra("idNotificacion", notificationId);
        PendingIntent aux = PendingIntent.getActivity(context, notificationId + 4, mostrarPregunta, PendingIntent.FLAG_UPDATE_CURRENT);

        //Encontrar una foto mas pequeña para el logo en las notificaciones
        //O poner otra imagen
        Intent closeButton = new Intent("Download_Cancelled");
        closeButton.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, closeButton, 0);

        RemoteViews notificationView = new RemoteViews( context.getPackageName(),R.layout.notificacion_pregunta);


        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(titulo) //Titulo
                        .setContentText(texto) //Texto
                        .setAutoCancel(true)
                        .setTicker("AS - Nueva Pregunta").setContent(notificationView)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                        .setContentIntent(aux)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000);
       // notificationView.setOnClickPendingIntent(R.id.btn_close, pendingSwitchIntent);


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }


}
