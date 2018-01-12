package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Produit;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name = "ligneCommandeMB")
@RequestScoped
public class LigneCommandeManagedBean implements Serializable {

	@EJB
	ILigneCommandeService ligneCommandeService;

	private LigneCommande ligne;

	private List<LigneCommande> listeLignes;

	private HttpSession maSession;

	private int total;

	// Constructeurs

	public LigneCommandeManagedBean() {
		this.ligne = new LigneCommande();
	}

	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

	}

	// Getters & Setters

	public LigneCommande getLigne() {
		return ligne;
	}

	public void setLigne(LigneCommande ligne) {
		this.ligne = ligne;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	// M�thodes m�tiers

	public String ajouterLigneCommande() {
		System.out.println("Ajout vers le panier");
		System.out.println("Ligne commande quantite:="+this.ligne.getQuantite());
		System.out.println("Ligne commande quantite 2:="+this.ligne.getProduit().getQuantiteSelect());
		if(this.ligne.getQuantite()==0)
			this.ligne.setQuantite(22);
		LigneCommande ligneExist;
		try {
			ligneExist = ligneCommandeService.isExistLigneCommande(this.ligne);

			// Si la ligne existe d�ja

			// On indique la quantit� � ajouter
			ligneExist.setQuantite(this.ligne.getQuantite());
			System.out.println("la quantite a ete rajouter");
			// On met � jour la ligne de commande existente
			ligneCommandeService.updateLigneCommande(ligneExist);
			System.out.println("REcup nouvelle liste du panier");
			// R�cup�rer la nouvelle liste � partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre � jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);

			return "produits";

		} catch (Exception e) {
			// Si la ligne de commande n'existe pas

			// Ajouter la ligne de commande
			int prix = (int) (this.ligne.getQuantite() * this.ligne.getProduit().getPrix());
			this.ligne.setPrix(prix);
			this.ligne = ligneCommandeService.ajouterLigneCommande(this.ligne);

			// R�cup�rer la nouvelle liste � partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre � jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);

			return "produits";
		}

	}

	public String supprimerLigneCommande() {

		try {
			ligneCommandeService.supprimerLigneCommande(this.ligne);

			// R�cup�rer la nouvelle liste � partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre � jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);
			
			// R�cup�rer le montant total du panier
			this.total = ligneCommandeService.totalPanier(this.listeLignes);

			return "panier";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La ligne n'a pas pu etre supprim�e"));
			return "panier";
		}

	}

	public String getAllLignes() {

		System.out.println("**************************GET ALL LIGNES ********************");
		
		// R�cup�rer la nouvelle liste � partir de la BDD
		this.listeLignes = ligneCommandeService.getAllLignes();

		// Metre � jour la liste dans la session
		maSession.setAttribute("listeLignes", this.listeLignes);

		System.out.println("**************************LISTE ********************"+this.listeLignes);
				
		// R�cup�rer le montant total du panier
		this.total = ligneCommandeService.totalPanier(this.listeLignes);

		return "panier";
	}

	public String quantiteLigneCommande() {

		ligneCommandeService.quantiteLigneCommande(this.ligne);

		// R�cup�rer la nouvelle liste � partir de la BDD
		this.listeLignes = ligneCommandeService.getAllLignes();

		// Metre � jour la liste dans la session
		maSession.setAttribute("listeLignes", this.listeLignes);

		return "panier";
	}

}
