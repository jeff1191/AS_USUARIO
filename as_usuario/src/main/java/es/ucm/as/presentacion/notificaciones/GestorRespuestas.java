package es.ucm.as.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import es.ucm.as.integracion.DBHelper;
import es.ucm.as.integracion.Tarea;
import es.ucm.as.negocio.utils.Frecuencia;

import static es.ucm.as.negocio.utils.Frecuencia.DIARIA;
import static es.ucm.as.negocio.utils.Frecuencia.MENSUAL;
import static es.ucm.as.negocio.utils.Frecuencia.SEMANAL;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class GestorRespuestas extends BroadcastReceiver {

    private DBHelper mDBHelper;

    private DBHelper getHelper(Context context) {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Integer respuesta = intent.getExtras().getInt("respuesta");
        Integer idNot = intent.getExtras().getInt("idNotificacion");
        Integer idTarea = intent.getExtras().getInt("idTarea");

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Log.e("prueba", "Va a cerrar la notifiacion ..." + idNot);

        notificationManager.cancel(idNot);

        Log.e("prueba", "Responde a la tarea " + idTarea + " con respuesta " + respuesta);

        responderPregunta(context, idTarea, respuesta);

    }

    // Esta funcion implementa el sistema de aprendizaje automatico de la app
    public void responderPregunta(Context context, Integer idTarea, Integer respuesta) {
        Dao<Tarea, Integer> tareasDao;
        Tarea tarea;
        try {
            tareasDao = getHelper(context).getTareaDao();
            tarea = tareasDao.queryForId(idTarea);
            if(tarea != null) {
                // si respondio que "si"***************************************************************
                if (respuesta == 1) {
                    tarea.setNumSi(tarea.getNumSi() + 1);
                    tarea.setContador(tarea.getContador() + 1);
                    tarea.setNoSeguidos(0); // se reinicia la cuenta de "no" seguidos

                    // si mejora disminuye la frecuencia y se reinician los contadores
                    if (tarea.getContador() == tarea.getMejorar()) {
                        tarea.setFrecuenciaTarea(disminuirFrecuencia(tarea.getFrecuenciaTarea()));
                        tarea.setContador(0);
                        tarea.setNumSi(0);
                        tarea.setNumNo(0);
                    }
                    /**********************************************************************************/


                    // si respondio que "no"////////////////////////////////////////////////////////////
                } else {
                    if (tarea.getNoSeguidos() >= 2) {    // si suma 3 "no" seguidos
                        // Se le aumenta la frecuencia de nuevo
                        tarea.setFrecuenciaTarea(aumentarFrecuencia(tarea.getFrecuenciaTarea()));
                        // Se reinician los contadores
                        tarea.setNumNo(0);
                        tarea.setNumSi(0);
                        tarea.setNoSeguidos(0);
                        tarea.setContador(0);
                    } else {                              // si aun no son 3 "no" seguidos
                        tarea.setNumNo(tarea.getNumNo() + 1);
                        tarea.setNoSeguidos(tarea.getNoSeguidos() + 1);
                        tarea.setContador(tarea.getContador() - 1);
                    }

                }//////////////////////////////////////////////////////////////////////////////////////

                // y se actualiza en la BBDD
                tareasDao.update(tarea);
                Log.e("prueba", "Respuesta guardada");
            }
        } catch (SQLException e) {

        }
    }

    private Frecuencia aumentarFrecuencia (Frecuencia frecuencia){
        Frecuencia nueva = DIARIA;
        switch (frecuencia){
            case DIARIA:
                nueva = DIARIA;
                break;
            case SEMANAL:
                nueva = DIARIA;
                break;
            case MENSUAL:
                nueva = SEMANAL;
                break;
        }
        return nueva;
    }

    private Frecuencia disminuirFrecuencia (Frecuencia frecuencia){
        Frecuencia nueva = MENSUAL;
        switch (frecuencia){
            case DIARIA:
                nueva = SEMANAL;
                break;
            case SEMANAL:
                nueva = MENSUAL;
                break;
            case MENSUAL:
                nueva = MENSUAL;
                break;
        }
        return nueva;
    }
}
