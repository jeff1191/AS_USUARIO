package es.ucm.as.presentacion.controlador.comandos.factoria.imp;

import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.factoria.FactoriaComandos;
import es.ucm.as.presentacion.controlador.comandos.imp.ActualizaPuntuacionComando;
import es.ucm.as.presentacion.controlador.comandos.imp.ActualizarNotificacionTareaComando;
import es.ucm.as.presentacion.controlador.comandos.imp.ConfiguracionComando;
import es.ucm.as.presentacion.controlador.comandos.imp.ConsultarUsuarioComando;
import es.ucm.as.presentacion.controlador.comandos.imp.CrearUsuarioComando;
import es.ucm.as.presentacion.controlador.comandos.imp.EditarUsuarioComando;
import es.ucm.as.presentacion.controlador.comandos.imp.EnviarCorreoComando;
import es.ucm.as.presentacion.controlador.comandos.imp.GenerarPDFComando;
import es.ucm.as.presentacion.controlador.comandos.imp.GuardarEventosComando;
import es.ucm.as.presentacion.controlador.comandos.imp.HayUsuarioComando;
import es.ucm.as.presentacion.controlador.comandos.imp.InfoComando;
import es.ucm.as.presentacion.controlador.comandos.imp.ResponderRetoComando;
import es.ucm.as.presentacion.controlador.comandos.imp.ResponderTareaComando;
import es.ucm.as.presentacion.controlador.comandos.imp.SincronizarComando;
import es.ucm.as.presentacion.controlador.comandos.imp.SincronizarRegistroComando;
import es.ucm.as.presentacion.controlador.comandos.imp.VerAyudaComando;
import es.ucm.as.presentacion.controlador.comandos.imp.VerEventosComando;
import es.ucm.as.presentacion.controlador.comandos.imp.VerInformeComando;
import es.ucm.as.presentacion.controlador.comandos.imp.VerRetoComando;
import es.ucm.as.presentacion.controlador.comandos.imp.actualizarNotificacionesComando;

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
                ret= new VerEventosComando();
            break;
            case ListaComandos.CONFIGURACION:
                ret= new ConfiguracionComando();
            break;
            case ListaComandos.EDITAR_USUARIO:
                ret= new EditarUsuarioComando();
            break;
            case ListaComandos.AYUDA:
                ret = new VerAyudaComando();
                break;
            case ListaComandos.VER_INFORME:
                ret = new VerInformeComando();
                break;
            case ListaComandos.ACTUALIZAR_PUNTUACION:
                ret = new ActualizaPuntuacionComando();
                break;
            case ListaComandos.CREAR_USUARIO:
                ret = new CrearUsuarioComando();
                break;
            case ListaComandos.CONSULTAR_USUARIO:
                ret = new ConsultarUsuarioComando();
                break;
            case ListaComandos.GENERAR_PDF:
                ret = new GenerarPDFComando();
                break;
            case ListaComandos.ENVIAR_CORREO:
                ret = new EnviarCorreoComando();
                break;
            case ListaComandos.MODIFICAR_EVENTOS:
                ret = new GuardarEventosComando();
                break;
            case ListaComandos.SINCRONIZAR:
                ret = new SincronizarComando();
                break;
            case ListaComandos.SINCRONIZAR_REGISTRO:
                ret = new SincronizarRegistroComando();
                break;
            case ListaComandos.HAY_USUARIO:
                ret = new HayUsuarioComando();
                break;
            case ListaComandos.RESPONDER_TAREA:
                ret = new ResponderTareaComando();
                break;
            case ListaComandos.ACTUALIZAR_NOTIFICACIONES:
                ret = new actualizarNotificacionesComando();
                break;
            case ListaComandos.ACTUALIZAR_NOTIFICACION_TAREA:
                ret = new ActualizarNotificacionTareaComando();
                break;
            case ListaComandos.INFO:
                ret = new InfoComando();
                break;
        }

        return ret;
    }
}
