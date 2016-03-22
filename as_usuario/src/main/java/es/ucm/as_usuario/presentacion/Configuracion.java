package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Clase asociada a la vista de personalizacion
 *
 * Created by Juan Lu on 24/02/2016.
 */
public class Configuracion extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE =3;
    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;
    private EditText editarNombre;
    private Button aceptar;
    private Button color;
    private Button tono;
    private RadioButton diaria;
    private RadioButton semanal;
    private RadioButton mensual;
    private RadioGroup rdgGrupo;
    private Frecuencia frecActual;
    static String temaActual="AS_theme_azul";
    static String tonoActual="tono1";
    private String[] nombresColores={ "Azul", "Rojo", "Rosa", "Verde",
            "Negro"};
    private String[] nombresTonos = { "Tono 1", "Tono 2", "Tono 3", "Tono 4",
            "Tono 5"};
    private Spinner spinnerColors;
    private Spinner spinnerTono;
    private String temaParcial;
    private String tonoParcial;
    private ImageView imagenConfiguracion;
    private String rutaImagen="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizacion);
        Bundle bundle = getIntent().getExtras();
        editarNombre = (EditText)findViewById(R.id.editarNombre);
        aceptar = (Button)findViewById(R.id.envioNuevaConfig);
        diaria =(RadioButton)findViewById(R.id.diario);
        semanal =(RadioButton)findViewById(R.id.semanal);
        mensual =(RadioButton)findViewById(R.id.mensual);
        rdgGrupo = (RadioGroup)findViewById(R.id.rdgGrupo);
        spinnerColors = (Spinner) findViewById(R.id.cambiarColor);
        spinnerTono = (Spinner) findViewById(R.id.cambiarTono);
        imagenConfiguracion = (ImageView) findViewById(R.id.editarAvatar);
        tonoParcial=tonoActual;
        temaActual=bundle.getString("temaConfiguracion");
        temaParcial=temaActual;
        rutaImagen=bundle.getString("imagenConfiguracion");
        ////////Spinner color ///////
        nombresColoresSistema();
        ArrayAdapter<String> adapter_colores= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,nombresColores);
        adapter_colores
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColors.setAdapter(adapter_colores);
        spinnerColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerColors.setSelection(position);
                String colorSeleccionado = (String) spinnerColors.getSelectedItem();

                switch (colorSeleccionado){
                    case "Azul":
                        temaParcial="AS_theme_azul";
                    break;

                    case "Rojo":
                        temaParcial="AS_theme_rojo";
                        break;

                    case "Rosa":
                        temaParcial="AS_theme_rosa";
                        break;

                    case "Verde":
                        temaParcial="AS_theme_verde";
                        break;
                    case "Negro":
                        temaParcial="AS_theme_negro";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////Spinner tonos///////////////////
        nombresTonosSistema();
        ArrayAdapter<String> adapter_tonos = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nombresTonos);
        adapter_tonos
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTono.setAdapter(adapter_tonos);
        spinnerTono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTono.setSelection(position);
                String selState = (String) spinnerTono.getSelectedItem();
                String tonoSeleccionado = (String) spinnerTono.getSelectedItem();
                switch (tonoSeleccionado){
                    case "Tono 1":
                        tonoParcial="tono1";
                        break;
                    case "Tono 2":
                        tonoParcial="tono2";
                        break;
                    case "Tono 3":
                        tonoParcial="tono3";
                        break;
                    case "Tono 4":
                        tonoParcial="tono4";
                        break;
                    case "Tono 5":
                        tonoParcial="tono5";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ////////////////////////////////////////////////////
        if(!bundle.getString("imagenConfiguracion").equals(""))
            imagenConfiguracion.setImageBitmap(BitmapFactory.decodeFile(bundle.getString("imagenConfiguracion")));
        else
            imagenConfiguracion.setImageResource(R.drawable.avatar);

        editarNombre.setText(bundle.getString("nombreConfiguracion"));
        frecActual=(Frecuencia)bundle.getSerializable("frecuenciaInformeConfiguracion");
        if(frecActual == Frecuencia.DIARIA){
            diaria.setChecked(true);
            semanal.setChecked(false);
            mensual.setChecked(false);
        }
        else
            if(frecActual == Frecuencia.SEMANAL){
                diaria.setChecked(false);
                semanal.setChecked(true);
                mensual.setChecked(false);
            }
            else
                if(frecActual == Frecuencia.MENSUAL){
                    diaria.setChecked(false);
                    semanal.setChecked(false);
                    mensual.setChecked(true);
                }

        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // TODO Auto-generated method stub
                if (checkedId == R.id.diario)
                     frecActual= Frecuencia.DIARIA;
                 else
                    if (checkedId == R.id.semanal)
                        frecActual= Frecuencia.SEMANAL;
                    else
                        if (checkedId == R.id.mensual)
                             frecActual= Frecuencia.MENSUAL;
            }

        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editarNombre.getText().toString().matches("")) {
                    TransferUsuario editarUsuario = new TransferUsuario();
                    editarUsuario.setNombre(String.valueOf(editarNombre.getText()));
                    editarUsuario.setFrecuenciaRecibirInforme(frecActual);
                    temaActual = temaParcial;
                    tonoActual = tonoParcial;
                    editarUsuario.setColor(temaActual);
                    editarUsuario.setAvatar(rutaImagen);

                    Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_USUARIO, editarUsuario);

                }else{
                    Toast errorNombre =
                            Toast.makeText(getApplicationContext(),
                                    "No puede estar vacío el campo nombre", Toast.LENGTH_SHORT);

                    errorNombre.show();
                }

            }
        });


    }

    private void nombresTonosSistema() {
        switch (tonoActual){
            case "tono1":
                nombresTonos[0]="Tono 1"; nombresTonos[1]="Tono 2";nombresTonos[2]="Tono 3";nombresTonos[3]="Tono 4";nombresTonos[4]="Tono 5";
                break;
            case "tono2":
                nombresTonos[0]="Tono 2"; nombresTonos[1]="Tono 1";nombresTonos[2]="Tono 3";nombresTonos[3]="Tono 4";nombresTonos[4]="Tono 5";
                break;
            case "tono3":
                nombresTonos[0]="Tono 3"; nombresTonos[1]="Tono 1";nombresTonos[2]="Tono 2";nombresTonos[3]="Tono 4";nombresTonos[4]="Tono 5";
                break;
            case "tono4":
                nombresTonos[0]="Tono 4"; nombresTonos[1]="Tono 1";nombresTonos[2]="Tono 2";nombresTonos[3]="Tono 3";nombresTonos[4]="Tono 5";
                break;
            case "tono5":
                nombresTonos[0]="Tono 5"; nombresTonos[1]="Tono 1";nombresTonos[2]="Tono 2";nombresTonos[3]="Tono 3";nombresTonos[4]="Tono 4";
                break;
        }
    }

    private void nombresColoresSistema() {
        switch (temaActual){
            case "AS_theme_azul":
                nombresColores[0]="Azul"; nombresColores[1]="Rojo";nombresColores[2]="Rosa";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_rojo":
                nombresColores[0]="Rojo"; nombresColores[1]="Azul";nombresColores[2]="Rosa";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_rosa":
                nombresColores[0]="Rosa"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_verde":
                nombresColores[0]="Verde"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Rosa";nombresColores[4]="Negro";
                break;
            case "AS_theme_negro":
                nombresColores[0]="Negro"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Rosa";nombresColores[4]="Verde";
                break;
        }
    }

    public void cargarTema(){
        switch (temaActual){
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


    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "configuracion");
    }

    public void cambiarImagenPerfil(View v) {
        final CharSequence[] items = { "Hacer foto", "Elegir de la galeria", "Imagen por defecto" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Hacer foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMARA);
                } else if (items[item].equals("Elegir de la galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECCIONAR_GALERIA);
                } else if (items[item].equals("Imagen por defecto")) {
                    imagenConfiguracion.setImageResource(R.drawable.avatar);
                    rutaImagen="";
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMARA) {
                Bitmap imagen = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagenConfiguracion.setImageBitmap(imagen);
                rutaImagen = destination.getPath();
            } else if (requestCode == SELECCIONAR_GALERIA) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                imagenConfiguracion.setImageBitmap(bm);
                rutaImagen=selectedImagePath;
            }
        }
    }
}
