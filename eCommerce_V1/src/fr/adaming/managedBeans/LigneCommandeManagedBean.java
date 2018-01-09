package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Produit;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name="ligneCommandeMB")
@RequestScoped
public class LigneCommandeManagedBean implements Serializable{

	@EJB
	ILigneCommandeService ligneCommandeService;
	
	private LigneCommande ligne;
	
	private List<LigneCommande> listeLignes;
	
	private HttpSession maSession;

	// Constructeurs
	
	public LigneCommandeManagedBean() {
		this.ligne=new LigneCommande();
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
	
	public String ajouterLigneCommande(){
		System.out.println("--------------------------********* Ajouter LIGNE COMMANDE *********--------------------------");

		LigneCommande ligneExist=ligneCommandeService.isExistLigneCommande(this.ligne);
		
		if (ligneExist==null) { // Si la ligne de commande n'existe pas
			
			// Ajouter la ligne de commande
			int prix=(int) (this.ligne.getQuantite()*this.ligne.getProduit().getPrix());
			this.ligne.setPrix(prix);
			this.ligne=ligneCommandeService.ajouterLigneCommande(this.ligne);
			
			// Récupérer la nouvelle liste à partir de la BDD
			this.listeLignes=ligneCommandeService.getAllLignes();
			
			// Metre à jour la liste dans la session
			maSession.setAttribute("listeLignes", this.listeLignes);
			
			return "panier";
		} else { // Si la ligne existe déja
			
			ligneCommandeService.updateLigneCommande(ligneExist);
			
			return "produits";
		}
		
	}
	
	
}
