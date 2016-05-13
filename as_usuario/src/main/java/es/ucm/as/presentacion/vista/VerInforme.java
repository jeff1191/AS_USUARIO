package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import es.ucm.as.R;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

/**
 * Clase para que se vean los activity_eventos temporales
 *
 * Created by msalitu on 09/03/2015.
 */
public class VerInforme extends Activity{

    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe);

        TextView titulo = (TextView)findViewById(R.id.tituloInforme);

        Bundle bundle = getIntent().getExtras();

        titulo.setText("¿Cómo vas?");

        ImageView img = (ImageView)findViewById(R.id.flecha);

        Integer puntAntes = bundle.getInt("puntuacionAnterior");
        Integer puntAhora = bundle.getInt("puntuacion");

        if (puntAhora >= puntAntes)
            img.setImageResource(R.drawable.flechaverde);
        else
            img.setImageResource(R.drawable.flecharoja);

        TextView ahora = (TextView)findViewById(R.id.puntuacionAhora);
        ahora.setText(puntAhora.toString());
        TextView antes = (TextView)findViewById(R.id.puntuacionAnterior );
        antes.setText(puntAntes.toString());

        final ListView lista = (ListView) findViewById(R.id.listView);

        ArrayList<String> t = getIntent().getStringArrayListExtra("titulos");
        ArrayList<Integer> s = getIntent().getIntegerArrayListExtra("si");
        ArrayList<Integer> n = getIntent().getIntegerArrayListExtra("no");

        Integer st = s.size();
        if(t.size()!= 0){
            adapter = new ListViewAdapter(t, s, n, this.getApplicationContext());
            lista.setAdapter(adapter);
        }else{
            Button tarea = (Button) findViewById(R.id.tarea);
            Button si = (Button) findViewById(R.id.si);
            Button no = (Button) findViewById(R.id.no);
            Button total = (Button) findViewById(R.id.total);

            tarea.setVisibility(View.INVISIBLE);
            si.setVisibility(View.INVISIBLE);
            no.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);

            TextView noHay = (TextView) findViewById(R.id.noHay);
            noHay.setText("No tienes ninguna tarea");
            noHay.setTextColor(Color.GRAY);
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "activity_informe");
    }

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
