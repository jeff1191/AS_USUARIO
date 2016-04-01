package es.ucm.as_usuario.negocio.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.presentacion.Contexto;

/**
 * Created by msalitu on 14/03/2016.
 */
public class Parser {

    private ArrayList<Tarea> tareas;
    private ArrayList<Tarea> tareasObsoletas;
    private ArrayList<Evento> eventos;
    private ArrayList<Evento> eventosObsoletos;

    public Parser(){
        tareas = new ArrayList<Tarea>();
        eventos = new ArrayList<Evento>();
        tareasObsoletas = new ArrayList<Tarea>();
        eventosObsoletos = new ArrayList<Evento>();
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


    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readTareas() {
        String alarma = "";
        String pregunta = "";
        String habilitada = "";
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.perfil_a);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                pregunta = brin.readLine();
                habilitada = brin.readLine();
                if (alarma != null && pregunta != null) {
                    if (habilitada.equals("true"))
                        tareas.add(toTarea(alarma, pregunta));
                    else
                        tareasObsoletas.add(toTarea(alarma, pregunta));
                }
            }
            fraw.close();

        } catch (Exception ex) {

        }
    }

    public String readReto() {
        String texto = null;
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.reto);
            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));
            texto = brin.readLine();
            fraw.close();
        } catch (Exception ex) {

        }
        return texto;
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
    public Tarea toTarea(String textoAlarma, String textoPregunta){
        Tarea ret = new Tarea();
        ret.setTextoAlarma(textoAlarma);
        ret.setTextoPregunta(textoPregunta);
        return ret;
    }

    public Reto toReto(String texto){
        if (texto != null){
            Reto ret = new Reto();
            ret.setNombre(texto);
            return ret;
        } else
            return null;
    }

    public Evento toEvento(String alarma, String fecha){
        Evento ret = new Evento();
        ret.setTextoAlarma(alarma);
        ret.setTextoFecha(fecha);
        // falta convertir a Date la fecha y almacenarla tambien
        return ret;
    }
}
