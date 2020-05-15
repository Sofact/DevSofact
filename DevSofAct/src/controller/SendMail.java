package controller;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo, String adjuntoPath) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = "facturacionraveloalarcon";  //Para la dirección nomcuenta@gmail.com
	    String clave = "CELESTE215922";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
	    
	    MimeMultipart multiParte = null;

	    Session session = Session.getDefaultInstance(props);
	    
	    BodyPart texto = new MimeBodyPart();
	    try {
			texto.setText("P Ravelo Alarcón Se alegra de poder enviar a través de este medio su factura, cumpliendo con las normativas de la Dian y Ayudando al planeta evitando el uso de facturas en papel");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    BodyPart adjunto = new MimeBodyPart();
        try {
			adjunto.setDataHandler(new DataHandler(new FileDataSource(adjuntoPath)));
			adjunto.setFileName(adjuntoPath+".pdf");
			
			  //defino las partes del mensaje
	        multiParte = new MimeMultipart();
	        multiParte.addBodyPart(texto);
	        multiParte.addBodyPart(adjunto);
	        
	        
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //  .setDataHandler(new DataHandler(new FileDataSource("d:/futbol.gif")));
        
        
      
	    
	    MimeMessage message = new MimeMessage(session);

	    try {
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        //message.setText(cuerpo);
	        message.setContent(multiParte);
	        
	       
            
	        
	        Transport transport = session.getTransport("smtp");
	        
			transport.connect("smtp.gmail.com", remitente, clave);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	}

}
