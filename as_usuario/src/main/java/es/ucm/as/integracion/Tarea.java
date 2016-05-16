/**
 * 
 */
package es.ucm.as.integracion;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import es.ucm.as.negocio.utils.Frecuencia;

public class Tarea {

	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TEXTO_PREGUNTA")
	private String textoPregunta;

	@DatabaseField(columnName = "TEXTO_ALARMA")
	private String textoAlarma;

	@DatabaseField(columnName = "HORA_PREGUNTA", dataType = DataType.DATE_LONG, format = "dd/mm/yyyy HH:mm")
	private Date horaPregunta;

	@DatabaseField(columnName = "HORA_ALARMA", dataType = DataType.DATE_LONG, format = "dd/MM/yyyy HH:mm")
	private Date horaAlarma;

	@DatabaseField(columnName = "CONTADOR")
	private Integer contador;

	@DatabaseField(columnName = "NO_SEGUIDOS")
	private Integer noSeguidos;

	@DatabaseField(columnName = "FRECUENCIA_TAREA", dataType = DataType.ENUM_STRING)
	private Frecuencia frecuenciaTarea;

	@DatabaseField(columnName = "MEJORAR")
	private Integer mejorar;

	@DatabaseField(columnName = "NUM_SI")
	private Integer numSi;

	@DatabaseField(columnName = "NUM_NO")
	private Integer numNo;

	public Tarea(){
		this.contador = 0;
		this.noSeguidos = 0;
		this.numNo = 0;
		this.numSi = 0;
		this.frecuenciaTarea = Frecuencia.DIARIA;
		this.mejorar = 30;
	}

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