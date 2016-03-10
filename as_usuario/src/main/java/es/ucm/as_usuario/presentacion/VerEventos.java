package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import es.ucm.as_usuario.R;

/**
 * Clase para que se vean los eventos temporales
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerEventos  extends Activity{
    private ListView listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventos);
        listaEventos = (ListView)findViewById(R.id.listViewEventos);
        Bundle bundle = getIntent().getExtras();

       /*if(bundle.getString("hola")!= null)
        {
            //TODO here get the string stored in the string variable and do
            userName.setText(bundle.getString("hola"));
        }*/
        if(bundle.getStringArrayList("listaEventos") != null){
            ArrayList<String> listaE = bundle.getStringArrayList("listaEventos");
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaE);
            listaEventos.setAdapter(adaptador);
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
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
