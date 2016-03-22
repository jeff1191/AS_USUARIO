/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario.imp;


import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import es.ucm.as_usuario.integracion.Mail;
import es.ucm.as_usuario.negocio.usuario.SAUsuario;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
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
			else
				usuario.setFrecuenciaRecibirInforme(Frecuencia.SEMANAL);

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
				if (u.getPuntuacionAnterior() != null)
					transferUsuario.setPuntuacionAnterior(u.getPuntuacionAnterior());
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

	@Override
	public void enviarCorreo() {

		String mail = "";
		String name = "";
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();
			Usuario u = daoUsuario.queryForId(1);
			mail= u.getCorreo();
			name = u.getNombre();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* Enviar correo abriendo aplicación/////////////////////////////////////////////////////

		//Instanciamos un Intent del tipo ACTION_SEND
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		//Definimos la tipologia de datos del contenido dle Email en este caso text/html
		emailIntent.setType("application/pdf");
		// Indicamos con un Array de tipo String las direcciones de correo a las cuales
		//queremos enviar el texto
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
		// Definimos un titulo para el Email
		emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Informe AS");
		// Definimos un Asunto para el Email
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Informe AS");
		// Obtenemos la referencia al texto y lo pasamos al Email Intent
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "¡Hola " + name + "!\n " +
				"Este es tu progreso hasta el momento. Sigue esforzándote para continuar mejorando."
		+ "\n¡Ánimo!" + "\n\nEnviado desde AS");

		Uri uri = Uri.parse( new File("file://" + "/sdcard/Download/AS/Informe.pdf").toString());
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

		Contexto.getInstancia().getContext().startActivity(emailIntent);

		//*/////////////////////////////////////////////////////////////////////////////////////////



			// Este codigo usa la clase Mail del paquete de integración
			Mail m = new Mail(mail, "aa");

			String[] toArr = {mail};
			m.setTo(toArr);
			m.setFrom(mail);
			m.setSubject("Informe AS");
			m.setBody("¡Hola " + name + "!\n" +
					"Este es tu progreso hasta el momento. Sigue esforzándote para continuar mejorando."
					+ "\n¡Ánimo!" + "\n\nEnviado desde AS");

			try {
				m.addAttachment("sdcard/Download/AS/Informe.pdf");

				if (m.send()) {
					Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(), "Email was not sent.", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				//Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
				Toast.makeText(Contexto.getInstancia().getContext().getApplicationContext(), "Exception autenticación u otra cosa " , Toast.LENGTH_LONG).show();
				Log.e("MailApp", "Could not send email", e);
			}


		// Esto sería usando la clase de Juanlu, lo malo es que solo sirve para gmail
		/*Intent correo = new Intent (Contexto.getInstancia().getContext().getApplicationContext(), Correo.class);
		correo.putExtra("destinatario", mail);
		correo.putExtra("titulo", "Informe AS");
		correo.putExtra("nombre", name);
		correo.putExtra("texto", "¡Hola " + name + "!\n " +
				"Este es tu progreso hasta el momento. Sigue esforzándote para continuar mejorando."
				+ "\n¡Ánimo!" + "\n\nEnviado desde AS");
		Contexto.getInstancia().getContext().startActivity(correo);*/
	}


}