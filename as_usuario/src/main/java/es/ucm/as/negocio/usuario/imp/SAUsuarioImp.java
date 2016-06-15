/**
 * 
 */
package es.ucm.as.negocio.usuario.imp;



import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import es.ucm.as.R;
import es.ucm.as.integracion.DBHelper;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.integracion.Usuario;
import es.ucm.as.presentacion.vista.Contexto;

public class SAUsuarioImp implements SAUsuario {

	private DBHelper mDBHelper;

	private DBHelper getHelper() {
		if (mDBHelper == null) {
			mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
		}
		return mDBHelper;
	}
	
	public TransferUsuario editarUsuario(TransferUsuario datos) {
		Dao<Usuario, Integer> daoUsuario;
		TransferUsuario ret = new TransferUsuario();
		try {
			daoUsuario = getHelper().getUsuarioDao();
			if (daoUsuario.idExists(1)) {
				Usuario usuario = daoUsuario.queryForId(1);
				usuario.setNombre(datos.getNombre());
				usuario.setAvatar(datos.getAvatar());
				usuario.setColor(datos.getColor());
				usuario.setTono(datos.getTono());
				daoUsuario.update(usuario);
			}else
				return null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}

	/*
	puntuacion = (10 * nºtareas positivas / nº tareas totales
	* */
	@Override
	public Integer calcularPuntuacion() {
		Integer ret;
		// Se cogen las tareas de la BBDD
		SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
		List<TransferTarea> tareas = ss.consultarTareas();
		// Si no tiene ninguna la puntuacion es 5
		if(tareas.size() == 0)
			return 10;
		// Se cuentan las positivas y se realiza el calculo
		int positivas = 0;
		for (int i = 0; i < tareas.size(); i++) {
			if (tareas.get(i).getNumSi() - tareas.get(i).getNumNo() >= 0)
				positivas++;
		}
		ret = 10*positivas/tareas.size();
		// Se actualiza la puntuacion en la BBDD
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();
			Usuario usuario = daoUsuario.queryForAll().get(0);
			usuario.setPuntuacionAnterior(usuario.getPuntuacion());
			usuario.setPuntuacion(ret);
			daoUsuario.update(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Log.e("SASuceso", "actualiza puntuacion" + ret.toString());
		return ret;
	}

	@Override
	public void crearUsuario(TransferUsuario transferUsuario) {
		
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();

			Usuario usuario = new Usuario();

			// actualizamos los valores del nuevo usuario con los introducidos o por defecto
			if (transferUsuario.getNombre() != null)
				usuario.setNombre(transferUsuario.getNombre());
			else
				usuario.setNombre("Usuario");

			if (transferUsuario.getCorreo() != null)
				usuario.setCorreo(transferUsuario.getCorreo());

			if (transferUsuario.getAvatar() != null)
				usuario.setAvatar(transferUsuario.getAvatar());

			if (transferUsuario.getPuntuacion() != null)
				usuario.setPuntuacion(transferUsuario.getPuntuacion());
			else
				usuario.setPuntuacion(10);

			if (transferUsuario.getPuntuacionAnterior() != null)
				usuario.setPuntuacionAnterior(transferUsuario.getPuntuacionAnterior());
			else
				usuario.setPuntuacionAnterior(10);

			if (transferUsuario.getColor() != null)
				usuario.setColor(transferUsuario.getColor());

			if (transferUsuario.getTono() != null)
				usuario.setTono(transferUsuario.getTono());
			else
				usuario.setTono(R.raw.defecto);

			if (transferUsuario.getCodigoSincronizacion() != null)
				usuario.setCodigoSincronizacion(transferUsuario.getCodigoSincronizacion());

			// se crea la fila en la tabla de la BBDD
			daoUsuario.create(usuario);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TransferUsuario consultarUsuario() {
        Log.e("SASuceso", "consultarUsuario");
		Dao<Usuario, Integer> daoUsuario;
		TransferUsuario transferUsuario = new TransferUsuario();
		try {

			daoUsuario = getHelper().getUsuarioDao();

			if (!daoUsuario.idExists(1))
				return null;
			else {
				Usuario u = daoUsuario.queryForId(1);
				// metemos los datos en un transfer
				transferUsuario.setId(u.getId());
				if (u.getNombre() != null)
					transferUsuario.setNombre(u.getNombre());
				if (u.getCorreo() != null)
					transferUsuario.setCorreo(u.getCorreo());
				if (u.getAvatar() != null)
					transferUsuario.setAvatar(u.getAvatar());
				if (u.getPuntuacion() != null)
					transferUsuario.setPuntuacion(u.getPuntuacion());
				if (u.getPuntuacionAnterior() != null)
					transferUsuario.setPuntuacionAnterior(u.getPuntuacionAnterior());
				if (u.getColor() != null)
					transferUsuario.setColor(u.getColor());
				if (u.getTono() != null)
					transferUsuario.setTono(u.getTono());
				else
					transferUsuario.setTono(R.raw.defecto);
				if (u.getCodigoSincronizacion() != null)
					transferUsuario.setCodigoSincronizacion(u.getCodigoSincronizacion());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transferUsuario;
	}
}