/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.sql.Timestamp;

import es.ucm.as_usuario.negocio.utils.Frecuencia;

public class Evento {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TEXTO_PREGUNTA")
	private String textoPregunta;

	@DatabaseField(columnName = "TEXTO_ALARMA")
	private String textoAlarma;

	@DatabaseField(columnName = "HORA_PREGUNTA")
	private Timestamp horaPregunta;

	@DatabaseField(columnName = "HORA_ALARMA")
	private Timestamp horaAlarma;

	@DatabaseField(columnName = "FECHA_INI")
	private Timestamp fechaIni;

	@DatabaseField(columnName = "CONTADOR")
	private Integer contador;

	@DatabaseField(columnName = "NO_SEGUIDOS")
	private Integer noSeguidos;

	@DatabaseField(columnName = "FREC_TAREA", dataType = DataType.ENUM_STRING)
	private Frecuencia frecuenciaTarea;

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

	public Timestamp getHoraPregunta() {
		return horaPregunta;
	}

	public void setHoraPregunta(Timestamp horaPregunta) {
		this.horaPregunta = horaPregunta;
	}

	public Timestamp getHoraAlarma() {
		return horaAlarma;
	}

	public void setHoraAlarma(Timestamp horaAlarma) {
		this.horaAlarma = horaAlarma;
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

	public void setContador(Integer contador) {
		this.contador = contador;
	}
}