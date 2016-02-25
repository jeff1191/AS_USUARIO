package es.ucm.as_usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Clase para que se vean los eventos temporales
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerEventos  extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventos);
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }
}
