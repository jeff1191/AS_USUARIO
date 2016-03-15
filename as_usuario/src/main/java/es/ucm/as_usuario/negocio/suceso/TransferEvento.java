package es.ucm.as_usuario.negocio.suceso;

import java.sql.Timestamp;
import java.util.Date;

import es.ucm.as_usuario.negocio.utils.Frecuencia;

/**
 * Created by Jeffer on 04/03/2016.
 */
public class TransferEvento {
    private Integer id;

    private String textoPregunta;

    private String textoAlarma;

    private Date horaPregunta;

    private Date horaAlarma;

    private Date fechaIni;

    private Integer contador;

    private Integer noSeguidos;

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
}

