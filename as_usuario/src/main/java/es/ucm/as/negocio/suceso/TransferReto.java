/**
 * 
 */
package es.ucm.as.negocio.suceso;

import java.io.Serializable;

public class TransferReto implements Serializable {

	static final long serialVersionUID = 1L;

	private Integer id;

	private String texto;

	private String premio;

	private Integer contador;

	private boolean superado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TransferReto(){	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public boolean getSuperado() {return superado;	}

	public void setSuperado(boolean superado) {		this.superado = superado;	}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}
}