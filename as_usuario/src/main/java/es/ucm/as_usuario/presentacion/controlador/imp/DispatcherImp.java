package es.ucm.as_usuario.presentacion.controlador.imp;

import android.content.Intent;
import android.util.Log;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.TransferEvento;
import es.ucm.as_usuario.negocio.suceso.TransferReto;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;
import es.ucm.as_usuario.presentacion.Ayuda;
import es.ucm.as_usuario.presentacion.Configuracion;
import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.presentacion.MainActivity;
import es.ucm.as_usuario.presentacion.VerEventos;
import es.ucm.as_usuario.presentacion.VerInforme;
import es.ucm.as_usuario.presentacion.VerReto;
import es.ucm.as_usuario.presentacion.controlador.Dispatcher;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class DispatcherImp extends Dispatcher{
    @Override
    public void dispatch(String accion, Object datos) {

        switch(accion){
            case ListaComandos.VER_EVENTOS:
                Intent intent = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerEventos.class);
                List<Evento> eventosModelo= (List<Evento>) datos;
                ArrayList<String> listaActividad= new ArrayList<String>();
                for(int i=0; i < eventosModelo.size(); i++){
                    String addEvento= eventosModelo.get(i).getTextoAlarma() + " el " + eventosModelo.get(i).getTextoFecha();
                    listaActividad.add(addEvento);
                }
                intent.putStringArrayListExtra("listaEventos", listaActividad);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;

            case ListaComandos.CONFIGURACION:
                Intent intentConfiguracion = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Configuracion.class);
                TransferUsuario conf = (TransferUsuario) datos;
                intentConfiguracion.putExtra("nombreConfiguracion", conf.getNombre());
                intentConfiguracion.putExtra("frecuenciaInformeConfiguracion", conf.getFrecuenciaRecibirInforme());
                intentConfiguracion.putExtra("imagenConfiguracion", conf.getAvatar());
                Contexto.getInstancia().getContext().startActivity(intentConfiguracion);
                break;
            case ListaComandos.EDITAR_USUARIO:
                Intent intentEditarUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                TransferUsuario editarUsuario = (TransferUsuario) datos;
                intentEditarUsuario.putExtra("editarNombre", editarUsuario.getNombre());
                intentEditarUsuario.putExtra("editarAvatar", editarUsuario.getAvatar());
                Contexto.getInstancia().getContext().startActivity(intentEditarUsuario);
                break;

            case ListaComandos.AYUDA:
                Intent intentAyuda = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Ayuda.class);
                intentAyuda.putExtra("pantalla", (String)datos);
                Contexto.getInstancia().getContext().startActivity(intentAyuda);
                break;

            case ListaComandos.VER_RETO:
                Intent intentR = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerReto.class);
                TransferReto r = (TransferReto)datos;
                if (r != null) {
                    intentR.putExtra("textReto", r.getNombre());
                    intentR.putExtra("contadorReto", r.getContador());
                    intentR.putExtra("superadoReto", r.getSuperado());
                }

                Contexto.getInstancia().getContext().startActivity(intentR);
                break;

            case ListaComandos.RESPONDER_RETO:
                break;

            case ListaComandos.VER_INFORME:

                Intent intentTareas = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerInforme.class);
                ArrayList<Object> a = (ArrayList<Object>)datos;
                TransferUsuario tu = (TransferUsuario) a.get(0);

                ArrayList<String> titulos = new ArrayList<String>();
                ArrayList<Integer> si = new ArrayList<Integer>();
                ArrayList<Integer> no = new ArrayList<Integer>();

                for(int j = 1; j < a.size(); j++){
                    TransferTarea tt = (TransferTarea)a.get(j);
                    titulos.add(tt.getTextoAlarma());
                    no.add(tt.getNumNo());
                    si.add(tt.getNumSi());
                }

                intentTareas.putExtra("puntuacion actual",tu.getPuntuacion() );
                intentTareas.putExtra("puntuacion anterior", tu.getPuntuacionAnterior());
                intentTareas.putStringArrayListExtra("titulos", titulos);
                intentTareas.putIntegerArrayListExtra("no", no);
                intentTareas.putIntegerArrayListExtra("si", si);
                Contexto.getInstancia().getContext().startActivity(intentTareas);
                break;

            case ListaComandos.ACTUALIZAR_PUNTUACION:
                Intent iPuntuacion = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                iPuntuacion.putExtra("puntuacion", (Integer)datos);
                break;

            case ListaComandos.CREAR_USUARIO:
                break;

            case ListaComandos.RESPONDER_TAREA:
                break;

            case ListaComandos.CONSULTAR_USUARIO:
                Intent iUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                TransferUsuario transferUsuario = (TransferUsuario)datos;
                if (transferUsuario!= null) {
                    iUsuario.putExtra("nombre", transferUsuario.getNombre());
                    iUsuario.putExtra("correo", transferUsuario.getCorreo());
                    iUsuario.putExtra("avatar", transferUsuario.getAvatar());
                    iUsuario.putExtra("puntuacion", transferUsuario.getPuntuacion());
                    iUsuario.putExtra("puntuacion anterior", transferUsuario.getPuntuacionAnterior());
                    iUsuario.putExtra("color", transferUsuario.getColor());
                    iUsuario.putExtra("tono", transferUsuario.getTono());
                    iUsuario.putExtra("frecuencia", transferUsuario.getFrecuenciaRecibirInforme());
                    iUsuario.putExtra("nombre tutor", transferUsuario.getNombreTutor());
                    iUsuario.putExtra("correo tutor", transferUsuario.getCorreoTutor());
                }             
                Contexto.getInstancia().getContext().startActivity(iUsuario);
                break;

            case ListaComandos.CARGAR_BBDD:
                break;
        }
    }
}
