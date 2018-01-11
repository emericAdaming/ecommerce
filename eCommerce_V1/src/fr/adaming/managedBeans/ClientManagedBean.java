package fr.adaming.managedBeans;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.activation.DataHandler;
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
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.ImageIcon;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

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
		
		
		try {
		System.out.println("!!!!!!!!!!!!!Creation pdf!!!!!!!!!!!!!!!!!!!!");
			creationPDF();
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
					InternetAddress.parse("eme.guibert49@gmail.com"));
			System.out.println("!!!!!!!! Destinataire et envoteur done !!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			message.setSubject("Sujet du mail");
			message.setText("Salut  !!!!!," +
					"\n\n Regarde le pdf !!"+
					"\n\n Cordialment! \n l'equipe ecommerce");
			System.out.println("!!!!!!!!!!!!!! Piece jointe !!!!!!!!!!!!!!!!!!!!!!!!!!");
			MimeMultipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.attachFile(new File("C:/Users/emegu/Documents/Test4.pdf"));
				/*ByteArrayDataSource ds = new ByteArrayDataSource(); 
				mbp1.setDataHandler(new DataHandler(ds));
				mbp1.setFileName("Test.pdf");*/
		    mp.addBodyPart(mbp1);
			message.setContent(mp);
			System.out.println("!!!!!!!! Debut transport !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		
		return "accueil";
	}
	

	
	public void creationPDF() throws DocumentException, IOException {
				String chemin= "C:/Users/emegu/Documents/Test4.pdf"; 
			 // step 1
		    	// Using a custom page size
		        Rectangle pagesize = new Rectangle(216f, 720f);
		        Document document = new Document();
		        // step 2
		        PdfWriter.getInstance(document, new FileOutputStream(chemin));
		        // step 3
		        document.open();
		        Image icon = Image.getInstance("C:/Users/emegu/Pictures/chauve.jpg");
		        // step 4
		       // document.addCreationDate();
		        document.add(new Paragraph(
		            "\t Hello People! \n" +
		            "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv!"));
		        document.add(icon);
		        // step 5
		        document.close();
		
		
	}
	
}