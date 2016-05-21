/**
 * 
 */
package es.ucm.as.negocio.usuario;

import java.io.Serializable;

import es.ucm.as.integracion.Usuario;

public class TransferUsuario implements Serializable{

	static final long serialVersionUID = 1L;

	private Integer id;

	private String nombre;

	private String correo;

	private String avatar;

	private Integer puntuacion;

	private String color;

	private Integer tono;

    private String codigoSincronizacion;

	private Integer puntuacionAnterior;

	public TransferUsuario(){	}

	public TransferUsuario(Usuario usuario){
		this.id = usuario.getId();
		this.nombre = usuario.getNombre();
		this.correo = usuario.getCorreo();
		this.avatar = usuario.getAvatar();
		this.puntuacion = usuario.getPuntuacion();
		this.color = usuario.getColor();
		this.tono = usuario.getTono();
		this.puntuacionAnterior = usuario.getPuntuacionAnterior();
		this.codigoSincronizacion = usuario.getCodigoSincronizacion();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getTono() {
		return tono;
	}

	public void setTono(Integer tono) {
		this.tono = tono;
	}

	public Integer getPuntuacionAnterior() {
		return puntuacionAnterior;
	}

	public void setPuntuacionAnterior(Integer puntuacionAnterior) {
		this.puntuacionAnterior = puntuacionAnterior;
	}

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }
}