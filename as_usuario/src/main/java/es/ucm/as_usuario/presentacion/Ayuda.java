package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import es.ucm.as_usuario.R;

/**
 * Created by msalitu on 08/03/2016.
 */
public class Ayuda extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda);
        Bundle bundle = getIntent().getExtras();
        String pantalla = bundle.getString("pantalla");
        ImageView iv = (ImageView)findViewById(R.id.imageView);

        switch (pantalla){
            case "principal":
                iv.setImageResource(R.drawable.ayuda_main);
                break;
            case "reto":
                iv.setImageResource(R.drawable.ayuda_main);
                break;
            case "configuracion":
                iv.setImageResource(R.drawable.ayuda_main);
                break;
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }
}
