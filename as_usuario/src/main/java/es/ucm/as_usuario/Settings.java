package es.ucm.as_usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Clase asociada a la vista de personalizacion
 *
 * Created by Juan Lu on 24/02/2016.
 */
public class Settings extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizacion);

    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }

    public void cambiarColorApp(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

        LayoutInflater inflater = Settings.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.edit_color, null));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cambiarTonoApp(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

        LayoutInflater inflater = Settings.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.edit_tone, null));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cambiarImagenPerfil(View v) {
        final CharSequence[] items = { "Tomar foto", "Elegir de la galeria", "Salir" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Tomar foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else if (items[item].equals("Elegir de la galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Salir")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
