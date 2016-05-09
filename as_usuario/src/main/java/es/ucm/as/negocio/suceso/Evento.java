/**
 * 
 */
package es.ucm.as.negocio.suceso;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Evento {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TEXTO")
	private String texto;

	@DatabaseField(columnName = "FECHA", dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date fecha;

	@DatabaseField(columnName = "HORA_ALARMA" ,dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date horaAlarma;

	@DatabaseField(columnName = "ASISTENCIA")
	private boolean asistencia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getHoraAlarma() {
		return horaAlarma;
	}

	public void setHoraAlarma(Date horaAlarma) {
		this.horaAlarma = horaAlarma;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(boolean asistencia) {
		this.asistencia = asistencia;
	}

}