package es.ucm.as.presentacion.notificaciones;

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
import java.util.List;

import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.presentacion.vista.Contexto;

public class ServicioNotificaciones extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("servicio","on bind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("servicio","se crea el servicio");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e("servicio","se destruye el servicio");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Tendriamos que pensar en tener to*do en una cola que la tendria que haber cogido de base de datos - esto es IMPORTANTE
        Log.e("servicio","se lanza el servicio");
        Contexto.getInstancia().setContext(getApplicationContext());

        AlarmManager am =( AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), CargarNotificaciones.class);

        Mensaje info;
        if(intent.getExtras() == null)
            Log.e("servicio", "no le llega info a traves del bundle");
        else {
            info = (Mensaje)intent.getExtras().getSerializable("info");
            List<TransferTarea> tareas = info.getTareas();

            // Elimina las alarmas que hay actualmente para sobreescribir con las nuevas
            for(int j = 0; j < tareas.size(); j++){
                if(tareas.get(j).getNotificacionAlarma() != null) {
                    Log.e("servicio", "cancela la notificacion alarma de " + tareas.get(j).getId() + " id " + tareas.get(j).getNotificacionAlarma());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(Contexto.getInstancia().getContext(),
                            tareas.get(j).getNotificacionAlarma(), new Intent(Contexto.getInstancia().getContext(), NotificacionAlarma.class), PendingIntent.FLAG_ONE_SHOT);
                    am.cancel(pendingIntent);
                    pendingIntent.cancel();
                    Log.e("servicio", "cancela la notificacion pregunta de " + tareas.get(j).getId() + " id " + tareas.get(j).getNotificacionPregunta());
                    PendingIntent pendingIntentPreg = PendingIntent.getBroadcast(Contexto.getInstancia().getContext(),
                            tareas.get(j).getNotificacionPregunta(), new Intent(Contexto.getInstancia().getContext(), NotificacionPregunta.class), PendingIntent.FLAG_ONE_SHOT);
                    am.cancel(pendingIntentPreg);
                    pendingIntentPreg.cancel();
                }
            }
            i.putExtra("info",info);
        }

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        // Establecer la hora a la que cargara de BBDD las notificaciones
        Integer h = 1;
        Integer m = 12;
        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, h);
        horaNotificacionCal.set(Calendar.MINUTE, m);
        horaNotificacionCal.set(Calendar.SECOND, 00);

        long horaNotificacion = horaNotificacionCal.getTimeInMillis();
        setAlarm(am, horaNotificacion, pi);

        Log.e("ServicioNot", "Se cargaran las notificaciones a las " + horaNotificacionCal.getTime().toString());

        return START_NOT_STICKY;
    }

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
