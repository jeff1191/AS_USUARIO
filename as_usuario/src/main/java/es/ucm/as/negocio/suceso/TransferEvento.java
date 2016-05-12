package es.ucm.as.negocio.suceso;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeffer on 04/03/2016.
 */
public class TransferEvento implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer id;
    private String texto;
    private Date fecha;
    private Date horaAlarma;
    private String asistencia;

    public TransferEvento(){    }

    public TransferEvento(Integer id, String texto, Date fecha, Date horaAlarma, String asistencia){
        this.id = id;
        this.texto = texto;
        this.fecha = fecha;
        this.horaAlarma = horaAlarma;
        this.asistencia= asistencia;
    }

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

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    public String getAsistencia() {
        return asistencia;
    }
}

