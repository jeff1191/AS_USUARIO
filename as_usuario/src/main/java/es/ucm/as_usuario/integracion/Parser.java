package es.ucm.as_usuario.integracion;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.Contexto;

/**
 * Created by msalitu on 14/03/2016.
 */
public class Parser {

    private ArrayList<Tarea> tareas;

    public Parser(){
        tareas = new ArrayList<Tarea>();
    }

    public ArrayList<Tarea> getTareas(){
        return this.tareas;
    }

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void read() {
        String alarma = "";
        String pregunta = "";
        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.perfil_a);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            while(alarma != null){
                alarma = brin.readLine();
                pregunta = brin.readLine();
                tareas.add(toTarea(alarma, pregunta));
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
}
