package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Clase para que se vean los eventos temporales
 *
 * Created by msalitu on 09/03/2015.
 */
public class VerInforme extends Activity{

    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informe);

        TextView titulo = (TextView)findViewById(R.id.tituloInforme);
        titulo.setText("¿CÓMO VAS?");

        // Esto es lo que hay que descomentar para que funcione pero crea un usuario cada vez que lo lanzas
        TransferUsuario transferUsuario = new TransferUsuario();
        transferUsuario.setPuntuacion(5);
        transferUsuario.setPuntuacionAnterior(8);
        transferUsuario.setNombre("Jeff");
        Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, transferUsuario);
        ///*/


        TextView ahora = (TextView)findViewById(R.id.puntuacionAhora);
        ahora.setText(Usuario.getInstancia().getPuntuacion().toString());
        TextView antes = (TextView)findViewById(R.id.puntuacionAnterior );
        antes.setText(Usuario.getInstancia().getPuntuacionAnterior().toString());

        ImageView img = (ImageView)findViewById(R.id.imageView2);

        Integer puntAntes = Usuario.getInstancia().getPuntuacionAnterior();
        Integer puntAhora = Usuario.getInstancia().getPuntuacion();
        if (puntAhora > puntAntes)
            img.setImageResource(R.drawable.flechaverde);
        else
            img.setImageResource(R.drawable.flecharoja);

        final ListView lista = (ListView) findViewById(R.id.listView);

        ArrayList<String> t = getIntent().getStringArrayListExtra("titulos");
        ArrayList<Integer> s = getIntent().getIntegerArrayListExtra("si");
        ArrayList<Integer> n = getIntent().getIntegerArrayListExtra("no");

        Integer st = s.size();
        if(t.size()!= 0){
            adapter = new ListViewAdapter(t, s, n);
            lista.setAdapter(adapter);
        }
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "informe");
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
