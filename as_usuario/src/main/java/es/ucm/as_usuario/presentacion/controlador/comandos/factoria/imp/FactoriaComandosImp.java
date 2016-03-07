package es.ucm.as_usuario.presentacion.controlador.comandos.factoria.imp;

import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.Configuracion;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.EditarUsuario;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.ResponderReto;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.verEventosCommand;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class FactoriaComandosImp extends FactoriaComandos {
    @Override
    public Command getCommand(String comando) {
        Command ret = null;
        switch(comando){
            case ListaComandos.RESPONDER_SI_RETO:
                ret= new ResponderReto();
            break;
            case ListaComandos.VER_EVENTOS:
                ret= new verEventosCommand();
            break;
            case ListaComandos.CONFIGURACION:
                ret= new Configuracion();
            break;
            case ListaComandos.EDITAR_USUARIO:
                ret= new EditarUsuario();
            break;
        }

        return ret;
    }
}
