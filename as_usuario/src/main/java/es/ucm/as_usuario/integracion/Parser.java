package es.ucm.as_usuario.integracion;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.Contexto;

/**
 * Created by msalitu on 14/03/2016.
 */
public class Parser {

    private ArrayList<Tarea> tareas;
    private ArrayList<Evento> eventos;

    public Parser(){
        tareas = new ArrayList<Tarea>();
        eventos = new ArrayList<Evento>();
    }

    public ArrayList<Tarea> getTareas(){
        return this.tareas;
    }

    public ArrayList<Evento> getEventos(){
        return this.eventos;
    }

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readTareas() {
        String alarma = "";
        String pregunta = "";
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.perfil_a);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                pregunta = brin.readLine();
                if (alarma != null && pregunta != null)
                    tareas.add(toTarea(alarma, pregunta));
            }
            fraw.close();

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
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
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
        return texto;
    }

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readEventos() {
        String alarma = "";
        String fecha = "";
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.eventos);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                fecha = brin.readLine();
                if (alarma != null && fecha != null)
                    eventos.add(toEvento(alarma, fecha));
            }
            fraw.close();

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
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
