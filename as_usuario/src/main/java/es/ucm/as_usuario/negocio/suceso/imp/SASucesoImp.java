package es.ucm.as_usuario.negocio.suceso.imp;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferReto;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.presentacion.Contexto;

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
    public List<Evento> consultarEventos() {

        Dao<Evento, Integer> eventos;
        List<Evento> listaEventos = null;
        try {
            eventos = getHelper().getEventoDao();

            listaEventos= eventos.queryForAll();
        } catch (SQLException e) {

        }
        return listaEventos;
    }

    @Override
    public TransferReto verReto() {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        TransferReto tr = new TransferReto();
        try {
            dao = getHelper().getRetoDao();

            // esto se omitira porque se coge de la BBDD
            /*Reto nuevojiji= new Reto();
            nuevojiji.setNombre("DUCHARSE POR LAS MAÑANAS");
            nuevojiji.setSuperado(false);
            nuevojiji.setContador(28);
            dao.create(nuevojiji);
            ///////////////////////////////////////////*/

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
            }else {
                reto = new Reto();
                reto.setNombre("NINGUNO");
                reto.setContador(0);
                reto.setSuperado(false);
                dao.update(reto);
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
            Log.e("SASuceso", "consulta tareas");

            tareasDao = getHelper().getTareaDao();

            // Esto no hara falta porque ya lo cogera de la BBDD
/*
            Tarea unaSinMas = new Tarea();
            unaSinMas.setFrecuenciaTarea(Frecuencia.DIARIA);
            unaSinMas.setTextoAlarma("Dale los buenos días a mamá");
            unaSinMas.setTextoPregunta("¿Le has dado los buenos días a mamá?");
            unaSinMas.setFechaIni(new Date());
            unaSinMas.setContador(0);
            unaSinMas.setHoraAlarma(new Date());
            unaSinMas.setHoraPregunta(new Date());
            unaSinMas.setMejorar(30);
            unaSinMas.setNoSeguidos(2);
            unaSinMas.setNumNo(3);
            unaSinMas.setNumSi(1);

            Tarea unaSinMas2 = new Tarea();
            unaSinMas2.setFrecuenciaTarea(Frecuencia.SEMANAL);
            unaSinMas2.setTextoAlarma("Meter las cosas de clase en la mochila");
            unaSinMas2.setTextoPregunta("¿Has metido las cosas de clase en la mochila?");
            unaSinMas2.setFechaIni(new Date());
            unaSinMas2.setContador(0);
            unaSinMas2.setHoraAlarma(new Date());
            unaSinMas2.setHoraPregunta(new Date());
            unaSinMas2.setMejorar(4);
            unaSinMas2.setNoSeguidos(2);
            unaSinMas2.setNumNo(3);
            unaSinMas2.setNumSi(1);

            tareasDao.create(unaSinMas);
            tareasDao.create(unaSinMas2);

            ////////////////////////////////*/

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
                tt.setFechaIni(tareas.get(i).getFechaIni());
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
    public void responderTarea(Integer respuesta) {
        Dao<Tarea, Integer> dao;
        Tarea tarea = new Tarea();
        try {
            Log.e("prueba", "Añadiendo en la database");
            dao = getHelper().getTareaDao();
            //tarea.setTextoPregunta("¿Te cepillaste los dientes Alfredo?");
            //tarea.setTextoAlarma("A cepillarse");
            //TERMINAR DE CREAR LA TAREA
            dao.create(tarea);
            /*
            tarea = (Tarea) dao.queryForId(1);
            if (!tarea.equals(null)) {
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

            }else {
                Log.e("prueba", "No hay nada en la database");
            }*/
        } catch (SQLException e) {

        }
    }

}
