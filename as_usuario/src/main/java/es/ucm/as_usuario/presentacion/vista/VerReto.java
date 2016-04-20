package es.ucm.as_usuario.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Clase para que se muestre el activity_reto
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerReto extends Activity {

    private ProgressBar progreso;   // barra progreso
    private Integer contInt;        // contador del activity_reto
    private Integer nuevo;          // necesario para el efecto de avance de la barra de progreso
    private TextView c;             // muestra el contador
    private TextView tv;            // muestra el texto del activity_reto
    private ImageView imagenPremio; // muestra la foto del premio por completar el reto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reto);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            String nombre = bundle.getString("textReto");

            tv = (TextView) findViewById(R.id.textoReto);
            tv.setText(nombre);

            imagenPremio = (ImageView) findViewById(R.id.premio);
            if(!bundle.getString("premioReto").equals(""))
                imagenPremio.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("premioReto")));
            else
                imagenPremio.setImageResource(R.drawable.jirafa);
                //imagenPremio.setImageResource(R.drawable.sonrisa_premio_defecto);

            contInt = bundle.getInt("contadorReto");
            c = (TextView) findViewById(R.id.cont);
            c.setText(contInt.toString()+"/30");

            progreso = (ProgressBar) findViewById(R.id.progressBar);
            progreso.setProgress(contInt);

            boolean superado = bundle.getBoolean("superadoReto");

            if (superado) {
                Button si = (Button) findViewById(R.id.si);
                si.setEnabled(false);
                si.setVisibility(View.INVISIBLE);
                Button no = (Button) findViewById(R.id.no);
                no.setEnabled(false);
                no.setVisibility(View.INVISIBLE);
                TextView sup = (TextView) findViewById(R.id.tituloReto);
                sup.setText("RETO SUPERADO");
            }
        }else {
            Button si = (Button) findViewById(R.id.si);
            si.setEnabled(false);
            si.setVisibility(View.INVISIBLE);
            Button no = (Button) findViewById(R.id.no);
            no.setEnabled(false);
            no.setVisibility(View.INVISIBLE);
            progreso = (ProgressBar) findViewById(R.id.progressBar);
            progreso.setVisibility(View.INVISIBLE);
            TextView sup = (TextView) findViewById(R.id.tituloReto);
            sup.setText("No tienes ningún activity_reto");
            sup.setTextColor(Color.GRAY);
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
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

        final TextView tv = (TextView) findViewById(R.id.cont);
        tv.setText(contInt.toString() + "/30");
    }


    public void responderRetoSI(View v){

        Controlador.getInstancia().ejecutaComando(ListaComandos.RESPONDER_RETO, 1);

        nuevo = progreso.getProgress()+1;
        if (nuevo <= 30) {
            progreso.setProgress(0);
            contInt = 0;
            new Thread(myThread).start();

            final TextView tv = (TextView) findViewById(R.id.cont);
            tv.setText(nuevo.toString() + "/30");
        }
        if (nuevo == 30){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "¡Enhorabuena! Has superado tu activity_reto", Toast.LENGTH_LONG);
            toast.show();
            Button si = (Button) findViewById(R.id.si);
            si.setEnabled(false);
            si.setVisibility(View.INVISIBLE);
            Button no = (Button) findViewById(R.id.no);
            no.setEnabled(false);
            no.setVisibility(View.INVISIBLE);
            TextView sup = (TextView) findViewById(R.id.tituloReto);
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
