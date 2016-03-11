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
       // Bundle bundle = intent.getExtras();
        //Integer horaAlarma = bundle.getInt("horaAlarma");
        //Integer minutosAlarma = bundle.getInt("minutosAlarma");
        //Integer horaPregunta = bundle.getInt("horaPregunta");
        //Integer minutosPregunta = bundle.getInt("minutosPregunta");
        Log.e("prueba", "Servicio lanzado...");
        alarma.lanzarAlarma(this, 2, 34);
        pregunta.lanzarPregunta(this, 2, 35);
        //alarma.lanzarAlarma(this, 1, 36);
        //pregunta.lanzarPregunta(this, 1, 35);
        //pregunta.lanzarPregunta(this, 8, 36);
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
