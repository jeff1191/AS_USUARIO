/**
 * 
 */
package es.ucm.as.negocio.suceso;

import java.io.Serializable;
import java.util.Date;

import es.ucm.as.negocio.utils.Frecuencia;

public class TransferTarea implements Serializable{

	static final long serialVersionUID = 1L;

	private Integer id;

	private String textoPregunta;

	private String textoAlarma;

	private Date horaPregunta;

	private Date horaAlarma;

	private Date fechaIni;

	private Integer contador;

	private Integer noSeguidos;

	private Frecuencia frecuenciaTarea;

	private Integer mejorar;

	private Integer numSi;

	private Integer numNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getTextoAlarma() {
		return textoAlarma;
	}

	public void setTextoAlarma(String textoAlarma) {
		this.textoAlarma = textoAlarma;
	}

	public Date getHoraPregunta() {
		return horaPregunta;
	}

	public void setHoraPregunta(Date horaPregunta) {
		this.horaPregunta = horaPregunta;
	}

	public Date getHoraAlarma() {
		return horaAlarma;
	}

	public void setHoraAlarma(Date horaAlarma) {
		this.horaAlarma = horaAlarma;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Integer getNoSeguidos() {
		return noSeguidos;
	}

	public void setNoSeguidos(Integer noSeguidos) {
		this.noSeguidos = noSeguidos;
	}

	public Frecuencia getFrecuenciaTarea() {
		return frecuenciaTarea;
	}

	public void setFrecuenciaTarea(Frecuencia frecuenciaTarea) {
		this.frecuenciaTarea = frecuenciaTarea;
	}

	public Integer getMejorar() {
		return mejorar;
	}

	public void setMejorar(Integer mejorar) {
		this.mejorar = mejorar;
	}

	public Integer getNumSi() {
		return numSi;
	}

	public void setNumSi(Integer numSi) {
		this.numSi = numSi;
	}

	public Integer getNumNo() {
		return numNo;
	}

	public void setNumNo(Integer numNo) {
		this.numNo = numNo;
	}
}