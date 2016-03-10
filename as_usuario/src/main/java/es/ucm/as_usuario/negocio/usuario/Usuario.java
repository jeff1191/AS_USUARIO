/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.List;

import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.utils.Frecuencia;


public class Usuario {

	public static Usuario instancia= new Usuario();

	@DatabaseField(columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "NOMBRE")
	private String nombre;

	@DatabaseField(columnName = "CORREO")
	private String correo;

	@DatabaseField(columnName = "AVATAR")
	private String avatar;

	@DatabaseField(columnName = "PUNTUACION")
	private Integer puntuacion;

	@DatabaseField(columnName = "PUNTUACION_ANTERIOR")
	private Integer puntuacionAnterior;

	@DatabaseField(columnName = "COLOR")
	private String color;

	@DatabaseField(columnName = "TONO")
	private String tono;

	@DatabaseField(columnName = "FRECUENCIA", dataType = DataType.ENUM_STRING)
	private Frecuencia frecuenciaRecibirInforme;

	@DatabaseField(columnName = "NOMBRE_TUTOR")
	private String nombreTutor;

	@DatabaseField(columnName = "CORREO_TUTOR")
	private String correoTutor;

	private Usuario(){	}

	public static Usuario getInstancia() {
		return instancia;
	}

	public Integer getId() {		return this.id;	}

	public String getNombre() {		return this.nombre;	}

	public String getCorreo() {
		return this.correo;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public Integer getPuntuacion() {
		return this.puntuacion;
	}

	public static void setInstancia(Usuario instancia) {
		Usuario.instancia = instancia;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPuntuacionAnterior() {
		return puntuacionAnterior;
	}

	public void setPuntuacionAnterior(Integer puntuacionAnterior) {
		this.puntuacionAnterior = puntuacionAnterior;
	}

	public Frecuencia getFrecuenciaRecibirInforme() {
		return frecuenciaRecibirInforme;
	}

	public String getColor() {
		return this.color;
	}

	public String getTono() {
		return this.tono;
	}

	public Frecuencia getFrecuenciaRecibirInformes() {
		return this.frecuenciaRecibirInforme;
	}

	public String getNombreTutor() {
		return nombreTutor;
	}

	public String getCorreoTutor() {
		return correoTutor;
	}

	public void setNombre(String nuevoNombre) {
		this.nombre=nuevoNombre;
	}

	public void setCorreo(String nuevoCorreo) {
		this.correo=nuevoCorreo;
	}

	public void setAvatar(String nuevoAvatar) {	this.avatar=nuevoAvatar;}

	public void setPuntuacion(Integer nuevaPuntuacion) {this.puntuacion=nuevaPuntuacion;	}

	public void setTono(String nuevoTono) {		this.tono=nuevoTono;	}

	public void setColor(String nuevoColor) {
		this.color=nuevoColor;
	}

	public void setFrecuenciaRecibirInforme(Frecuencia nuevaFrecuencia) {
		this.frecuenciaRecibirInforme=nuevaFrecuencia;
	}

	public void setNombreTutor(String nuevoNombreTutor) {
		this.nombreTutor=nuevoNombreTutor;
	}

	public void setCorreoTutor(String nuevoCorreoTutor) {
		this.correoTutor=nuevoCorreoTutor;
	}

}