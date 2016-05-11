package es.ucm.as.negocio.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.Tarea;
import es.ucm.as.presentacion.vista.Contexto;

/**
 * Created by msalitu on 14/03/2016.
 */
public class Parser {

    private ArrayList<Tarea> tareas;
    private ArrayList<Tarea> tareasObsoletas;

    public Parser(){
        tareas = new ArrayList<>();
        tareasObsoletas = new ArrayList<>();
    }

    public ArrayList<Tarea> getTareas(){
        return this.tareas;
    }

    public ArrayList<Tarea> getTareasObsoletas() {return this.tareasObsoletas;}

    /* Lee de fichero y transforma en tareas que almacena en el ArrayList atributo de esta clase*/
    public void readTareas() {
        String alarma = "";
        String pregunta;
        String habilitada;
        String horaPregunta;
        String horaAlarma;
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
            ex.printStackTrace();
        }
    }


    /*A partir de dos Strings crea una pregunta con esos textos de alarma y pregunta*/
    public Tarea toTarea(String textoAlarma, String textoPregunta, String horaAlarma, String horaPregunta){
        Tarea ret = new Tarea();
        SimpleDateFormat horasMinutos = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat diaMes = new SimpleDateFormat("dd/MM/yyyy");
        ret.setTextoAlarma(textoAlarma);
        ret.setTextoPregunta(textoPregunta);
        ret.setFrecuenciaTarea(Frecuencia.DIARIA);
        try {
            Date e = Calendar.getInstance().getTime();
            String concat = diaMes.format(e).toString();
            String loquesea= concat+" "+horaAlarma;
            ret.setHoraAlarma(horasMinutos.parse(loquesea));
            loquesea= concat+" "+horaPregunta;
            ret.setHoraPregunta(horasMinutos.parse(loquesea));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
