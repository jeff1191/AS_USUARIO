package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.util.List;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.as.presentacion.controlador.comandos.factoria.FactoriaComandos;

/**
 * Created by msalitu on 10/03/2016.
 */
public class SincronizarComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario usuario = saUsuario.consultarUsuario();

        SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        List<TransferTarea> tareas = saSuceso.consultarTareas();

        List<TransferEvento> eventos = saSuceso.consultarEventos();
        TransferReto reto = saSuceso.consultarReto();

        Mensaje mensajeEnvio = new Mensaje(usuario,reto,eventos,tareas);

        Log.e("pruebaaa", "Nombre envio-> " + usuario.getNombre());
        Log.e("pruebaaa", "Codigo envio-> " + usuario.getCodigoSincronizacion());

        ConectionManager conectionManager = new ConectionManager(mensajeEnvio);
        if(conectionManager.getResponse() != null) {


            // Problema nombre usuario nulooooooooooooooooooooooooooooooooooooooooooooooooooooooooo


            if (conectionManager.getResponse().getUsuario() != null) {
                if (conectionManager.getResponse().getUsuario().getNombre() != null)
                    Log.e("pruebaaa", "respuesta-> " + conectionManager.getResponse().getUsuario().getNombre());
                else
                    Log.e("pruebaaa", "transfer nombre nulo");
            }

            ///////////////////////////////////////////////////////////////////////////////////////

            String ret = conectionManager.getResponse().getUsuario().getNombre();
            //Va el desmenuze
            Mensaje respuestaTutor = conectionManager.getResponse();
            TransferReto retoDesdeT =  respuestaTutor.getReto();

            TransferReto retoActual = (TransferReto) FactoriaComandos.getInstancia()
                    .getCommand(ListaComandos.VER_RETO).ejecutaComando(null);
            TransferReto retoAU = new TransferReto();
            if(retoDesdeT != null){
                if(retoActual != null){
                    //Tiene un reto, ver si son diferente o no
                    Integer dif = sonIguales(retoDesdeT, retoActual);
                    switch (dif){
                        case 0:
                            FactoriaComandos.getInstancia()
                                    .getCommand(ListaComandos.GESTIONAR_RETO).ejecutaComando(retoAU);
                            return null;
                        case 1:
                            retoAU.setTexto(retoDesdeT.getTexto());
                            retoAU.setPremio(retoAU.getPremio());
                            break;
                        case 2:
                            retoAU.setTexto(retoAU.getTexto());
                            retoAU.setPremio(retoDesdeT.getPremio());
                            break;
                        case 3:
                            retoAU.setTexto(retoDesdeT.getTexto());
                            retoAU.setPremio(retoDesdeT.getPremio());
                            break;
                    }
                }
                else{
                    //Si no tiene un reto: A crear!!
                    retoAU.setPremio(retoDesdeT.getPremio());
                    retoAU.setTexto(retoDesdeT.getTexto());
                }
                retoAU.setContador(0);
                retoAU.setSuperado(false);
                FactoriaComandos.getInstancia()
                        .getCommand(ListaComandos.GESTIONAR_RETO).ejecutaComando(retoAU);
            }
            else if(retoDesdeT == null && retoActual != null){ // No le llega nada y ya hay algo
                //Borrar el reto
                FactoriaComandos.getInstancia()
                        .getCommand(ListaComandos.ELIMINAR_RETO).ejecutaComando(null);
            }

            //Fin sync
            conectionManager.reset();
            Log.e("sync", "Sincronizacion acabada");
        }
        else{
            Log.e("pruebaaa", "respuesta nula");
        }
        return null;
    }

    public Integer sonIguales(TransferReto nuevo, TransferReto viejo){
        Integer d = 0;

        if(nuevo.getPremio() != viejo.getPremio() || nuevo.getTexto() != viejo.getTexto()){
            //Hay algo diferente
            if(nuevo.getPremio() != viejo.getPremio() && nuevo.getTexto() != viejo.getTexto()){
                //Ambos diferentes
                d = 3;
            }
            else if(nuevo.getPremio() != viejo.getPremio()){
                //Premio diferente
                d = 2;
            }
            else{
                //Texto diferente
                d = 1;
            }
        }

        return d;
    }

}
