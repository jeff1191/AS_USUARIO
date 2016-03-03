package es.ucm.as_usuario.negocio.base_datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import es.ucm.as_usuario.negocio.tarea.Reto;
import es.ucm.as_usuario.negocio.tarea.Tarea;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
/**
 * Created by msalitu on 03/03/2016.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "as_user.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Reto, Integer> retoDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Reto.class);
           // TableUtils.createTable(connectionSource, Usuario.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }
    public Dao<Reto, Integer> getRetoDao() throws SQLException {
        if (retoDao == null) {
            retoDao = getDao(Reto.class);
        }
        return retoDao;
    }

    @Override
    public void close() {
        super.close();
        usuarioDao = null;
        retoDao = null;
    }
}

