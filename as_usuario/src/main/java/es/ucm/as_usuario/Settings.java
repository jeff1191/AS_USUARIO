package es.ucm.as_usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Clase asociada a la vista de personalizacion
 *
 * Created by Juan Lu on 24/02/2016.
 */
public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizacion);

        //Get the color Spinner from personlaizacion.xml
        Spinner colorSpinner = (Spinner) findViewById(R.id.coloresPosibles);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
                R.array.colores, R.layout.my_spinner_item);
        // Specify the layout to use when the list of choices appears
        colorAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        // Apply the adapter to the spinner
        colorSpinner.setAdapter(colorAdapter);

        //Get the tono Spinner from personlaizacion.xml
        Spinner tonoSpinner = (Spinner) findViewById(R.id.tonosPosibles);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> tonoAdapter = ArrayAdapter.createFromResource(this,
                R.array.tonos, R.layout.my_spinner_item);
        // Specify the layout to use when the list of choices appears
        tonoAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        // Apply the adapter to the spinner
        tonoSpinner.setAdapter(tonoAdapter);

    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }
}
