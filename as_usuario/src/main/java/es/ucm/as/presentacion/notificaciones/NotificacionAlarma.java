package es.ucm.as.presentacion.notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

import es.ucm.as.R;

/**
 *
 * Lo guay de esto es que las notificaciones normalmente se lanzan con un notification manager
 * Como lo que queremos es que se muestren a una hora X se deben lanzan con un alarm manager  y un servicio
 * En el servicio se llama con un intent al onReceive de la clase q extiende el BroadcastReciever
 *
 * El servicio hay que arrancarlo de alguna manera.... ver mas info acerca de eso, lo mejor seria cuando se inicia la app
 *
 * Created by Juan Lu on 09/03/2016.
 */
public class NotificacionAlarma extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        PendingIntent aux = PendingIntent.getBroadcast(context, notificationId+4, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_notificacion)
                        .setContentTitle(intent.getExtras().getString("titulo"))
                        .setContentText("Revisa las notificaciones anteriores")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_notificacion))
                        .setContentIntent(aux)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                  .bigText(intent.getExtras().getString("texto"))
                                  .setBigContentTitle(intent.getExtras().getString("titulo")));

        //.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.silbido))

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }


}
