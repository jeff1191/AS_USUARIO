/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Timestamp;

public class Reto {

	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "NOMBRE")
	private String nombre;

	@DatabaseField(columnName = "CONTADOR")
	private Integer contador;

	@DatabaseField(columnName = "SUPERADO")
	private boolean superado;

	public Reto(){
		this.contador = 0;
		this.superado = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {this.id = id;}

	public String getNombre() {return nombre;}

	public void setNombre(String nombre) {this.nombre = nombre;}

	public Integer getContador() {return contador;}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public boolean getSuperado() {return superado;}

	public void setSuperado(boolean superado) {this.superado = superado;}
}