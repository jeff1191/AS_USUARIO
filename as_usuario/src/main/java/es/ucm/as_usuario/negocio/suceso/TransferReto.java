/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import java.sql.Timestamp;


public class TransferReto {

	private Integer id;

	private String nombre;

	private Timestamp fechaIni;

	private Integer contador;

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

	public Timestamp getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Timestamp fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}
}