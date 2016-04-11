package es.ucm.as_usuario.presentacion.notificaciones;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;

/**
 * Created by Juan Lu on 08/04/2016.
 */
public class CargarNotificaciones extends BroadcastReceiver {

    private DBHelper mDBHelper;

    private DBHelper getHelper(Context context) {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Lee las tareas de bbdd
        Dao<Tarea, Integer> tareasDao;
        List<Tarea> tareas = new ArrayList<Tarea>();
        List<TransferTarea> transferTareas = new ArrayList<TransferTarea>();
        try {
            Log.e("prueba", "Va a buscar las tareas");
            tareasDao = getHelper(context).getTareaDao();
            tareas = tareasDao.queryForAll();
            String tituloAlarma = "Alarma";
            String tituloPregunta = "Pregunta";
            for(int i = 0; i < tareas.size(); i++){
                //Esto es para dividir el date en horas y minutos
                SimpleDateFormat horasMinutos = new SimpleDateFormat("HH:mm");
                StringTokenizer tokensAlarma = new StringTokenizer(horasMinutos.format
                        (tareas.get(i).getHoraAlarma()),":");
                StringTokenizer tokensPregunta = new StringTokenizer(horasMinutos.format
                        (tareas.get(i).getHoraPregunta()),":");

                lanzarSuceso(context, Integer.parseInt(tokensAlarma.nextToken()),
                        Integer.parseInt(tokensAlarma.nextToken()), tituloAlarma,
                        tareas.get(i).getTextoAlarma(), "alarma", tareas.get(i).getId());
                lanzarSuceso(context, Integer.parseInt(tokensPregunta.nextToken()),
                        Integer.parseInt(tokensPregunta.nextToken()), tituloPregunta,
                        tareas.get(i).getTextoPregunta(), "pregunta", tareas.get(i).getId());

                Log.e("prueba", "Se crean las notificaciones para la tarea " + tareas.get(i).getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public long lanzarSuceso(Context context, Integer hora, Integer minutos, String titulo, String texto, String tipo, Integer idTarea)
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
        i.putExtra("idTarea", idTarea);

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

        //Si ya se ha pasado de la hora actual, para que no se lance inmeditamente
        //se pone que empieze a partir de mañana
        if(horaNotificacion < horaActual){
            horaNotificacionCal.add(Calendar.DAY_OF_MONTH, 1);
            Log.e("prueba", "La hora de la pregunta se pasa al dia ..." + horaNotificacionCal.getTime().toString());
            horaNotificacion = horaNotificacionCal.getTimeInMillis();
        }

        setAlarm(am,horaNotificacion,pi);

        return horaNotificacion;
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
