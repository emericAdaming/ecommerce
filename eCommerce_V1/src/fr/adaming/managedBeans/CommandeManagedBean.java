package fr.adaming.managedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
import fr.adaming.service.CommandeServiceImpl;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name = "commandeMB")
@RequestScoped
public class CommandeManagedBean {

	@EJB
	ICommandeService commandeService;

	@EJB
	ILigneCommandeService ligneCommandeService;

	@EJB
	IClientService clientService;

	private Commande commande;
	private Client client;

	private HttpSession maSession;

	// Constructeur

	public CommandeManagedBean() {
		this.commande = new Commande();
		this.client = new Client();
	}

	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

	}

	// Getters & Setters

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	// M�thodes metiers

	public String ajouterCommande() {
		System.out.println("********************AJOUTER COMMANDE*************");

		// Ajouter le client
		this.client = clientService.ajouterClient(this.client);

		commande.setClient(this.client);

		// Ajouter la date
		String format = "dd/MM/yy H:mm:ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(format);
		java.util.Date date = new java.util.Date();

		commande.setDateCommande(date);
		// Attribuer le N� de la commande aux lignes de commandes

		System.out.println("********************" + maSession.getAttribute("listeLignes"));

		List<LigneCommande> liste = (List<LigneCommande>) maSession.getAttribute("listeLignes");

		// Ajouter la commande � la BDD
		this.commande = commandeService.ajouterCommande(this.commande);

		for (LigneCommande ligneCommande : liste) {
			ligneCommande.setCommande(this.commande);
			ligneCommandeService.updateLigneCommande(ligneCommande);
		}

		return "commande";
	}

}
