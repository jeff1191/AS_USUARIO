package es.ucm.as_usuario.negocio.suceso;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.sql.Timestamp;
import java.util.Date;

import es.ucm.as_usuario.negocio.utils.Frecuencia;

/**
 * Created by Jeffer on 04/03/2016.
 */
public class TransferEvento {

    private Integer id;
    private String textoAlarma;
    private String textoFecha;
    private Date horaAlarma;
    private Integer asistencia;

    public TransferEvento(){    }

    public TransferEvento(Integer id, String textoAlarma, String textoFecha, Date horaAlarma,Integer asistencia){
        this.id = id;
        this.textoAlarma = textoAlarma;
        this.textoFecha = textoFecha;
        this.horaAlarma = horaAlarma;
        this.asistencia= asistencia;
    }

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
    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia;
    }

    public Integer getAsistencia() {
        return asistencia;
    }
}

