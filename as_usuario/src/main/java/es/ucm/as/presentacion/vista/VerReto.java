package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.as.R;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

/**
 * Clase para que se muestre el activity_reto
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerReto extends Activity {

    private Integer contInt;        // contador del activity_reto
    private Integer nuevo;          // necesario para el efecto de avance de la barra de progreso

    private TextView c;             // contador reto
    private TextView tv;            // texto reto
    private TextView premio;        // premio del reto
    private TextView sup;           // titulo
    private ProgressBar progreso;   // barra progreso
    private Button si;              // boton Si
    private Button no;              // boton No

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);

        TransferReto reto = (TransferReto) getIntent().getExtras().getSerializable("reto");

        sup = (TextView) findViewById(R.id.tituloReto);
        tv = (TextView) findViewById(R.id.textoReto);
        premio = (TextView) findViewById(R.id.premioReto);
        c = (TextView) findViewById(R.id.cont);
        progreso = (ProgressBar) findViewById(R.id.progressBar);
        si = (Button) findViewById(R.id.si);
        no = (Button) findViewById(R.id.no);

        if(reto != null){
            if (reto.getTexto() != null) {
                tv.setText(reto.getTexto());
                c.setText(reto.getContador().toString() + "/30");
                progreso.setProgress(reto.getContador());

                if (!reto.getPremio().equals(""))
                    premio.setText("Premio: " + reto.getPremio());

                boolean superado = reto.getSuperado();

                if (superado) {
                    si.setEnabled(false);
                    si.setVisibility(View.INVISIBLE);
                    no.setEnabled(false);
                    no.setVisibility(View.INVISIBLE);
                    sup.setText("RETO SUPERADO");
                }
            }
        } else {
            TextView noHay = (TextView) findViewById(R.id.noHay);
            noHay.setText("No tienes ningún reto");
            noHay.setTextColor(Color.GRAY);
            si.setEnabled(false);
            si.setVisibility(View.INVISIBLE);
            no.setEnabled(false);
            no.setVisibility(View.INVISIBLE);
            progreso = (ProgressBar) findViewById(R.id.progressBar);
            progreso.setVisibility(View.INVISIBLE);
        }
    }


    public void volver(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, null);
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "activity_reto");
    }

    public void responderRetoNO(View v){

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_RETO, -1);

        // si el progreso es 0 no se hace nada
        if (progreso.getProgress() > 0){
            progreso.incrementProgressBy(-1);
            contInt = progreso.getProgress();
        }

        c.setText(contInt.toString() + "/30");
    }


    public void responderRetoSI(View v){

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_RETO, 1);

        nuevo = progreso.getProgress()+1;
        if (nuevo <= 30) {
            progreso.setProgress(0);
            contInt = 0;
            new Thread(myThread).start();

            c.setText(nuevo.toString() + "/30");
        }
        if (nuevo == 30){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "¡Enhorabuena! Has superado tu activity_reto", Toast.LENGTH_LONG);
            toast.show();

            si.setEnabled(false);
            si.setVisibility(View.INVISIBLE);
            no.setEnabled(false);
            no.setVisibility(View.INVISIBLE);
            sup.setText("RETO SUPERADO");
        }
    }

    // clase privada que crea una hebra secundaria para el efecto de avance de la barra de progreso
    private Runnable myThread = new Runnable(){

        public void run() {
            while (contInt < nuevo){
                try{
                    myHandle.sendMessage(myHandle.obtainMessage());
                    Thread.sleep(50);
                } catch(Throwable t){}
            }
        }

        Handler myHandle = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                contInt++;
                progreso.setProgress(contInt);
            }
        };
    };

    public void cargarTema(){
        switch (Configuracion.temaActual){
            case "AS_theme_azul":
                setTheme(R.style.AS_tema_azul);
                break;
            case "AS_theme_rojo":
                setTheme(R.style.AS_tema_rojo);
                break;
            case "AS_theme_rosa":
                setTheme(R.style.AS_tema_rosa);
                break;
            case "AS_theme_verde":
                setTheme(R.style.AS_tema_verde);
                break;
            case "AS_theme_negro":
                setTheme(R.style.AS_tema_negro);
                break;
        }
    }
}
