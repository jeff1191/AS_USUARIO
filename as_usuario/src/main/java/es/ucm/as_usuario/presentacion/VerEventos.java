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

/**
 * Clase para que se vean los eventos temporales
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerEventos  extends Activity{
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventos);
        list = (ListView)findViewById(R.id.listViewEventos);
        TextView userName = (TextView)findViewById(R.id.tituloEventos);
        Bundle bundle = getIntent().getExtras();

       /*if(bundle.getString("hola")!= null)
        {
            //TODO here get the string stored in the string variable and do
            userName.setText(bundle.getString("hola"));
        }*/
        if(bundle.getStringArrayList("listaEventos") != null){
            ArrayList<String> listaE = bundle.getStringArrayList("listaEventos");
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaE);
            list.setAdapter(adaptador);
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }
}
