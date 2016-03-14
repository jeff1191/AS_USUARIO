package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.notificaciones.ServicioNotificaciones;


public class MainActivity extends Activity {

    private TextView nombrePrincipal;
    private TextView puntuacion;
    private ImageView imagenPerfil;

    private int request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Contexto.getInstancia().setContext(this);
        /*TransferUsuario crearUsuario = new TransferUsuario();
        crearUsuario.setNombre("Jiji");
        crearUsuario.setAvatar("");
        crearUsuario.setPuntuacion(6);
        crearUsuario.setPuntuacionAnterior(4);
        crearUsuario.setFrecuenciaRecibirInforme(Frecuencia.DIARIA);
        Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, crearUsuario);*/


        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*nombrePrincipal=(TextView)findViewById(R.id.nombreUser);
        nombrePrincipal.setText(Usuario.getInstancia().getNombre());
        puntuacion = (TextView)findViewById(R.id.puntuacionUsuario);
        imagenPerfil= (ImageView) findViewById(R.id.avatar);
        if(Usuario.getInstancia().getAvatar() != null && !Usuario.getInstancia().getAvatar().equals(""))
            imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(Usuario.getInstancia().getAvatar()));
        else
            imagenPerfil.setImageResource(R.drawable.avatar);
        puntuacion.setText(Usuario.getInstancia().getPuntuacion() + "/10");
        */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.e("ww", "tiene algo");
            String name = bundle.getString("nombre");
            Integer punt = bundle.getInt("puntuacion");

            nombrePrincipal = (TextView) findViewById(R.id.nombreUser);
            nombrePrincipal.setText(name);
            puntuacion = (TextView) findViewById(R.id.puntuacionUsuario);
            puntuacion.setText(punt.toString() + "/10");


            /*if (bundle.getString("editarUsuario") != null) {
                //Falta rellenar los demás campos...imagen tono color
                nombrePrincipal.setText(bundle.getString("editarUsuario"));
            }
            if (bundle.getString("editarAvatar") != null) {
             /*   InputStream is;
             imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("editarAvatar")));

                try {
                    is = getContentResolver().openInputStream(Uri.parse(bundle.getString("editarAvatar")));
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    imagenPerfil.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {e.printStackTrace();}*/
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void personalizacion(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONFIGURACION, null);
       // startActivityForResult(intentConfiguracion, request_code);
    }

    public void ayuda(View v) {
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "principal");
    }

    public void verInforme(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.VER_INFORME, null);
    }

    public void verEventos(View v){
       Controlador.getInstancia().ejecutaComando(ListaComandos.VER_EVENTOS, null);
    }

    public void verReto(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.VER_RETO, null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){
            Bundle bundle = data.getExtras();
            nombrePrincipal.setText(bundle.getString("nombreNuevo"));
        }
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
