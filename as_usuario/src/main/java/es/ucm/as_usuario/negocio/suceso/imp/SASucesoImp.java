package es.ucm.as_usuario.negocio.suceso.imp;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;

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
        Log.d("Info", "consultaEventos");
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
    public void mostrarAlarma() {

    }

    @Override
    public void responderPregunta() {

    }


    @Override
    public Reto verReto() {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        try {
            dao = getHelper().getRetoDao();

            // esto se omitira porque se coge de la BBDD
            /*Reto nuevojiji= new Reto();
            nuevojiji.setNombre("DUCHARSE POR LAS MAÑANAS");
            nuevojiji.setSuperado(false);
            nuevojiji.setContador(28);
            dao.create(nuevojiji);
            ///////////////////////////////////////////*/
            reto = (Reto) dao.queryForId(1);
        } catch (SQLException e) {

        }
        return reto;
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
    public void sincronizar() {

    }
}
