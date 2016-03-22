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

    public TransferEvento(){    }

    public TransferEvento(Evento evento){
        this.id = evento.getId();
        this.textoAlarma = evento.getTextoAlarma();
        this.textoFecha = evento.getTextoFecha();
        this.horaAlarma = evento.getHoraAlarma();
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
}

