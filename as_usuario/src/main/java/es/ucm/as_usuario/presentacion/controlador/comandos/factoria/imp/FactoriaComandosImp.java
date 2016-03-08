package es.ucm.as_usuario.presentacion.controlador.comandos.factoria.imp;

import es.ucm.as_usuario.presentacion.controlador.ListaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.factoria.FactoriaComandos;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.ResponderRetoComando;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.VerAyudaComando;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.VerRetoComando;
import es.ucm.as_usuario.presentacion.controlador.comandos.imp.verEventosCommand;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class FactoriaComandosImp extends FactoriaComandos {
    @Override
    public Command getCommand(String comando) {
        Command ret = null;
        switch(comando){
            case ListaComandos.RESPONDER_RETO:
                ret = new ResponderRetoComando();
            break;
            case ListaComandos.VER_RETO:
                ret = new VerRetoComando();
                break;
            case ListaComandos.VER_EVENTOS:
                ret = new verEventosCommand();
            break;
            case ListaComandos.AYUDA:
                ret = new VerAyudaComando();
        }

        return ret;
    }
}
