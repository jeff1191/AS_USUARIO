package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
*/

// En el gradle hay que poner
// compile 'com.google.apis:google-api-services-gmail:v1-rev39-1.21.0'
// Hablan de unas movidas de registrarse y no veo muy sencillo lo de logearse

/**
 * Created by Juan Lu on 18/03/2016.
 */
public class Correo extends Activity{

    private String user; // correo nuestro desde el cual se envia
    private String pass; // contraseña de nuestro correo

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        //Aqui es desde donde se tiene que lanzar el correo
        //El intent q llega debe tener las cosas para enviarlo
        //String destinatario = bundle.getString("destinatario");
        //String titulo = bundle.getString("titulo");
        //String texto = bundle.getString("texto");
        user = "noreplyASUsuario@gmail.com"; // nuestro correo
        pass = "prinCERV"; // nuestro email

        Session sesionEmail = createSesionObject();
        try {
            Message message = createMessage("juanluar@ucm.es", "PrimeraPrueba", "Parece que esto marcha", sesionEmail);
            // En un ejemplo lo ejecutaba en otro hilo a traves del ASYNCTASK
            //new SendMailTask().execute(message);
            Transport.send(message);
            Log.e("pruebaEmail", "Mensaje enviado con exito....");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //Establece los parametros para la conexion
    private Session createSesionObject(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session)
            throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        // poner nuestras credenciales
        message.setFrom(new InternetAddress("noreplyASUsuario@gmail.com", "Princesas cerveceras"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }


    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to Email address of the receiver.
     * @param from Email address of the sender, the mailbox account.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
   /* public static MimeMessage createEmail(String to, String from, String subject,
                                          String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        InternetAddress tAddress = new InternetAddress(to);
        InternetAddress fAddress = new InternetAddress(from);

        email.setFrom(fAddress);
        email.addRecipient(javax.mail.Message.RecipientType.TO,tAddress);
        email.setSubject(subject);
        email.setText(bodyText);

        return email;
    }*/

    /**
     * Create a Message from an email
     *
     * @param email Email to be set to raw of message
     * @return Message containing base64url encoded email.
     * @throws IOException
     * @throws MessagingException
     */
   /* public static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }*/


    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param email Email to be sent.
     * @throws MessagingException
     * @throws IOException
     */
    /*public static void sendMessage(Gmail service, String userId, MimeMessage email)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(email);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
    }*/



}

