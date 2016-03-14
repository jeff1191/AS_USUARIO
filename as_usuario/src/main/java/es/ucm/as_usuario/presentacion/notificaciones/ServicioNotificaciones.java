package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Juan Lu on 09/03/2016.
 */
public class ServicioNotificaciones extends Service{

    private NotificacionAlarma alarma;
    private NotificacionPregunta pregunta;

    public void onCreate()
    {
        alarma = new NotificacionAlarma();
        pregunta = new NotificacionPregunta();
        Log.e("prueba", "Servicio creado y variables inicializadas...");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        /*Bundle bundle = intent.getExtras();
        Integer horaAlarma = bundle.getInt("horaAlarma");
        Integer minutosAlarma = bundle.getInt("minutosAlarma");
        Integer horaPregunta = bundle.getInt("horaPregunta");
        Integer minutosPregunta = bundle.getInt("minutosPregunta");
        String titulo = bundle.getString("titulo");
        String textoAlarma = bundle.getString("textoAlarma");
        String textoPregunta = bundle.getString("textoPregunta");
        Integer idTarea = bundle.getInt("idTarea");
*/
        Log.e("prueba", "Servicio lanzado...");

       // alarma.lanzarAlarma(this, 3, 44, "holaa", "Esto es una pruebaaa");
       // pregunta.lanzarPregunta(this, 3, 45, "holaa", "Esto es una pruebaaa con botones", 1);
        //alarma.lanzarAlarma(this, 1, 36);
        //pregunta.lanzarPregunta(this, 1, 35);
        //pregunta.lanzarPregunta(this, 8, 36);

        //La idea es que solo esten estas dos, ya que para cada tarea se necesitan una de cada
        //alarma.lanzarAlarma(this, horaAlarma, minutosAlarma, titulo, textoAlarma);
        //pregunta.lanzarPregunta(this, horaPregunta, minutosPregunta, titulo, textoPregunta, idTarea);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
