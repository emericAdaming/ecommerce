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
	
	private int quantiteSelect;

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
	
	

	public List<LigneCommande> getListeLignes() {
		return listeLignes;
	}

	public void setListeLignes(List<LigneCommande> listeLignes) {
		this.listeLignes = listeLignes;
	}

	public int getQuantiteSelect() {
		return quantiteSelect;
	}

	public void setQuantiteSelect(int quantiteSelect) {
		this.quantiteSelect = quantiteSelect;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	// Méthodes métiers

	public String ajouterLigneCommande() {
		System.out.println("Ajout vers le panier");
		System.out.println("Ligne commande quantite:="+this.ligne.getQuantite());
		//System.out.println("Ligne commande quantite 2:="+this.ligne.getProduit().getQuantiteSelect());
		System.out.println("Ligne commande quantite 3:="+this.quantiteSelect);

		LigneCommande ligneExist;
		try {
			System.out.println("Debut try 1");
			ligneExist = ligneCommandeService.isExistLigneCommande(this.ligne);

			// Si la ligne existe déja
			System.out.println("Debut 2");
			// On indique la quantité à ajouter
			ligneExist.setQuantite(this.ligne.getQuantite());
			System.out.println("la quantite a ete rajouter");
			// On met à jour la ligne de commande existente
			ligneCommandeService.updateLigneQte(ligneExist);
			System.out.println("REcup nouvelle liste du panier");
			// Récupérer la nouvelle liste à partir de la BDD
			this.listeLignes = ligneCommandeService.getAllLignes();

			// Metre à jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);

			return "produits";

		} catch (Exception e) {
			// Si la ligne de commande n'existe pas
			System.out.println("Ligne de commande inexistante");
			System.out.println(ligne);
			// Ajouter la ligne de commande
			int prix = (int) (this.ligne.getQuantite() * this.ligne.getProduit().getPrix());
			this.ligne.setPrix(prix);
			System.out.println("************"+ligne+"*****************************");
			try{
				this.ligne = ligneCommandeService.ajouterLigneCommande(this.ligne);

				// Récupérer la nouvelle liste à partir de la BDD
				this.listeLignes = ligneCommandeService.getAllLignes();

				// Metre à jour la liste dans la session
				maSession.setAttribute("listeLignes", this.listeLignes);
			}catch(Exception e2){
				System.out.println("%%%%%%%%%%%%%% Erreur dans ligne inexistante %%%%%%%%%%%%%%%");
			}

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
			
			// Récupérer le montant total du panier
			this.total = ligneCommandeService.totalPanier(this.listeLignes);

			return "panier";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La ligne n'a pas pu etre supprimée"));
			return "panier";
		}

	}

	public String getAllLignes() {

		System.out.println("**************************GET ALL LIGNES ********************");
		
		// Récupérer la nouvelle liste à partir de la BDD
		this.listeLignes = ligneCommandeService.getAllLignes();

		// Metre à jour la liste dans la session
		maSession.setAttribute("listeLignes", this.listeLignes);

		System.out.println("**************************LISTE ********************"+this.listeLignes);
				
		// Récupérer le montant total du panier
		this.total = ligneCommandeService.totalPanier(this.listeLignes);

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
