package es.ucm.as.presentacion.controlador;

import es.ucm.as.presentacion.controlador.imp.ControladorImp;

/**
 * Created by Jeffer on 02/03/2016.
 */
public abstract class Controlador {
    private static Controlador controlador;
    public abstract void ejecutaComando(String comando, Object datos);
    public abstract void actualizaVista(String comando, Object datos);
    public static Controlador getInstancia() {
        if (controlador == null) {
            controlador = new ControladorImp();
        }
        return controlador;
    }
}
