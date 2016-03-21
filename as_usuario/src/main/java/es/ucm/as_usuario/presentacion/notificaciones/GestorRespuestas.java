package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.presentacion.Contexto;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class GestorRespuestas extends Activity {

    private ListView listaPreguntas;
    private TextView titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmas);

        String tituloAlarma = getIntent().getExtras().getString("titulo");
        String texto = getIntent().getExtras().getString("texto");
        Integer respuesta = getIntent().getExtras().getInt("respuesta");
        Integer idNot = getIntent().getExtras().getInt("idNotificacion");
        Integer idTarea = getIntent().getExtras().getInt("idTarea");

        NotificationManager notificationManager =
                (NotificationManager) Contexto.getInstancia().getContext().getApplicationContext()
                        .getSystemService(Contexto.getInstancia().getContext().
                                getApplicationContext().NOTIFICATION_SERVICE);

        Log.e("prueba", "Va a cerrar la notifiacion ..." + idNot);

        notificationManager.cancel(idNot);


        listaPreguntas = (ListView)findViewById(R.id.listViewAlarma);
        titulo = (TextView)findViewById(R.id.tituloAlarma);

        String[] aux = texto.split("/");
        //List<Evento> eventosModelo= (List<Evento>) datos;
        ArrayList<String> preguntas= new ArrayList<String>();
        for(int i=0; i < aux.length; i++){
            preguntas.add(aux[i]);
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);

        titulo.setText(tituloAlarma);
        listaPreguntas.setAdapter(adaptador);

        //String respuestaTarea = respuesta + "_" + idTarea;

        //Log.e("prueba", "Lo que se le pasa al programa es ..." + respuestaTarea);

       // Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_TAREA, respuestaTarea);

        //Toast de feedback (Poner esto igual al final de comando??)
       // String texto;
        //if(respuesta == 1) texto = "Has contestado SI a la tarea";
        //else texto = "Has contestado NO a la tarea";

        //Toast toastFeedbackRespuesta = Toast.makeText(Contexto.getInstancia().getContext(),texto, Toast.LENGTH_LONG);
        //toastFeedbackRespuesta.show();
    }

    public void cerrar(View v){
        finish();
    }
}
