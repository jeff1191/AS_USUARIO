package es.ucm.as_usuario.integracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Tarea;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by msalitu on 03/03/2016.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "as_user.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Evento, Integer> eventoDao;
    private Dao<Reto, Integer> retoDao;
    private Dao<Tarea, Integer> tareaDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Evento.class);
            TableUtils.createTable(connectionSource, Reto.class);
            TableUtils.createTable(connectionSource, Tarea.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<Evento, Integer> getEventoDao() throws SQLException {
        if (eventoDao == null) {
            eventoDao = getDao(Evento.class);
        }
        return eventoDao;
    }
    public Dao<Reto, Integer> getRetoDao() throws SQLException {
        if (retoDao == null) {
            retoDao = getDao(Reto.class);
        }
        return retoDao;
    }
    public Dao<Tarea, Integer> getTareaDao() throws SQLException {
        if (tareaDao == null) {
            tareaDao = getDao(Tarea.class);
        }
        return tareaDao;
    }

    @Override
    public void close() {
        super.close();
        eventoDao = null;
        retoDao = null;
        tareaDao=null;
    }
}

