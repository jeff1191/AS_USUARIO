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
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

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
        Log.e("pruebaaa", usuario.getNombre());
        Log.e("pruebaaa", usuario.getCodigoSincronizacion());

        ConectionManager conectionManager = new ConectionManager(mensajeEnvio);
        if(conectionManager.getResponse() != null) {


            // Problema nombre usuario nulooooooooooooooooooooooooooooooooooooooooooooooooooooooooo


            if (conectionManager.getResponse().getUsuario() != null) {
                if (conectionManager.getResponse().getUsuario().getNombre() != null)
                    Log.e("pruebaaa", conectionManager.getResponse().getUsuario().getNombre());
                else
                    Log.e("pruebaaa", "transfer nombre nulo");
            }

            ///////////////////////////////////////////////////////////////////////////////////////



            //String ret = conectionManager.getResponse().getUsuario().getNombre();
            /*Va el desmenuze
            Mensaje respuestaTutor = conectionManager.getResponse();
            TransferReto retoDesdeT =  respuestaTutor.getReto();

            TransferReto retoActual = (TransferReto) FactoriaComandos.getInstancia()
                    .getCommand(ListaComandos.VER_RETO).ejecutaComando(null);
            TransferReto retoAU = new TransferReto();
            if(retoDesdeT != null){ //Le llega algo (Uno nuevo o vuelve a empezar)
                if(retoActual != null){
                    //Si ya hay un reto y difieren de algo: A modificar
                    if(retoActual.getTexto() != retoDesdeT.getTexto()){
                        if(retoActual.getPremio() != retoDesdeT.getPremio()){
                            retoAU.setPremio(retoDesdeT.getPremio());
                        }
                        else{
                            retoAU.setPremio(retoAU.getPremio());
                        }
                        retoAU.setTexto(retoDesdeT.getTexto());
                        retoAU.setContador(0);
                        retoAU.setSuperado(false);
                    }
                    if(retoActual.getPremio() != retoDesdeT.getPremio()){
                        retoAU.setPremio(retoDesdeT.getPremio());
                    }

                }
                else{
                    //Si no tiene un reto: A crear!!
                    retoAU.setPremio(retoDesdeT.getPremio());
                    retoAU.setTexto(retoDesdeT.getTexto());
                    retoAU.setContador(0);
                    retoAU.setSuperado(false);
                    //Comando crea RETO
                }
            }
            else if(retoDesdeT == null && retoActual != null){ // No le llega nada y ya hay algo
                //Borrar el reto
                //Comando borra RETO
            }

            //Fin sync
            conectionManager.reset();
            Log.e("sync", "Sincronizacion acabada");*/
        }
        else{
            Log.e("pruebaaa", "respuesta nula");
        }
        return null;
    }
}
