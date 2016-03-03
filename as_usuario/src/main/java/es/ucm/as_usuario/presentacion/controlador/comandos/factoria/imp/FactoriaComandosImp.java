package es.ucm.as_usuario.presentacion.controlador.comandos.factoria.imp;

import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.ResponderReto;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class FactoriaComandosImp extends FactoriaComandos {
    @Override
    public Command getCommand(String comando) {
        //dependiendo del string definido el lista comandos devuelve un comando de ese tipo para que lo execute la factoria
        //un switch seria lo mejor
        if(comando.equals(ListaComandos.RESPONDER_SI_RETO))
            return new ResponderReto();

        return null;
    }
}
