/**
 * 
 */
package es.ucm.as_usuario.negocio.tarea;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Timestamp;

import es.ucm.as_usuario.negocio.usuario.Usuario;
public class Reto {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "NOMBRE")
	private String nombre;

	@DatabaseField(columnName = "FECHA")
	private Timestamp fechaIni;

	@DatabaseField(columnName = "CONTADOR")
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