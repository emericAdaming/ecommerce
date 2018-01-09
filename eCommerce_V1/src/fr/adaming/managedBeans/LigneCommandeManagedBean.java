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

	// Méthodes métiers

	public String ajouterLigneCommande() {

		LigneCommande ligneExist;
		try {
			ligneExist = ligneCommandeService.isExistLigneCommande(this.ligne);

			// Si la ligne existe déja

			// On indique la quantité à ajouter
			ligneExist.setQuantite(this.ligne.getQuantite());

			// On met à jour la ligne de commande existente
			ligneCommandeService.updateLigneCommande(ligneExist);

			// Récupérer la nouvelle liste à partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre à jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);

			return "produits";

		} catch (Exception e) {
			// Si la ligne de commande n'existe pas

			// Ajouter la ligne de commande
			int prix = (int) (this.ligne.getQuantite() * this.ligne.getProduit().getPrix());
			this.ligne.setPrix(prix);
			this.ligne = ligneCommandeService.ajouterLigneCommande(this.ligne);

			// Récupérer la nouvelle liste à partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre à jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);

			return "produits";
		}

	}

	public String supprimerLigneCommande() {

		try {
			ligneCommandeService.supprimerLigneCommande(this.ligne);

			// Récupérer la nouvelle liste à partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre à jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);

			return "panier";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La ligne n'a pas pu etre supprimée"));
			return "panier";
		}

	}

	public String getAllLignes() {
		// Récupérer la nouvelle liste à partir de la BDD
		this.listeLignes = ligneCommandeService.getAllLignes();

		// Metre à jour la liste dans la session
		maSession.setAttribute("listeLignes", this.listeLignes);

		return "panier";
	}

	public String quantiteLigneCommande() {

		ligneCommandeService.quantiteLigneCommande(this.ligne);

		// Récupérer la nouvelle liste à partir de la BDD
		this.listeLignes = ligneCommandeService.getAllLignes();

		// Metre à jour la liste dans la session
		maSession.setAttribute("listeLignes", this.listeLignes);

		return "panier";
	}

}
