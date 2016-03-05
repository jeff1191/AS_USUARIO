package es.ucm.as_usuario.presentacion.controlador.imp;

import android.util.Log;

import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.Dispatcher;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class ControladorImp extends Controlador {
    @Override
    public void ejecutaComando(String accion, Object datos) {
        Command comando = FactoriaComandos.getInstancia().getCommand(accion);
        Object ret;
        try {
            ret = comando.ejecutaComando(datos);
            actualizaVista(accion,ret);
        } catch (commandException e) {
            // TODO Auto-generated catch block
            //AQUI SEGURAMENTE HAGA FALTA OTRO METOODO PARA QUE EL DISPATCHER LANCE ERROREs
        }
    }

    @Override
    public void actualizaVista(String accion, Object datos) {
        Log.d("Info", "actualizaVista");
        Dispatcher.getInstancia().dispatch(accion, datos);
    }
}
