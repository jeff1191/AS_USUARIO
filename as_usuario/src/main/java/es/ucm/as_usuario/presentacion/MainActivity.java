package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;
import es.ucm.as_usuario.presentacion.notificaciones.ServicioNotificaciones;


public class MainActivity extends Activity {

    private TextView nombrePrincipal;
    private TextView puntuacion;
    private ImageView imagenPerfil;
    private int request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Command c = FactoriaComandos.getInstancia().getCommand(ListaComandos.CONSULTAR_USUARIO);
        TransferUsuario cargarUsuario = new TransferUsuario();
        try {
            cargarUsuario = (TransferUsuario) c.ejecutaComando(null);
        } catch (commandException e) {
            e.printStackTrace();
        }
        Configuracion.temaActual=cargarUsuario.getColor();
        cargarTema();
        super.onCreate(savedInstanceState);

        // Esto es para solventar un error al enviar el correo
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.activity_main);
        nombrePrincipal=(TextView)findViewById(R.id.nombreUser);
        puntuacion = (TextView)findViewById(R.id.puntuacionUsuario);
        imagenPerfil= (ImageView) findViewById(R.id.avatar);
        if(cargarUsuario.getAvatar() != null && !cargarUsuario.getAvatar().equals(""))
            imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(cargarUsuario.getAvatar()));
        else
            imagenPerfil.setImageResource(R.drawable.avatar);
        puntuacion.setText(cargarUsuario.getPuntuacion() + "/10");
        nombrePrincipal.setText(cargarUsuario.getNombre());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
          /*  String name = bundle.getString("nombre");
            Integer punt = bundle.getInt("puntuacion");

            nombrePrincipal = (TextView) findViewById(R.id.nombreUser);
            nombrePrincipal.setText(name);
            puntuacion = (TextView) findViewById(R.id.puntuacionUsuario);
            puntuacion.setText(punt.toString() + "/10");*/

            if (bundle.getString("editarUsuario") != null) {
                //Falta rellenar los demás campos...imagen tono color
                nombrePrincipal.setText(bundle.getString("editarUsuario"));
            }
            if (bundle.getString("editarAvatar") != null && !bundle.getString("editarAvatar").equals("")) {
             /*  InputStream is;


                try {
                    is = getContentResolver().openInputStream(Uri.parse(bundle.getString("editarAvatar")));
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    imagenPerfil.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {e.printStackTrace();}*/
                imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("editarAvatar")));
            }
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
    }

    public void ayuda(View v) {
        //Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "principal");

        // Enviar el correo
        Controlador.getInstancia().ejecutaComando(ListaComandos.ENVIAR_CORREO, null);
    }

    public void verInforme(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.GENERAR_PDF, null);
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
