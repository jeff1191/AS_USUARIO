package es.ucm.as_usuario.negocio.tarea.imp;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import es.ucm.as_usuario.negocio.base_datos.Contexto;
import es.ucm.as_usuario.negocio.base_datos.DBHelper;
import es.ucm.as_usuario.negocio.tarea.Reto;
import es.ucm.as_usuario.negocio.tarea.SATarea;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.presentacion.MainActivity;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class SATareaImp implements SATarea {

    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {

            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext(), DBHelper.class);
        }
        return mDBHelper;
    }
    @Override
    public void editarUsuario() {

    }

    @Override
    public void ayudaUsuario() {

    }

    @Override
    public void consultarInforme() {

    }

    @Override
    public void consultarTareasPuntuales() {

    }

    @Override
    public void consultarReto() {

    }

    @Override
    public void mostrarAlarma() {

    }

    @Override
    public void responderPregunta() {

    }

    @Override
    public void responderReto(Integer respuesta) {
        Dao<Reto, Integer> dao;
        //Dao dao;
        try {
            dao = getHelper().getRetoDao();
            Reto nuevojiji= new Reto();
            nuevojiji.setNombre("LO QUE SEA");
            nuevojiji.setContador(0);
            dao.create(nuevojiji);


            Reto nuevoReto= (Reto) dao.queryForId(1);
            nuevoReto.setContador(nuevoReto.getContador()+respuesta);
            dao.update(nuevoReto);
        } catch (SQLException e) {
        }
    }

    @Override
    public void sincronizar() {

    }
}
