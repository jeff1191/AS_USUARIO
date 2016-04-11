package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

import es.ucm.as_usuario.R;

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
        //Hay que mirar lo del SONIDO y la VIBRACION

        //Si los ids dan problemas, generarlos de otra manera y pasarselos aqui

        Log.e("prueba", "Empieza a crear la notificacion alarma...");

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

        PendingIntent aux = PendingIntent.getBroadcast(context, notificationId+4, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_notificacion)
                        .setContentTitle(intent.getExtras().getString("titulo"))
                        .setContentText(intent.getExtras().getString("texto"))
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_notificacion))
                        .setContentIntent(aux)
                        .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.silbido))
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                  .bigText(intent.getExtras().getString("texto"))
                                  .setBigContentTitle(intent.getExtras().getString("titulo")));

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }


}
