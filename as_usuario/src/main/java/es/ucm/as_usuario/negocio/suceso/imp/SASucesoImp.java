package es.ucm.as_usuario.negocio.suceso.imp;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.integracion.Parser;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferEvento;
import es.ucm.as_usuario.negocio.suceso.TransferReto;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.presentacion.notificaciones.NotificacionAlarma;
import es.ucm.as_usuario.presentacion.notificaciones.NotificacionPregunta;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class SASucesoImp implements SASuceso {

    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
        }
        return mDBHelper;
    }


    @Override
    public List<TransferEvento> consultarEventos() {

        Dao<Evento, Integer> eventos;
        List<Evento> listaEventos = null;
        List<TransferEvento> transferEventos = new ArrayList<TransferEvento>();
        try {
            eventos = getHelper().getEventoDao();
            listaEventos= eventos.queryForAll();
            for(int i = 0; i < listaEventos.size(); i++)
                transferEventos.add(new TransferEvento(listaEventos.get(i)));

        } catch (SQLException e) {

        }
        return transferEventos;
    }

    @Override
    public TransferReto verReto() {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        TransferReto tr = new TransferReto();
        try {
            dao = getHelper().getRetoDao();

            if (dao.idExists(1)) {  // comprueba si hay algun reto en la BBDD
                reto = (Reto) dao.queryForId(1);
                tr.setContador(reto.getContador());
                tr.setId(reto.getId());
                tr.setNombre(reto.getNombre());
                tr.setSuperado(reto.getSuperado());
            }else
                return null;

        } catch (SQLException e) {

        }
        return tr;
    }


    @Override
    public Integer responderReto(Integer respuesta) {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        try {
            dao = getHelper().getRetoDao();
            reto = (Reto) dao.queryForId(1);
            if (!reto.equals(null)) {
                //Si el reto no esta superado y se puede incrementar o decrementar aun se modifica
                if (!reto.getSuperado() && respuesta == -1 && reto.getContador() > 0 ||
                        !reto.getSuperado() && respuesta == 1 && reto.getContador() <= 30) {
                    reto.setContador(reto.getContador() + respuesta);
                    dao.update(reto);
                }
                if (reto.getContador() == 30) {
                    reto.setSuperado(true);
                    dao.update(reto);
                }
            }
        } catch (SQLException e) {

        }
        return reto.getContador();
    }


    @Override
    public List<TransferTarea> consultarTareas() {

        Dao<Tarea, Integer> tareasDao;
        List<Tarea> tareas = new ArrayList<Tarea>();
        List<TransferTarea> transferTareas = new ArrayList<TransferTarea>();

        try {

            tareasDao = getHelper().getTareaDao();

            tareas = tareasDao.queryForAll();
            for(int i = 0; i < tareas.size(); i++){

                TransferTarea tt = new TransferTarea();
                tt.setId(tareas.get(i).getId());
                tt.setContador(tareas.get(i).getContador());
                tt.setTextoPregunta(tareas.get(i).getTextoPregunta());
                tt.setTextoAlarma(tareas.get(i).getTextoAlarma());
                tt.setHoraPregunta(tareas.get(i).getHoraPregunta());
                tt.setHoraAlarma(tareas.get(i).getHoraAlarma());
                tt.setMejorar(tareas.get(i).getMejorar());
                tt.setFrecuenciaTarea(tareas.get(i).getFrecuenciaTarea());
                tt.setNoSeguidos(tareas.get(i).getNoSeguidos());
                tt.setNumNo(tareas.get(i).getNumNo());
                tt.setNumSi(tareas.get(i).getNumSi());
                transferTareas.add(tt);
            }

        } catch (SQLException e) {

        }
        return transferTareas;
    }

    @Override
    public void cargarTareasBBDD() {
        Parser p = new Parser();
        Dao<Tarea, Integer> tareaDao;
        p.readTareas();   // lee del fichero y convierte en tareas

        // crea las nuevas tareas en BBDD si hubiera
        ArrayList<Tarea> tareasBBDD = p.getTareas();
        for (int i = 0; i < tareasBBDD.size(); i++){
            try {
                tareaDao = getHelper().getTareaDao();
                if (tareaDao.queryForEq("TEXTO_ALARMA", tareasBBDD.get(i).getTextoAlarma()).size() == 0)
                    tareaDao.create(tareasBBDD.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // elimina las tareas que el tutor ha deshabilitado o borrado
        ArrayList<Tarea> tareasObsoletas = p.getTareasObsoletas();
        for (int i = 0; i < tareasObsoletas.size(); i++){
            try {
                tareaDao = getHelper().getTareaDao();
                if (tareaDao.queryForEq("TEXTO_ALARMA", tareasObsoletas.get(i).getTextoAlarma()).size() != 0)
                    tareaDao.delete(tareaDao.queryForEq("TEXTO_ALARMA", tareasObsoletas.get(i).getTextoAlarma()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void cargarRetoBBDD() {
        Parser p = new Parser();
        Dao<Reto, Integer> retoDao;
        String texto = p.readReto();   // lee del fichero y obtiene el texto del reto
        try {
            retoDao = getHelper().getRetoDao();
            Reto reto = p.toReto(texto);
            if (reto != null && !retoDao.idExists(1))
                retoDao.create(reto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cargarEventosBBDD() {
        Parser p = new Parser();
        Dao<Evento, Integer> eventoDao;
        p.readEventos();   // lee del fichero y convierte en eventos

        // crea las nuevas tareas en BBDD si hubiera
        ArrayList<Evento> eventosBBDD = p.getEventos();
        for (int i = 0; i < eventosBBDD.size(); i++){
            try {
                eventoDao = getHelper().getEventoDao();
                if (eventoDao.queryForEq("TEXTO_ALARMA", eventosBBDD.get(i).getTextoAlarma()).size() == 0)
                    eventoDao.create(eventosBBDD.get(i));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // elimina las tareas que el tutor ha deshabilitado o borrado
        ArrayList<Evento> eventosObsoletos = p.getEventosObsoletos();
        for (int i = 0; i < eventosObsoletos.size(); i++){
            try {
                eventoDao = getHelper().getEventoDao();
                if (eventoDao.queryForEq("TEXTO_ALARMA", eventosObsoletos.get(i).getTextoAlarma()).size() != 0)
                    eventoDao.delete(eventoDao.queryForEq("TEXTO_ALARMA", eventosObsoletos.get(i).getTextoAlarma()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarNotificaciones() {

        NotificacionAlarma alarma = new NotificacionAlarma();
        NotificacionPregunta pregunta = new NotificacionPregunta();
        Log.e("prueba", "Variables inicializadas...");
        Log.e("prueba", "Vamos a ello...");
        alarma.lanzarAlarma(Contexto.getInstancia().getContext().getApplicationContext(),
                14, 19, "Desayunar", "Vamos a desayunar!");
        pregunta.lanzarPregunta(Contexto.getInstancia().getContext().getApplicationContext(),
                14, 20 , "Desayunar", "¿Has desayunado?/¿Has dejado todo recogido?");
        /*alarma.lanzarAlarma(Contexto.getInstancia().getContext().getApplicationContext(),
                21, 5, "Aseo personal", "Es la hora del aseo... tienes que... " +
                        "/Lavarte la cara, las axilas, etc..." +
                        "/Lavarte los dientes" +
                        "/Ponerte desodorante" +
                        "/Ponerte colonia" +
                        "/Peinarte");
        pregunta.lanzarPregunta(Contexto.getInstancia().getContext().getApplicationContext(),
                21, 5, "Aseo personal", "¿Te has lavado antes de vestirte? Cara, Axilas..." +
                "/¿Te has lavado los dientes?" +
                "/¿Te has puesto desodorante y colonia?" +
                "/¿Te has peinado?");
        */
       /* Parser p = new Parser();
        Dao<Tarea, Integer> tareaDao;
        p.readTareas();   // lee del fichero y convierte en tareas

        // crea las nuevas tareas en BBDD si hubiera
        ArrayList<Tarea> tareasBBDD = p.getTareas();
        for (int i = 0; i < tareasBBDD.size(); i++){
            try {
                tareaDao = getHelper().getTareaDao();
                if (tareaDao.queryForEq("TEXTO_ALARMA", tareasBBDD.get(i).getTextoAlarma()).size() == 0)
                    tareaDao.create(tareasBBDD.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // elimina las tareas que el tutor ha deshabilitado o borrado
        ArrayList<Tarea> tareasObsoletas = p.getTareasObsoletas();
        for (int i = 0; i < tareasObsoletas.size(); i++){
            try {
                tareaDao = getHelper().getTareaDao();
                if (tareaDao.queryForEq("TEXTO_ALARMA", tareasObsoletas.get(i).getTextoAlarma()).size() != 0)
                    tareaDao.delete(tareaDao.queryForEq("TEXTO_ALARMA", tareasObsoletas.get(i).getTextoAlarma()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }
}
