package fr.adaming.managedBeans;


import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import fr.adaming.modele.Client;
import fr.adaming.service.IClientService;

@ManagedBean(name="clientMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	@EJB
	private IClientService clientService;
	
	private Client client;

	// Constructeur
	
	public ClientManagedBean() {
		this.client = new Client();
	}
	
	// Getters & Setters

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	// Méthodes métiers
	
	public String ajouterClient(){
		this.client=clientService.ajouterClient(this.client);
		return "ajoutClient";
	}
	
	
	
	class GMailAuthenticator extends Authenticator {
	     String user;
	     String pw;
	     public GMailAuthenticator (String username, String password)
	     {
	        super();
	        this.user = username;
	        this.pw = password;
	     }
	    public PasswordAuthentication getPasswordAuthentication()
	    {
	       return new PasswordAuthentication(user, pw);
	    }
	}
	
	public String sendMail(){
		System.out.println("!!!!!!!!!!!!!!!!!!! ENVOIE MAIL !!!!!!!!!!!!!!!!!!!!!!!!");
		// Creation protocole
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.setProperty("mail.smtp.starttls.enable", "true");
		System.out.println("!!!!!!!!!!!!! Debut creation session !!!!!!!!!!!!!!!!!!!");
		// 1 -> Création de la session
		Session session = Session.getInstance(props, new GMailAuthenticator("ecommerce44000@gmail.com", "adaming44000"));
		System.out.println("!!!!!!!!! Session cree !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		try {

			Message message = new MimeMessage(session);
			System.out.println("!!!!!!!! Message session cree !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			message.setFrom(new InternetAddress("ecommerce44000@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("alexandre.cocagne@gmail.com"));
			System.out.println("!!!!!!!! Destinataire et envoteur done !!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler," +
					"\n\n No spam to my email, please!");
			System.out.println("!!!!!!!! Debut transport !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		return "login";
	}
	
}
