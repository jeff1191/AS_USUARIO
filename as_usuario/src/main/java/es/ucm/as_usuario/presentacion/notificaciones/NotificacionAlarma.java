package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import es.ucm.as_usuario.R;

/**
 *
 * Lo guay de sto es que las notificaciones normalmente se lanzan con un notification manager
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
        //Encontrar una foto mas pequeña para el logo en las notificaciones o poner otra imagen
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

        Log.e("prueba", "Con el ID..." + notificationId);

        Intent mostrarAlarma = new Intent(context, GestorRespuestas.class);
        mostrarAlarma.putExtra("titulo", intent.getExtras().getString("titulo"));
        mostrarAlarma.putExtra("texto", intent.getExtras().getString("texto"));
        mostrarAlarma.putExtra("idNotificacion", notificationId);
        PendingIntent aux = PendingIntent.getActivity(context, notificationId+4,  mostrarAlarma, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder n =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Alarma para...")
                        .setContentText(intent.getExtras().getString("titulo"))
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                        .setContentIntent(aux);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }

    public void lanzarAlarma(Context context, Integer hora, Integer minutos, String titulo, String texto)
    {
        Log.e("prueba", "Guarda la notificacion alarma...");

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificacionAlarma.class);
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
        int notificationId = Integer.valueOf(last4Str);

        Log.e("prueba", "Con el ID..." + notificationId);

        PendingIntent pi = PendingIntent.getBroadcast(context, notificationId, i, PendingIntent.FLAG_ONE_SHOT);

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
            Log.e("prueba", "La hora de la alarma se pasa al dia ..." + horaActualCal.getTime().toString());
            horaNotificacion = horaNotificacionCal.getTimeInMillis();
        }

        am.setRepeating(AlarmManager.RTC_WAKEUP, horaNotificacion, 24 * 60 * 60 * 1000, pi);

    }
}
