package es.ucm.as_usuario.presentacion.controlador.comandos.imp;

import es.ucm.as_usuario.negocio.factoria.FactoriaSA;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.presentacion.controlador.comandos.Command;
import es.ucm.as_usuario.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class ResponderReto implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SASuceso servicioTareas= FactoriaSA.getInstancia().nuevoSASuceso();


        //Seguramente haga falta un metodo en el dispatcher que sea mandar error o algo asi
        servicioTareas.responderReto((Integer) datos);
           //throw new commandException("MANDO ERROR AL DISPATCHER"); //lo captura el controlador

        //return "Has respondido el reto"; //se enviaria al controlador para luego enviarselo al dispatcher


       // servicioTareas.responderReto(); //dependiendo de lo que te devuelva este metodo lanzas excepcion o no
        return null;//aqui devolverias algo bonito para que lo actualice el dispatcher, seguramente un string (Toast) o algo asi
    }
}
