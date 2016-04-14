package es.ucm.as_usuario.presentacion.vista;

import android.content.Context;

public class Contexto {
    private static Contexto instancia = new Contexto();

    private Context actividadPrincipal;

    public static Contexto getInstancia() {
        return instancia;
    }

    public Contexto() {
    }

    public Context getContext() {
        return actividadPrincipal;
    }

    public void setContext(Context actividadPrincipal) {
        this.actividadPrincipal = actividadPrincipal;
    }
}
