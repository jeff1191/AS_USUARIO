package es.ucm.as.negocio.conexion;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import es.ucm.as.negocio.conexion.msg.Mensaje;


/**
 * Created by msalitu on 05/05/2016.
 */
public class ConectionManager {

    protected static String codigo;
    protected static MyClientTask myClientTask;
    protected static Mensaje respuesta;

    public ConectionManager(Mensaje mensaje){
        this.codigo = mensaje.getUsuario().getCodigoSincronizacion();
        Log.e("prueba cod-> ", codigo);
        String ip = getIp();
        if (ip != null) {
            myClientTask = new MyClientTask(ip, 8080, mensaje);

            try {
                respuesta = myClientTask.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset(){
        respuesta = null;
    }

    public static Mensaje getResponse(){
        return respuesta;
    }

    public static String getIp(){
        final ExecutorService es = Executors.newFixedThreadPool(20);
        final int timeout = 300;
        final int port = 8080;
        final List<Future<String>> futures = new ArrayList<Future<String>>() ;

        for (int i = 0; i <= 255; i++) {
            String ip = "192.168.1."+i;
            futures.add(checkIfPortIsOpen(es, ip, port, timeout));
        }

        es.shutdown();

        for (final Future<String> future : futures) {
            try {
                if (future.get() != null)
                    return future.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Log.e("prueba", "No ip");
        return null;
    }

    private static Future<String> checkIfPortIsOpen(final ExecutorService executorService,
                                                    final String ip,
                                                    final int port,
                                                    final int timeout) {
        return executorService.submit(new Callable<String>() {
            Socket socket;
            PrintWriter mOut;
            @Override public String call() {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    Mensaje msgToServer = new Mensaje("Cod:"+ codigo);
                    ObjectOutputStream dataOutputStream = new ObjectOutputStream(
                            socket.getOutputStream());
                    dataOutputStream.writeObject(msgToServer);
                    Log.e("prueba", msgToServer.getVerificar());
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    Mensaje response = (Mensaje) objectInputStream.readObject();
                    if(!response.getVerificar().equals("Permitido")) {
                        Log.e("prueba", "no me permite");
                        return null;
                    }
                    else {
                        Log.e("prueba", "Permitido");
                        mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                        mOut.println("test");
                        mOut.flush();
                        return ip;
                    }
                } catch (Exception ex) {
                    return null;
                }finally{
                    try {
                        mOut.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public class MyClientTask extends AsyncTask<Mensaje , Mensaje, Mensaje> {

        private String dstAddress;
        private int dstPort;
        private Mensaje response = new Mensaje();
        private Mensaje msgToServer;

        MyClientTask(String addr, int port, Mensaje msgTo) {
            dstAddress = addr;
            dstPort = port;
            msgToServer = msgTo;
        }

        @Override
        protected void onPostExecute(Mensaje result) {
            super.onPostExecute(result);
        }

        @Override
        protected Mensaje doInBackground(Mensaje... params) {
            Socket socket = null;
            ObjectOutputStream dataOutputStream = null;
            ObjectInputStream dataInputStream = null;

            try {
                socket = new Socket(dstAddress, dstPort);

                dataOutputStream = new ObjectOutputStream(
                        socket.getOutputStream());
                dataInputStream = new ObjectInputStream(socket.getInputStream());

                if(msgToServer != null)
                    dataOutputStream.writeObject(msgToServer);

                response = (Mensaje) dataInputStream.readObject();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {

                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response;
        }
    }
}
