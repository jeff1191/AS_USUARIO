/**
 * 
 */
package es.ucm.as.negocio.suceso;

import com.j256.ormlite.field.DatabaseField;

public class Reto {

	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TEXTO")
	private String texto;

	@DatabaseField(columnName = "PREMIO")
	private String premio;

	@DatabaseField(columnName = "CONTADOR")
	private Integer contador;

	@DatabaseField(columnName = "SUPERADO")
	private boolean superado;

	public Reto(){
		this.contador = 0;
		this.superado = false;
		this.premio = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {this.id = id;}

	public String getTexto() {return texto;}

	public void setTexto(String texto) {this.texto = texto;}

	public Integer getContador() {return contador;}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public boolean getSuperado() {return superado;}

	public void setSuperado(boolean superado) {this.superado = superado;}

	public String getPremio() {
		return premio;
	}

	public void setPremio(String premio) {
		this.premio = premio;
	}


}