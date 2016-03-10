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
		Usuario.getInstancia().setNombre(datos.getNombre());
		Usuario.getInstancia().setFrecuenciaRecibirInforme(datos.getFrecuenciaRecibirInforme());
		TransferUsuario ret = new TransferUsuario();
		ret.setNombre(Usuario.getInstancia().getNombre());
		ret.setFrecuenciaRecibirInforme(Usuario.getInstancia().getFrecuenciaRecibirInformes());
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
			Usuario.getInstancia().setNombre(transferUsuario.getNombre());
			Usuario.getInstancia().setPuntuacion(transferUsuario.getPuntuacion());
			Usuario.getInstancia().setPuntuacionAnterior(transferUsuario.getPuntuacionAnterior());
			daoUsuario.create(Usuario.getInstancia());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TransferUsuario usuarioActivo() {
		TransferUsuario ret = new TransferUsuario();
		ret.setNombre(Usuario.getInstancia().getNombre());
		ret.setFrecuenciaRecibirInforme(Usuario.getInstancia().getFrecuenciaRecibirInformes());
		return ret;
	}
}