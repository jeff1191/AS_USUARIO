package es.ucm.as_usuario.integracion;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.presentacion.Contexto;

/**
 * Created by msalitu on 14/03/2016.
 */
public class Parser {

    public String readLine() {
        String linea = "";

        try {
            InputStream fraw = Contexto.getInstancia().getContext().getResources().openRawResource(R.raw.perfil_a);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            linea = brin.readLine();

            fraw.close();

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
        Log.e("Ficheros", linea);
        return linea;
    }
}
