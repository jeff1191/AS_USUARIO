package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
 * Created by Juan Lu on 25/02/2016.
 */
public class VerInforme extends Activity{
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventos);
        list = (ListView)findViewById(R.id.listViewTareas);
        TextView userName = (TextView)findViewById(R.id.tituloInforme);
        Bundle bundle = getIntent().getExtras();

       /* if(bundle.getStringArrayList("listaTareas") != null){
            ArrayList<String> listaE = bundle.getStringArrayList("listaTareas");
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaE);
            list.setAdapter(adaptador);
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
