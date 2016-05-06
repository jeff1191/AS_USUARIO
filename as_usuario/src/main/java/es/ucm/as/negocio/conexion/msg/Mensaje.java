package es.ucm.as.negocio.conexion.msg;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.Date;

        import es.ucm.as.negocio.suceso.Evento;
        import es.ucm.as.negocio.suceso.TransferEvento;
        import es.ucm.as.negocio.suceso.TransferReto;
        import es.ucm.as.negocio.suceso.TransferTarea;
        import es.ucm.as.negocio.usuario.TransferUsuario;

/**
 * Created by msalitu on 28/04/2016.
 */
public class Mensaje implements Serializable{

    static final long serialVersionUID = 1L;

    private String verificar;
    private TransferUsuario usuario;
    private TransferReto reto;
    private ArrayList<TransferEvento> eventos;
    private ArrayList<TransferTarea> tareas;

    public Mensaje(String verificar){
        this.verificar = verificar;
    }

    public Mensaje(){
        this.verificar = "";
        usuario = new TransferUsuario();
        reto = new TransferReto();
        eventos = new ArrayList<TransferEvento>();
        tareas = new ArrayList<TransferTarea>();
    }

    public Mensaje(TransferUsuario usuario, TransferReto reto,
                   ArrayList<TransferEvento> eventos, ArrayList<TransferTarea> tareas ){
        this.usuario = usuario;
        this.reto = reto;
        this.eventos = eventos;
        this.tareas = tareas;
    }

    public TransferUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TransferUsuario usuario) {
        this.usuario = usuario;
    }

    public TransferReto getReto() {
        return reto;
    }

    public void setReto(TransferReto reto) {
        this.reto = reto;
    }

    public ArrayList<TransferEvento> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<TransferEvento> eventos) {
        this.eventos = eventos;
    }

    public ArrayList<TransferTarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<TransferTarea> tareas) {
        this.tareas = tareas;
    }

    public String getVerificar() {
        return verificar;
    }

    public void setVerificar(String verificar) {
        this.verificar = verificar;
    }
}
