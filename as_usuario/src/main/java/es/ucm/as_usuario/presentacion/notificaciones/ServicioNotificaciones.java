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

import es.ucm.as_usuario.negocio.suceso.Tarea;

public class ServicioNotificaciones extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onCreate()
     */
    @Override
    public void onCreate() {
        Log.d(this.getClass().getSimpleName(),"onCreate()");
        super.onCreate();
    }

   /* private Alarm getNext(){
        Set<Alarm> alarmQueue = new TreeSet<Alarm>(new Comparator<Alarm>() {
            @Override
            public int compare(Alarm lhs, Alarm rhs) {
                int result = 0;
                long diff = lhs.getAlarmTime().getTimeInMillis() - rhs.getAlarmTime().getTimeInMillis();
                if(diff>0){
                    return 1;
                }else if (diff < 0){
                    return -1;
                }
                return result;
            }
        });

        Database.init(getApplicationContext());
        List<Alarm> alarms = Database.getAll();

        for(Alarm alarm : alarms){
            if(alarm.getAlarmActive())
                alarmQueue.add(alarm);
        }
        if(alarmQueue.iterator().hasNext()){
            return alarmQueue.iterator().next();
        }else{
            return null;
        }
    }
    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onDestroy()
     */
    @Override
    public void onDestroy() {
       // Database.deactivate();
        super.onDestroy();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Tendriamos que pensar en tener to*do en una cola que la tendria que haber cogido de base de datos - esto es IMPORTANTE
        Log.d(this.getClass().getSimpleName(),"onStartCommand() SERVICIO");
        //Alarm alarm = getNext();
        //obtener la siguiente tarea
        Tarea a = new Tarea();
        a.setTextoPregunta("A1");
        Tarea a1 = new Tarea();
        a1.setTextoPregunta("A2");
        Tarea a2 = new Tarea();
        a2.setTextoPregunta("A3");
        Tarea a3 = new Tarea();
        a3.setTextoPregunta("A4");
        /*
        if(null != alarm){
            alarm.schedule(getApplicationContext());
            Log.d(this.getClass().getSimpleName(),alarm.getTimeUntilNextAlarmMessage());

        }else{*/
            Intent myIntent = new Intent(getApplicationContext(), NotificacionPregunta.class);
            myIntent.putExtra("titulo", a.getTextoPregunta());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            lanzarSuceso(getApplicationContext(), 11, 25, a.getTextoAlarma(), "TEXOTOTOOTOT", "pregunta");
            lanzarSuceso(getApplicationContext(), 11, 23, a1.getTextoAlarma(), "TEXOTOTOOTOT222222", "alarma");

           // alarmManager.cancel(pendingIntent);
        //}
        return START_NOT_STICKY;
    }


    @TargetApi(19)
    private void setAlarmFromKitkat(AlarmManager am, long ms, PendingIntent pi){
        am.setExact(AlarmManager.RTC, ms, pi);
    }


    ///PARA OBTENER LOS MILISSEGUNDOS
    public long lanzarSuceso(Context context, Integer hora, Integer minutos, String titulo, String texto, String tipo)
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

       // am.set(AlarmManager.RTC_WAKEUP, horaNotificacion, pi);



        setAlarm(am,horaNotificacion,pi);


        // am.setRepeating(AlarmManager.RTC_WAKEUP, horaNotificacion, AlarmManager.INTERVAL_DAY, pi);

        return horaNotificacion;
    }

    private void setAlarm(AlarmManager am,long ms, PendingIntent pendingIntent){
        /*final AlarmManager am = (AlarmManager) mCtx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastReceiverAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/


        //  long ms = lanzarPregunta(getApplicationContext(),15,29,a.getTextoAlarma(), "texto 1");

        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.KITKAT) {
            am.set(AlarmManager.RTC, ms, pendingIntent);
        } else {
            setAlarmFromKitkat(am, ms, pendingIntent);
        }
    }

}
