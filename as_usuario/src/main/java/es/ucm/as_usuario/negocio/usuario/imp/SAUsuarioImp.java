/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario.imp;


import es.ucm.as_usuario.negocio.usuario.SAUsuario;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.presentacion.Contexto;

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
				usuario.setFrecuenciaRecibirInforme(datos.getFrecuenciaRecibirInforme());
				usuario.setAvatar(datos.getAvatar());
				ret.setNombre(usuario.getNombre());
				ret.setFrecuenciaRecibirInforme(usuario.getFrecuenciaRecibirInformes());
				ret.setAvatar(usuario.getAvatar());
				daoUsuario.update(usuario);
			}else
				return null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void sincronizar() {
		
	}

	/*
	puntuacion = (10 * nºtareas positivas / nº tareas totales
	* */
	@Override
	public Integer calcularPuntuacion() {
		SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
		List<TransferTarea> tareas = ss.consultarTareas();
		int positivas = 0;
		for (int i = 0; i < tareas.size(); i++)
			if (tareas.get(i).getNumSi() - tareas.get(i).getNumNo() >= 0)
				positivas++;
		return 10*positivas/tareas.size();
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
				usuario.setPuntuacion(0);

			if (transferUsuario.getPuntuacionAnterior() != null)
				usuario.setPuntuacionAnterior(transferUsuario.getPuntuacionAnterior());
			else
				usuario.setPuntuacionAnterior(0);

			if (transferUsuario.getColor() != null)
				usuario.setColor(transferUsuario.getColor());

			if (transferUsuario.getTono() != null)
				usuario.setTono(transferUsuario.getTono());

			if (transferUsuario.getFrecuenciaRecibirInforme() != null)
				usuario.setFrecuenciaRecibirInforme(transferUsuario.getFrecuenciaRecibirInforme());

			if (transferUsuario.getNombreTutor() != null)
				usuario.setNombreTutor(transferUsuario.getNombreTutor());

			if (transferUsuario.getCorreoTutor() != null)
				usuario.setCorreoTutor(transferUsuario.getCorreoTutor());

			// se crea la fila en la tabla de la BBDD
			daoUsuario.create(usuario);


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TransferUsuario consultarUsuario() {
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
				if (u.getColor() != null)
					transferUsuario.setColor(u.getColor());
				if (u.getTono() != null)
					transferUsuario.setTono(u.getTono());
				if (u.getFrecuenciaRecibirInforme() != null)
					transferUsuario.setFrecuenciaRecibirInforme(u.getFrecuenciaRecibirInforme());
				if (u.getNombreTutor() != null)
					transferUsuario.setNombreTutor(u.getNombreTutor());
				if (u.getCorreoTutor() != null)
					transferUsuario.setCorreoTutor(u.getCorreoTutor());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transferUsuario;
	}
}