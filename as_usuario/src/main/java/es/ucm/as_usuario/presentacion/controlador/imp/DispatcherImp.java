package es.ucm.as_usuario.presentacion.controlador.imp;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.TransferReto;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
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
                Log.d("Info", "LLEGA: " + eventosModelo.get(0).getTextoPregunta());
                ArrayList<String> listaActividad= new ArrayList<String>();

                for(int i=0; i < eventosModelo.size(); i++){
                   // eventosModelo.get(i).getFechaIni()
                    String addEvento= eventosModelo.get(i).getTextoPregunta() + " a las " + eventosModelo.get(i).getFechaIni();
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
                ArrayList<TransferTarea> tareasModelo= (ArrayList<TransferTarea>) datos;
                ArrayList<String> titulos = new ArrayList<String>();
                ArrayList<Integer> si = new ArrayList<Integer>();
                ArrayList<Integer> no = new ArrayList<Integer>();

                for(int j=0; j < tareasModelo.size(); j++){
                    titulos.add(tareasModelo.get(j).getTextoAlarma());
                    no.add(tareasModelo.get(j).getNumNo());
                    si.add(tareasModelo.get(j).getNumSi());
                }

                intentTareas.putStringArrayListExtra("titulos", titulos);
                intentTareas.putIntegerArrayListExtra("no", no);
                intentTareas.putIntegerArrayListExtra("si", si);
                Contexto.getInstancia().getContext().startActivity(intentTareas);
                break;

            case ListaComandos.ACTUALIZAR_PUNTUACION:
                Intent iPuntuacon = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                iPuntuacon.putExtra("puntuacion", (Integer)datos);
                break;

            case ListaComandos.CREAR_USUARIO:
                break;

            case ListaComandos.RESPONDER_TAREA:
                break;
        }
    }
}
