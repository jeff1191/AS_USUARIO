/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import es.ucm.as_usuario.negocio.tarea.Respuesta;
import es.ucm.as_usuario.negocio.tarea.Reto;
import es.ucm.as_usuario.negocio.tarea.Tarea;


public class Usuario {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "NOMBRE")
	private String nombre;

	@DatabaseField(columnName = "CORREO")
	private String correo;

	@DatabaseField(columnName = "AVATAR")
	private String avatar;

	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private Reto reto;

	@DatabaseField(columnName = "INFORME")
	private String informe;

	@DatabaseField(columnName = "PUNTUACION")
	private Integer puntuacion;

	@DatabaseField(columnName = "COLOR")
	private String color;

	@DatabaseField(columnName = "TONO")
	private String tono;

	@DatabaseField(columnName = "FREC")
	private String frecuenciaRecibirInforme;

	//@ForeignCollectionField
	//private List<Tarea> tareas;

	@DatabaseField(columnName = "NOMBRE_TUTOR")
	private String nombreTutor;

	@DatabaseField(columnName = "CORREO_TUTOR")
	private String correoTutor;

	@DatabaseField(columnName = "URL_AYUDA")
	private String ayuda;

	//@ForeignCollectionField
	//private ForeignCollection<Respuesta> respuestas;

	public Integer getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getCorreo() {
		return this.correo;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public Reto getReto() {
		return this.reto;
	}

	public String getInforme() {
		return this.informe;
	}

	public Integer getPuntuacion() {
		return this.puntuacion;
	}
	
	public String getColor() {
		return this.color;
	}

	public String getTono() {
		return this.tono;
	}

	public String getFrecuenciaRecibirInformes() {
		return this.frecuenciaRecibirInforme;
	}
/*
	public List<Tarea> getTareas() {
		return tareas;
	}*/

	public String getNombreTutor() {
		return nombreTutor;
	}

	public String getCorreoTutor() {
		return correoTutor;
	}

	public String getAyuda() {
		return this.ayuda;
	}

	public void setNombre(String nuevoNombre) {
		this.nombre=nuevoNombre;
	}

	public void setCorreo(String nuevoCorreo) {
		this.correo=nuevoCorreo;
	}

	public void setAvatar(String nuevoAvatar) {
		this.avatar=nuevoAvatar;
	}

	public void setPuntuacion(Integer nuevaPuntuacion) {
		this.puntuacion=nuevaPuntuacion;
	}

	public void setReto(Reto nuevoReto) {
		this.reto=nuevoReto;
	}

	public void setInforme(String nuevoInforme) {
		this.informe=nuevoInforme;
	}

	public void setTono(String nuevoTono) {
		this.tono=nuevoTono;
	}

	public void setColor(String nuevoColor) {
		this.color=nuevoColor;
	}

	public void setFrecuenciaRecibirInforme(String nuevaFrecuencia) {
		this.frecuenciaRecibirInforme=nuevaFrecuencia;
	}
/*
	public void setTareas(List<Tarea> nuevasTareas) {
		this.tareas=nuevasTareas;
	}*/

	public void setNombreTutor(String nuevoNombreTutor) {
		this.nombreTutor=nuevoNombreTutor;
	}

	public void setCorreoTutor(String nuevoCorreoTutor) {
		this.correoTutor=nuevoCorreoTutor;
	}
}