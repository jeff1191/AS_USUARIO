package es.ucm.as_usuario.negocio.base_datos;

import android.app.Activity;
import android.content.Context;

/**
 * Created by msalitu on 03/03/2016.
 */
public class Contexto {
    private static Contexto instancia = new Contexto();

    private Activity actividadPrincipal;

    public static Contexto getInstancia() {
        return instancia;
    }

    public Contexto() {
    }

    public Activity getActividadPrincipal() {
        return actividadPrincipal;
    }

    public void setActividadPrincipal(Activity actividadPrincipal) {
        this.actividadPrincipal = actividadPrincipal;
    }
}
