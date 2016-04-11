package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

import static es.ucm.as_usuario.negocio.utils.Frecuencia.DIARIA;
import static es.ucm.as_usuario.negocio.utils.Frecuencia.MENSUAL;
import static es.ucm.as_usuario.negocio.utils.Frecuencia.SEMANAL;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class GestorRespuestas extends BroadcastReceiver {


    // Esto es por no llamar al comando *********************************************************************************************
    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
        }
        return mDBHelper;
    }

    public void responderPregunta(Integer idTarea, Integer respuesta) {
        Dao<Tarea, Integer> tareasDao;
        Tarea tarea;
        try {
            tareasDao = getHelper().getTareaDao();
            tarea = tareasDao.queryForId(idTarea);

            // si respondio que "si"***************************************************************
            if (respuesta == 1){
                tarea.setNumSi(tarea.getNumSi() + 1);
                tarea.setContador(tarea.getContador() + 1);
                tarea.setNoSeguidos(0); // se reinicia la cuenta de "no" seguidos

                // si mejora disminuye la frecuencia y se reinician los contadores
                if(tarea.getContador() == tarea.getMejorar()){
                    tarea.setFrecuenciaTarea(disminuirFrecuencia(tarea.getFrecuenciaTarea()));
                    tarea.setContador(0);
                    tarea.setNumSi(0);
                    tarea.setNumNo(0);
                }
                /**********************************************************************************/


                // si respondio que "no"////////////////////////////////////////////////////////////
            }else{
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
        } catch (SQLException e) {

        }
    }

    private Frecuencia aumentarFrecuencia (Frecuencia frecuencia){
        Frecuencia nueva = DIARIA;
        switch (frecuencia){
            case DIARIA:
                nueva = DIARIA;
            case SEMANAL:
                nueva = DIARIA;
            case MENSUAL:
                nueva = SEMANAL;
        }
        return nueva;
    }

    private Frecuencia disminuirFrecuencia (Frecuencia frecuencia){
        Frecuencia nueva = MENSUAL;
        switch (frecuencia){
            case DIARIA:
                nueva = SEMANAL;
            case SEMANAL:
                nueva = MENSUAL;
            case MENSUAL:
                nueva = MENSUAL;
        }
        return nueva;
    }
    /**********************************Hasta aqui**********************************************************************************/



    @Override
    public void onReceive(Context context, Intent intent) {

        Integer respuesta = intent.getExtras().getInt("respuesta");
        Integer idNot = intent.getExtras().getInt("idNotificacion");
        Integer idTarea = intent.getExtras().getInt("idTarea");

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Log.e("prueba", "Va a cerrar la notifiacion ..." + idNot);

        notificationManager.cancel(idNot);

        /* Estas lineas comentadas sirven si se hiciera a traves de un comando
        ArrayList<Integer> objetoRespuesta = new ArrayList<Integer>();
        objetoRespuesta.add(0, idTarea);
        objetoRespuesta.add(1, respuesta);
        Log.e("prueba", "Responde a la tarea " + idTarea + " con respuesta " + respuesta);
        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, objetoRespuesta);*/

        responderPregunta(idTarea, respuesta);
    }

}
