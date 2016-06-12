package es.ucm.as.presentacion.controlador.comandos.factoria;

import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.factoria.imp.FactoriaComandosImp;


public abstract class FactoriaComandos {
    private static FactoriaComandos instancia;

    public static FactoriaComandos getInstancia() {

        if (instancia == null){
            instancia = new FactoriaComandosImp();
        }
        return instancia;
    }
    public abstract Command getCommand(String comando);
}
