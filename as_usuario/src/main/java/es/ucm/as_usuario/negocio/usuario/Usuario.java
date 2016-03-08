/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario;

import java.util.List;

import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.utils.Frecuencia;


public class Usuario {

	public static Usuario instancia= new Usuario();

	private Integer id;

	private String nombre;

	private String correo;

	private String avatar;

	private Integer puntuacion;

	private String color;

	private String tono;

	private Frecuencia frecuenciaRecibirInforme;

	private String nombreTutor;

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