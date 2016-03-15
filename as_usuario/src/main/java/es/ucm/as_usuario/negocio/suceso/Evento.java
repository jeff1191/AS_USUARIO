/**
 * 
 */
package es.ucm.as_usuario.negocio.suceso;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.sql.Timestamp;
import java.util.Date;

import es.ucm.as_usuario.negocio.utils.Frecuencia;

public class Evento {
	@DatabaseField(generatedId = true, columnName = "ID")
	private Integer id;

	@DatabaseField(columnName = "TEXTO_ALARMA")
	private String textoAlarma;

	@DatabaseField(columnName = "TEXTO_FECHA")
	private String textoFecha;

	@DatabaseField(columnName = "HORA_ALARMA" ,dataType = DataType.DATE_STRING, format = "dd-MM-yyyy HH:mm:ss")
	private Date horaAlarma;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTextoAlarma() {
		return textoAlarma;
	}

	public void setTextoAlarma(String textoAlarma) {
		this.textoAlarma = textoAlarma;
	}

	public Date getHoraAlarma() {
		return horaAlarma;
	}

	public void setHoraAlarma(Date horaAlarma) {
		this.horaAlarma = horaAlarma;
	}

	public String getTextoFecha() {
		return textoFecha;
	}

	public void setTextoFecha(String textoFecha) {
		this.textoFecha = textoFecha;
	}


}