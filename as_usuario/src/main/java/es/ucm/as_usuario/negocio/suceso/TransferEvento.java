package es.ucm.as_usuario.negocio.suceso;

import java.sql.Timestamp;

import es.ucm.as_usuario.negocio.utils.Frecuencia;

/**
 * Created by Jeffer on 04/03/2016.
 */
public class TransferEvento {
    private Integer id;

    private String textoPregunta;

    private String textoAlarma;

    private Timestamp horaPregunta;

    private Timestamp horaAlarma;

    private Timestamp fechaIni;

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

