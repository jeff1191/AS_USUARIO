package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import es.ucm.as_usuario.R;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Clase para que se muestre el reto
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerReto extends Activity {

    private ProgressBar progreso;
    private Integer cont;
    private Integer nuevo;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto);

        //Bundle bundle = getIntent().getExtras();
        //nombre = bundle.getString("textReto");
        //cont = bundle.getInt("contadorReto");

        tv = (TextView) findViewById(R.id.tv);
        //Bundle bundle = getIntent().getExtras();
        // tv.setText(cont.toString());

        progreso = (ProgressBar) findViewById(R.id.progressBar);
        //progreso.setProgress(cont);

    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }

    public void responderRetoNO(View v){

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_RETO, -1);

        // si el progreso es 0 no se hace nada
        if (progreso.getProgress() > 0){
            progreso.incrementProgressBy(-1);
            cont = progreso.getProgress();
        }

        final TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(cont.toString() + "/30");
    }


    public void responderRetoSI(View v){

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_RETO, 1);

        nuevo = progreso.getProgress()+1;
        if (nuevo <= 30) {
            progreso.setProgress(0);
            cont = 0;
            new Thread(myThread).start();

            final TextView tv = (TextView) findViewById(R.id.tv);
            tv.setText(nuevo.toString() + "/30");
        }
        if (nuevo == 30){
            Toast toast = Toast.makeText(Contexto.getInstancia().getContext(),
                    "¡Ehnorabuena! Has superado tu reto", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // clase privada que crea una hebra secundaria para el efecto de avance de la barra de progreso
    private Runnable myThread = new Runnable(){

        public void run() {

            while (cont < nuevo){
                try{
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(50);
                } catch(Throwable t){}
            }
        }

        Handler myHandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                cont++;
                progreso.setProgress(cont);
            }
        };

    };


}
