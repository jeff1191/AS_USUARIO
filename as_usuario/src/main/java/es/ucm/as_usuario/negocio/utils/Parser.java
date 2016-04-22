package es.ucm.as_usuario.negocio.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.presentacion.vista.Contexto;

/**
 * Created by msalitu on 14/03/2016.
 */
public class Parser {

    private ArrayList<Tarea> tareas;
    private ArrayList<Tarea> tareasObsoletas;
    private ArrayList<Evento> eventos;
    private ArrayList<Evento> eventosObsoletos;
    private Reto reto;

    public Parser(){
        tareas = new ArrayList<Tarea>();
        eventos = new ArrayList<Evento>();
        tareasObsoletas = new ArrayList<Tarea>();
        eventosObsoletos = new ArrayList<Evento>();
        reto = new Reto();
    }

    public ArrayList<Tarea> getTareas(){
        return this.tareas;
    }

    public ArrayList<Evento> getEventos(){
        return this.eventos;
    }

    public ArrayList<Tarea> getTareasObsoletas() {return this.tareasObsoletas;}

    public ArrayList<Evento> getEventosObsoletos(){
        return this.eventosObsoletos;
    }

    public Reto getReto() {
        return reto;
    }

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readTareas() {
        String alarma = "";
        String pregunta = "";
        String habilitada = "";
        String horaPregunta = "";
        String horaAlarma = "";
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.perfil_a);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                horaAlarma = brin.readLine();
                pregunta = brin.readLine();
                horaPregunta = brin.readLine();
                habilitada = brin.readLine();
                if (alarma != null && horaAlarma != null && pregunta != null && horaPregunta != null) {
                    if (habilitada.equals("true"))
                        tareas.add(toTarea(alarma, pregunta, horaAlarma, horaPregunta));
                    else
                        tareasObsoletas.add(toTarea(alarma, pregunta, horaAlarma, horaPregunta));
                }
            }
            fraw.close();

        } catch (Exception ex) {

        }
    }

    public void readReto() {
        String texto = null;
        String premio = null;
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.reto);
            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));
            texto = brin.readLine();
            if(texto != null){
                reto.setTexto(texto);
                premio = brin.readLine();
                if(premio != null){
                    reto.setPremio(premio);
                }
            }
            fraw.close();
        } catch (Exception ex) {

        }
    }

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readEventos() {
        String alarma = "";
        String fecha = "";
        String habilitado = "";
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.eventos);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                fecha = brin.readLine();
                habilitado = brin.readLine();

                if (alarma != null && fecha != null) {
                    if (habilitado.equals("true"))
                        eventos.add(toEvento(alarma, fecha));
                    else
                        eventosObsoletos.add(toEvento(alarma, fecha));
                }
            }
            fraw.close();

        } catch (Exception ex) {

        }
    }

    /*A partir de dos Strings crea una pregunta con esos textos de alarma y pregunta*/
    public Tarea toTarea(String textoAlarma, String textoPregunta, String horaAlarma, String horaPregunta){
        Tarea ret = new Tarea();
        SimpleDateFormat horasMinutos = new SimpleDateFormat("HH:mm");
        ret.setTextoAlarma(textoAlarma);
        ret.setTextoPregunta(textoPregunta);
        try {
            ret.setHoraAlarma(horasMinutos.parse(horaAlarma));
            ret.setHoraPregunta(horasMinutos.parse(horaPregunta));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Evento toEvento(String alarma, String fecha){
        Evento ret = new Evento();
        ret.setTextoAlarma(alarma);
        ret.setTextoFecha(fecha);
        // falta convertir a Date la fecha y almacenarla tambien
        return ret;
    }
}
