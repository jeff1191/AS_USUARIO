package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Clase para que se vean los eventos temporales
 *
 * Created by msalitu on 09/03/2015.
 */
public class VerInforme extends Activity{

    ListViewAdapter adapter;

    String[] titulo = new String[]{
            "Dar los buenos días a mamá",
            "Ducharse",
            "titulo3",
            "titulo4",
            "haz un dibujo",
            "cosquillas a Jeff",
    };

    Integer[] si = new Integer[]{
            1,
            2,
            3,
            4,
            1,
            12,
    };
    Integer[] no = new Integer[]{
            1,
            2,
            3,
            10,
            9,
            2,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informe);
        final ListView lista = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(this, titulo, si, no);
        lista.setAdapter(adapter);

        //list = (ListView)findViewById(R.id.listView);
        TextView titulo = (TextView)findViewById(R.id.tituloInforme);
        titulo.setText("¿CÓMO VAS?");
        Bundle bundle = getIntent().getExtras();

       /*if(!bundle.getStringArrayList("listaTareas").isEmpty()){
            ArrayList<String> listaE = bundle.getStringArrayList("listaTareas");
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaE);
            //list.setAdapter(adaptador);
        }*/
    }


    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "informe");
    }
}
