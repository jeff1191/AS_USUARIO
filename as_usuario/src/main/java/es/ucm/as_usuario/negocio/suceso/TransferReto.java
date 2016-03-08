/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import java.sql.Timestamp;


public class TransferReto {

	private Integer id;

	private String nombre;

	private Integer contador;

	private boolean superado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TransferReto(){	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public boolean getSuperado() {return superado;	}

	public void setSuperado(boolean superado) {		this.superado = superado;	}
}