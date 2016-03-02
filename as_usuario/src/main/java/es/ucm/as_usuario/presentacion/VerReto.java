package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.ucm.as_usuario.R;

/**
 * Clase para que se muestre el reto
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerReto extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reto);
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }
}
