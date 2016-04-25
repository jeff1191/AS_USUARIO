/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario;

import com.j256.ormlite.field.DatabaseField;


public class Usuario {

	@DatabaseField(generatedId = true, columnName = "ID")
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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPuntuacionAnterior() {
		return puntuacionAnterior;
	}

	public void setPuntuacionAnterior(Integer puntuacionAnterior) {
		this.puntuacionAnterior = puntuacionAnterior;
	}

	public String getColor() {
		return this.color;
	}

	public String getTono() {
		return this.tono;
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

}