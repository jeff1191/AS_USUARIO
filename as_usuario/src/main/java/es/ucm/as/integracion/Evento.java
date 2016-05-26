/**
 * 
 */
package es.ucm.as.integracion;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

import es.ucm.as.negocio.suceso.TransferEvento;

public class Evento {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "NOMBRE")
	private String nombre;

	@DatabaseField(columnName = "FECHA", dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date fecha;

	@DatabaseField(columnName = "HORA_ALARMA" ,dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date horaAlarma;

	@DatabaseField(columnName = "HORA_EVENTO", dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date horaEvento;

	@DatabaseField(columnName = "ASISTENCIA")
	private String asistencia;

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

	public String getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}

	public Date getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(Date horaEvento) {
		this.horaEvento = horaEvento;
	}

	public TransferEvento getTransfer(){
		TransferEvento ret = new TransferEvento();
		ret.setAsistencia(getAsistencia());
		ret.setNombre(getNombre());
		ret.setFecha(getFecha());
		ret.setHoraAlarma(getHoraAlarma());
		ret.setHoraEvento(getHoraEvento());
		ret.setId(getId());
		return ret;
	}
}