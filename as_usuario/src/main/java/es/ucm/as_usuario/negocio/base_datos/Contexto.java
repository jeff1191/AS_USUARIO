package es.ucm.as_usuario.negocio.base_datos;

import android.content.Context;

/**
 * Created by msalitu on 03/03/2016.
 */
public class Contexto {
    private static Contexto instancia ;

    private Context context;

    public static Contexto getInstancia() {
        return instancia;
    }

    public Contexto() {
        context=null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}