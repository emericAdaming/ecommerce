package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;

@ManagedBean(name="produitMB")
@RequestScoped
public class ProduitManagedBean implements Serializable{

	@EJB
	private IProduitService produitService;
	
	private Produit produit;
	
	private List<Produit> listeProduits;
	
	private Categorie categorie;

	// Constructeur
	
	public ProduitManagedBean() {
		this.produit=new Produit();
	}

	// Getters & Setters
	
	public IProduitService getProduitService() {
		return produitService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	// Méthodes métier
	
	public String getProduitsCategorie(){
		
		System.out.println("Categorie du produit: "+this.categorie);
		
		this.listeProduits=produitService.getProduitsCategorie(this.categorie);
		
		
		
		return "produits";
		
	}
	
public String getProduitsSelect(){
		
		this.listeProduits=produitService.getProduitsSelect();
		
		return null;
		
	}
	
}
