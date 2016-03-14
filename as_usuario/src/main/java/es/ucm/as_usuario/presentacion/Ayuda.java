package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Created by msalitu on 08/03/2016.
 */
public class Ayuda extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
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
            case "informe":
                iv.setImageResource(R.drawable.ayuda_main);
                break;
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);

        //Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, null);
        startActivity(pantallaPrincipal);
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
