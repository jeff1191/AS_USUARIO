package es.ucm.as.negocio.suceso.imp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import es.ucm.as.R;
import es.ucm.as.integracion.DBHelper;
import es.ucm.as.integracion.Evento;
import es.ucm.as.integracion.Reto;
import es.ucm.as.integracion.Tarea;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.negocio.utils.Frecuencia;
import es.ucm.as.presentacion.vista.Contexto;

import static es.ucm.as.negocio.utils.Frecuencia.DIARIA;
import static es.ucm.as.negocio.utils.Frecuencia.MENSUAL;
import static es.ucm.as.negocio.utils.Frecuencia.SEMANAL;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class SASucesoImp implements SASuceso {

    private final static String NOMBRE_DOCUMENTO = "Informe.pdf";
    private final static String NOMBRE_DIRECTORIO = "AS";
    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
        }
        return mDBHelper;
    }


    @Override
    public List<TransferEvento> consultarEventos() {

        Dao<Evento, Integer> eventos;
        List<Evento> listaEventos = null;
        List<TransferEvento> transferEventos = new ArrayList<TransferEvento>();
        try {
            eventos = getHelper().getEventoDao();
            listaEventos= eventos.queryForAll();
            for(int i = 0; i < listaEventos.size(); i++)
                transferEventos.add(new TransferEvento(listaEventos.get(i).getId(),listaEventos.get(i).getNombre(),
                        listaEventos.get(i).getFecha(),listaEventos.get(i).getHoraAlarma(),
                        listaEventos.get(i).getAsistencia(), listaEventos.get(i).getHoraEvento()));

        } catch (SQLException e) {

        }
        return transferEventos;
    }

    // Se obtienen los eventos a recordar ese dia ordenadas por horas de manera ascendente
    @Override
    public List<TransferEvento> consultarEventosNotificaciones() {
        List<TransferEvento> eventos = new ArrayList<>();
        try {
            Dao<Evento,Integer> daoE = getHelper().getEventoDao();

            Date actual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(actual);
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = c.getTime();

            QueryBuilder<Evento, Integer> qbEvento = daoE.queryBuilder();
            qbEvento.where().between("HORA_ALARMA",actual, tomorrow);
            qbEvento.orderBy("HORA_ALARMA", true);
            List<Evento> events = qbEvento.query();

            for (int i = 0; i < events.size(); i++)
                eventos.add(events.get(i).getTransfer());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return eventos;
    }

    @Override
    public TransferReto consultarReto() {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        TransferReto tr = null;
        try {
            dao = getHelper().getRetoDao();

            if (dao.queryForAll().size() != 0) {  // comprueba si hay algun activity_reto en la BBDD
                reto =  dao.queryForAll().get(0);
                tr = new TransferReto();
                tr.setContador(reto.getContador());
                tr.setId(reto.getId());
                tr.setPremio(reto.getPremio());
                tr.setTexto(reto.getTexto());
                tr.setSuperado(reto.getSuperado());
            }
        } catch (SQLException e) {

        }
        return tr;
    }


    @Override
    public Integer responderReto(Integer respuesta) {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        try {
            dao = getHelper().getRetoDao();
            if (dao.queryForAll().size() != 0) {
                reto = dao.queryForAll().get(0);
                //Si el activity_reto no esta superado y se puede incrementar o decrementar aun se modifica
                if (!reto.getSuperado() && respuesta == -1 && reto.getContador() > 0 ||
                        !reto.getSuperado() && respuesta == 1 && reto.getContador() <= 30) {
                    reto.setContador(reto.getContador() + respuesta);
                    dao.update(reto);
                }
                if (reto.getContador() == 30) {
                    reto.setSuperado(true);
                    dao.update(reto);
                }
            }
        } catch (SQLException e) {

        }
        return reto.getContador();
    }


    @Override
    public List<TransferTarea> consultarTareas() {

        Dao<Tarea, Integer> tareasDao;
        List<Tarea> tareas = new ArrayList<Tarea>();
        List<TransferTarea> transferTareas = new ArrayList<TransferTarea>();

        try {

            tareasDao = getHelper().getTareaDao();
            tareas = tareasDao.queryForAll();
            for(int i = 0; i < tareas.size(); i++)
                transferTareas.add(tareas.get(i).getTransfer());

        } catch (SQLException e) {

        }
        return transferTareas;
    }


    @Override
    public void cargarTareas(List<TransferTarea> tareas) {

            Dao<Tarea, Integer> tareaDao;

            try {
                tareaDao = getHelper().getTareaDao();

                if(tareas != null) {

                    List<Tarea> misTareas = tareaDao.queryForAll();
                    for(int j = 0; j < misTareas.size(); j++){
                        boolean encontrado = false;
                        for (int i = 0; i < tareas.size() && !encontrado; i++) {
                            if (misTareas.get(j).getTextoAlarma().equals(tareas.get(i).getTextoAlarma()))
                                encontrado = true;
                        }
                        if(!encontrado)
                            tareaDao.deleteById(misTareas.get(j).getId());
                    }

                    for (int i = 0; i < tareas.size(); i++) {

                        TransferTarea transfer = tareas.get(i);
                        Tarea tarea = new Tarea();
                        tarea.setTextoAlarma(transfer.getTextoAlarma());
                        tarea.setHoraAlarma(actualizaDatesTareas(transfer.getHoraAlarma()));
                        tarea.setTextoPregunta(transfer.getTextoPregunta());
                        tarea.setHoraPregunta(actualizaDatesTareas(transfer.getHoraPregunta()));
                        tarea.setContador(transfer.getContador());
                        tarea.setMejorar(transfer.getMejorar());
                        tarea.setNumNo(transfer.getNumNo());
                        tarea.setNumSi(transfer.getNumSi());
                        tarea.setFrecuenciaTarea(transfer.getFrecuenciaTarea());

                        // Si existe en BBDD
                        if (tareaDao.queryForEq("TEXTO_ALARMA", transfer.getTextoAlarma()).size()> 0) {
                            tarea = tareaDao.queryForEq("TEXTO_ALARMA", transfer.getTextoAlarma()).get(0);
                            if (tarea.getHoraAlarma() != transfer.getHoraAlarma())
                                tarea.setHoraAlarma(actualizaDatesTareas(transfer.getHoraAlarma()));
                            if (tarea.getTextoPregunta() != transfer.getTextoPregunta())
                                tarea.setTextoPregunta(transfer.getTextoPregunta());
                            if (tarea.getHoraPregunta() != transfer.getHoraPregunta())
                                tarea.setHoraPregunta(actualizaDatesTareas(transfer.getHoraPregunta()));
                            if (tarea.getMejorar() != transfer.getMejorar())
                                tarea.setMejorar(transfer.getMejorar());
                            if (tarea.getFrecuenciaTarea() != transfer.getFrecuenciaTarea())
                                tarea.setFrecuenciaTarea(transfer.getFrecuenciaTarea());
                        }

                        // En cualquier caso se crea o actualiza
                        tareaDao.createOrUpdate(tarea);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void responderTarea(TransferTarea transferTarea) {
        Dao<Tarea, Integer> tareasDao;
        Tarea tarea;
        try {
            tareasDao = getHelper().getTareaDao();
            tarea = tareasDao.queryForId(transferTarea.getId());
            if(transferTarea != null && tarea != null) {
                // si respondio que "si"***************************************************************
                if (transferTarea.getNumSi() == 1) {
                    tarea.setNumSi(tarea.getNumSi() + 1);
                    tarea.setContador(tarea.getContador() + 1);
                    tarea.setNoSeguidos(0); // se reinicia la cuenta de "no" seguidos

                    // si mejora disminuye la frecuencia y se reinician los contadores
                    if (tarea.getContador() == tarea.getMejorar()) {
                        tarea.setFrecuenciaTarea(disminuirFrecuencia(tarea.getFrecuenciaTarea()));
                        tarea.setContador(0);
                        tarea.setNumSi(0);
                        tarea.setNumNo(0);
                    }
                    /**********************************************************************************/


                    // si respondio que "no"////////////////////////////////////////////////////////////
                } else if(transferTarea.getNumNo() == 1){
                    if (tarea.getNoSeguidos() >= 2) {    // si suma 3 "no" seguidos
                        // Se le aumenta la frecuencia de nuevo
                        tarea.setFrecuenciaTarea(aumentarFrecuencia(tarea.getFrecuenciaTarea()));
                        // Se reinician los contadores
                        tarea.setNumNo(0);
                        tarea.setNumSi(0);
                        tarea.setNoSeguidos(0);
                        tarea.setContador(0);
                    } else {                              // si aun no son 3 "no" seguidos
                        tarea.setNumNo(tarea.getNumNo() + 1);
                        tarea.setNoSeguidos(tarea.getNoSeguidos() + 1);
                        tarea.setContador(tarea.getContador() - 1);
                    }

                }//////////////////////////////////////////////////////////////////////////////////////

                // y se actualiza en la BBDD
                tareasDao.update(tarea);
            }
        } catch (SQLException e) {

        }
    }

    // Se buscan en BBDD las tareas comprendidas entre la hora actual y el dia siquiente
    @Override
    public List<TransferTarea> consultarTareasNotificaciones() {
        ArrayList<TransferTarea> transferTareas = new ArrayList<>();
        try {
            Date actual = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(actual);
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = c.getTime();

            Log.e("CargarNotificaciones", "Entre las "+ actual.toString() + " y las " + tomorrow.toString());
            QueryBuilder<Tarea, Integer> qb = getHelper().getTareaDao().queryBuilder();
            qb.where().between("HORA_ALARMA", actual, tomorrow);
            qb.orderBy("HORA_ALARMA", true);
            List<Tarea> tareas = qb.query();

            for(int i = 0; i < tareas.size(); i++)
                transferTareas.add(tareas.get(i).getTransfer());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferTareas;
    }

    @Override
    public void actualizarNotificaciones() {

        try {
            Dao<Tarea, Integer> tareaDao = getHelper().getTareaDao();
            List<Tarea> tareas = tareaDao.queryForAll();
            for(int i = 0; i < tareas.size(); i++){
                Tarea tarea = tareas.get(i);

                // Se actualizan las proximas horas de alarma y pregunta de esa tarea en base a la frecuencia
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tarea.getHoraAlarma());
                Date newAlarma = proximaNotificacion(calendar, tarea.getFrecuenciaTarea());
                calendar.setTime(tarea.getHoraPregunta());
                Date newPregunta = proximaNotificacion(calendar, tarea.getFrecuenciaTarea());
                tarea.setHoraAlarma(newAlarma);
                tarea.setHoraPregunta(newPregunta);

                tareaDao.update(tarea);
            }

            Dao<Evento, Integer> eventoDao = getHelper().getEventoDao();
            List<Evento> eventos = eventoDao.queryForAll();
            for(int i = 0; i < eventos.size(); i++)
                eventoDao.deleteById(eventos.get(i).getId());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarNotificacionTarea(TransferTarea transferTarea) {
        try {
            Dao<Tarea, Integer> tareaDao = getHelper().getTareaDao();
            Tarea tarea = tareaDao.queryForId(transferTarea.getId());
            if(transferTarea.getNotificacionAlarma() != null)
                tarea.setNotificacionAlarma(transferTarea.getNotificacionAlarma());
            if(transferTarea.getNotificacionPregunta() != null)
                tarea.setNotificacionPregunta(transferTarea.getNotificacionPregunta());
            tareaDao.update(tarea);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Date proximaNotificacion(Calendar old, Frecuencia frecuencia) {
        switch (frecuencia) {
            case DIARIA:
                old.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case SEMANAL:
                old.add(Calendar.DAY_OF_MONTH, 7);
                break;
            case MENSUAL:
                old.add(Calendar.DAY_OF_MONTH, 30);
                break;
        }
        return old.getTime();
    }

    private Frecuencia aumentarFrecuencia (Frecuencia frecuencia){
        Frecuencia nueva = DIARIA;
        switch (frecuencia){
            case DIARIA:
                nueva = DIARIA;
                break;
            case SEMANAL:
                nueva = DIARIA;
                break;
            case MENSUAL:
                nueva = SEMANAL;
                break;
        }
        return nueva;
    }

    private Frecuencia disminuirFrecuencia (Frecuencia frecuencia){
        Frecuencia nueva = MENSUAL;
        switch (frecuencia){
            case DIARIA:
                nueva = SEMANAL;
                break;
            case SEMANAL:
                nueva = MENSUAL;
                break;
            case MENSUAL:
                nueva = MENSUAL;
                break;
        }
        return nueva;
    }

    public Date actualizaDatesTareas(Date a){
        SimpleDateFormat horasMinutos = new SimpleDateFormat("HH:mm");
        StringTokenizer tokens = new StringTokenizer(horasMinutos.format
                (a),":");
        Integer hora = Integer.parseInt(tokens.nextToken());
        Integer minutos =  Integer.parseInt(tokens.nextToken());
        Date horaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(horaActual);
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
        return calendar.getTime();
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {
        // El fichero será almacenado en un directorio dentro del directorio Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }

    @Override
    public String generarPDF() throws IOException, DocumentException{
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario usuario = saUsuario.consultarUsuario();
        String name = usuario.getNombre();
        Integer puntuacion = usuario.getPuntuacion();
        Integer puntuacionAnterior = usuario.getPuntuacionAnterior();

        Document document = new Document();
        File f = crearFichero(NOMBRE_DOCUMENTO);
        PdfWriter.getInstance(document, new FileOutputStream(f.getAbsolutePath()));
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        document.add(new Paragraph("\n", paragraphFont));
        document.add(new Paragraph("Informe AS", titleFont));
        document.add(new Paragraph(name, paragraphFont));
        document.add(new Paragraph("\n", paragraphFont));

        // Insertamos el logo
        Bitmap bitmap = BitmapFactory.decodeResource(Contexto.getInstancia().getContext().getResources(), R.drawable.logo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        Image imagen = Image.getInstance(stream.toByteArray());
        imagen.scaleToFit(75f, 75f);
        imagen.setAbsolutePosition(480f, 730f);
        document.add(imagen);


        // Mostramos la puntuacion anterior y la actual para comparar el progreso
        document.add(new Paragraph("Puntuacion", chapterFont));
        document.add(new Paragraph("\n", paragraphFont));
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Anterior\n" + puntuacionAnterior );
        table.addCell("Actual\n" + puntuacion);

        /*///////////////////////Se me resiste el tam de la flechita///////////////////////////////

        // Flecha roja
        Bitmap bitmapRojo = BitmapFactory.decodeResource(Contexto.getInstancia().getContext().getResources(), R.drawable.flecharoja_informe);
        ByteArrayOutputStream streamRojo = new ByteArrayOutputStream();
        bitmapRojo.compress(Bitmap.CompressFormat.JPEG, 100, streamRojo);
        Image imagenRojo = Image.getInstance(streamRojo.toByteArray());
       // imagenRojo.scaleToFit(25f, 25f);
        // Flecha verde
        Bitmap bitmapVerde = BitmapFactory.decodeResource(Contexto.getInstancia().getContext().getResources(), R.drawable.flechaverde_informe);
        ByteArrayOutputStream streamVerde = new ByteArrayOutputStream();
        bitmapVerde.compress(Bitmap.CompressFormat.JPEG, 100, streamVerde);
        Image imagenVerde = Image.getInstance(streamVerde.toByteArray());
        //imagenVerde.scaleToFit(25f, 25f);
        if (puntuacion - puntuacionAnterior >= 0)
            table.addCell(Image.getInstance(imagenVerde));
        else
            table.addCell(Image.getInstance(imagenRojo));
        *///////////////////////////////////////////////////////////////////////////////////////////

        document.add(table);
        document.add(new Paragraph("\n", paragraphFont));


        // Mostramos el activity_reto
        document.add(new Paragraph("Reto", chapterFont));
        document.add(new Paragraph("\n", paragraphFont));
        Dao<Reto, Integer> retoDao;
        Reto reto = null;
        try {
            retoDao = getHelper().getRetoDao();
            reto = retoDao.queryForId(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (reto != null) {
            document.add(new Paragraph("Texto: " + reto.getTexto(), paragraphFont));
            if (reto.getPremio() != null)
                document.add(new Paragraph("Premio: " + reto.getPremio(), paragraphFont));
            document.add(new Paragraph("Contador: " + reto.getContador().toString() + "/30", paragraphFont));
            if (reto.getSuperado())
                document.add(new Paragraph("Superado", paragraphFont));
            else
                document.add(new Paragraph("No superado", paragraphFont));
            document.add(new Paragraph("\n", paragraphFont));
        }


        // Insertamos una tabla con las tareas y sus puntuaciones.
        document.add(new Paragraph("Tareas", chapterFont));
        document.add(new Paragraph("\n", paragraphFont));
        Dao<Tarea, Integer> tareaDao;
        List<Tarea> tareas = new ArrayList<Tarea>();
        try {
            tareaDao = getHelper().getTareaDao();
            tareas = tareaDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        float[] columnWidths = {1, 5, 1, 1, 1};
        PdfPTable tabla = new PdfPTable(columnWidths);
        tabla.setWidthPercentage(100);
        tabla.getDefaultCell().setUseAscender(true);
        tabla.getDefaultCell().setUseDescender(true);

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell1 = new PdfPCell(new Phrase("Nº", font));
        PdfPCell cell2 = new PdfPCell(new Phrase("Tarea", font));
        PdfPCell cell3 = new PdfPCell(new Phrase("Si", font));
        PdfPCell cell4 = new PdfPCell(new Phrase("No", font));
        PdfPCell cell5 = new PdfPCell(new Phrase("Total", font));
        cell1.setBackgroundColor(BaseColor.BLUE);
        cell2.setBackgroundColor(BaseColor.BLUE);
        cell3.setBackgroundColor(BaseColor.BLUE);
        cell4.setBackgroundColor(BaseColor.BLUE);
        cell5.setBackgroundColor(BaseColor.BLUE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cell1);
        tabla.addCell(cell2);
        tabla.addCell(cell3);
        tabla.addCell(cell4);
        tabla.addCell(cell5);

        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
        for (Integer i = 1; i <= tareas.size(); i++) {
            Tarea tarea = tareas.get(i - 1);
            tabla.addCell(i.toString());
            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            tabla.addCell(tarea.getTextoAlarma());
            tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(tarea.getNumSi().toString());
            tabla.addCell(tarea.getNumNo().toString());
            Integer total = tarea.getNumSi() - tarea.getNumNo();
            tabla.addCell(total.toString());
        }
        document.add(tabla);

        document.close();
        return f.getAbsolutePath();
    }

    @Override
    public void modificarEventos(List<TransferEvento> eventosRespuesta) {

        Dao<Evento, Integer> eventos;
        try {
            eventos = getHelper().getEventoDao();
            for(int i=0; i < eventosRespuesta.size();i++){
                Evento actualizar = eventos.queryForId(eventosRespuesta.get(i).getId());
                actualizar.setAsistencia(eventosRespuesta.get(i).getAsistencia());
                eventos.update(actualizar);
            }

        } catch (SQLException e) {

        }
    }

    @Override
    public void crearEventos(List<TransferEvento> eventosTutor) {

        Dao<Evento, Integer> eventos;
        try {
            eventos = getHelper().getEventoDao();
            List<TransferEvento> eventosBD = FactoriaSA.getInstancia().nuevoSASuceso().consultarEventos();
            //Si ya hay algo en la BD
            if(eventosBD.size() != 0) {
                //Si le llega chicha
                if(eventosTutor.size() != 0){
                    eliminarEventos();
                    for(int i=0; i < eventosTutor.size();i++){
                        Evento nuevoEvento = new Evento();
                        nuevoEvento.setNombre(eventosTutor.get(i).getNombre());
                        nuevoEvento.setFecha(eventosTutor.get(i).getFecha());
                        nuevoEvento.setHoraAlarma(eventosTutor.get(i).getHoraAlarma());
                        nuevoEvento.setAsistencia(eventosTutor.get(i).getAsistencia());
                        nuevoEvento.setHoraEvento(eventosTutor.get(i).getHoraEvento());
                        eventos.create(nuevoEvento);
                    }
                }
                else
                    eliminarEventos();
            }
            else{
                if(eventosTutor != null){
                    for(int i=0; i < eventosTutor.size();i++){
                        Evento nuevoEvento = new Evento();
                        nuevoEvento.setNombre(eventosTutor.get(i).getNombre());
                        nuevoEvento.setFecha(eventosTutor.get(i).getFecha());
                        nuevoEvento.setHoraAlarma(eventosTutor.get(i).getHoraAlarma());
                        nuevoEvento.setHoraEvento(eventosTutor.get(i).getHoraEvento());
                        nuevoEvento.setAsistencia("NO");
                        eventos.create(nuevoEvento);
                    }
                }
            }

        } catch (SQLException e) {

        }
    }

    @Override
    public void eliminarEventos(){
        Dao<Evento, Integer> eventos;
        try {
            eventos = getHelper().getEventoDao();
            List<Evento> eventosBorrar = eventos.queryForAll();
            for(int i= 0; i < eventosBorrar.size();i++) {
                eventos.delete(eventosBorrar.get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crearReto(TransferReto r) {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        try {
            dao = getHelper().getRetoDao();
            reto.setTexto(r.getTexto());
            reto.setPremio(r.getPremio());
            reto.setContador(r.getContador());
            reto.setSuperado(r.getSuperado());
            if (dao.queryForAll().size() != 0) {
                dao.delete(dao.queryForAll().get(0));
                dao.create(reto);
            } else {
                dao.create(reto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarReto() {
        Dao<Reto, Integer> dao;
        try {
            dao = getHelper().getRetoDao();
            dao.delete(dao.queryForAll().get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Este metodo analiza si se debe crear/modificar/eliminar el reto existente despues de la sincronizacion
    @Override
    public void cargarReto(TransferReto nuevoReto) {

        TransferReto retoActual = consultarReto();

        // Llega un reto desde el tutor
        if(nuevoReto.getTexto()!= null){

            // Si el usuario ya tenia reto
            if(retoActual != null){

                // Si se detecta algun cambio entre el reto que manda el tutor y el del usuario
                // se machaca el reto del usuario
                if (!nuevoReto.getTexto().equals(retoActual.getTexto()) ||
                        !nuevoReto.getPremio().equals(retoActual.getPremio())){
                    retoActual.setTexto(nuevoReto.getTexto());
                    retoActual.setPremio(nuevoReto.getPremio());
                    retoActual.setContador(0);
                    retoActual.setSuperado(false);
                    crearReto(retoActual);
                }

            } else{ //Si el usuario no tiene un reto: A crear!!
                retoActual = new TransferReto();
                retoActual.setPremio(nuevoReto.getPremio());
                retoActual.setTexto(nuevoReto.getTexto());
                retoActual.setContador(0);
                retoActual.setSuperado(false);
                crearReto(retoActual);
            }

            // Si no llega reto del tutor hay que borrar el del usuario si lo hubiera
        } else if(nuevoReto.getTexto()== null && retoActual != null) {
            FactoriaSA.getInstancia().nuevoSASuceso().eliminarReto();
        }
    }

}
