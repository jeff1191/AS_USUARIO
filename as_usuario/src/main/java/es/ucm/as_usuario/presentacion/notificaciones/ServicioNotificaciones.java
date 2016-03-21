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

        //Le tienes que pasar el titulo(tipo de las preguntas) y un array de id's de tareas

        alarma.lanzarAlarma(this, 19, 34, "Desayunar", "Vamos a desayunar!");
        alarma.lanzarAlarma(this, 19, 35, "Aseo personal", "Es la hora del aseo... tienes que... " +
                "/Lavarte la cara, las axilas, etc..." +
                "/Lavarte los dientes" +
                "/Ponerte desodorante" +
                "/Ponerte colonia" +
                "/Peinarte");
      /*  pregunta.lanzarPregunta(Contexto.getInstancia().getContext().getApplicationContext()
                , 16, 51, "Desayunar", "¿Has desayunado?/¿Has dejado todo recogido?", 1);
        pregunta.lanzarPregunta(Contexto.getInstancia().getContext().getApplicationContext()
                , 16, 54, "Aseo personal", "¿Te has lavado antes de vestirte? Cara, Axilas..." +
                "/¿Te has lavado los dientes?" +
                "/¿Te has puesto desodorante y colonia?" +
                "/¿Te has peinado?", 2);

*/
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
