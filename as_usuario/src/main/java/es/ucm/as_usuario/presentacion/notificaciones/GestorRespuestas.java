package es.ucm.as_usuario.presentacion.notificaciones;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as_usuario.R;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class GestorRespuestas extends Activity {

    private ListView listaPreguntas;
    private TextView titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.alarmas);

        String tituloAlarma = getIntent().getExtras().getString("titulo");
        String texto = getIntent().getExtras().getString("texto");
        Integer idNot = getIntent().getExtras().getInt("idNotificacion");

        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

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

    }

    public void cerrar(View v){
        finish();
    }
}
