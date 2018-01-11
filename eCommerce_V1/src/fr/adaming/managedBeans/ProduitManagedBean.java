package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.context.FacesContext;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "produitMB")
@SessionScoped
public class ProduitManagedBean implements Serializable {

	@EJB
	private IProduitService produitService;

	@EJB
	private ICategorieService categorieService;

	private Produit produit;

	private List<Produit> listeProduits;

	private Categorie categorie;

	private String image;

	private HttpSession maSession;

	// Constructeur

	public ProduitManagedBean() {
		this.produit = new Produit();
		this.categorie = new Categorie();
	}

	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// Méthodes métier

	public String getProduitsCategorie() {

		System.out.println("Categorie du produit: " + this.categorie);
		System.out.println(" %%%%%%%%%%%%%%% Categorie du produit: " + this.categorie.getIdCategorie()
				+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		List<Produit>   listOut = produitService.getProduitsCategorie(this.categorie);
		this.listeProduits=new ArrayList<Produit>();
		
		// On ajoute la liste de produits dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduits);
		
		for(Produit element:listOut){
			if(element.getPhoto() == null){
				element.setImage(null);
			}else{
				element.setImage("data:image/png;base64,"+Base64.encodeBase64String(element.getPhoto()));
			}
			this.listeProduits.add(element);
		}
		
		
		
		return "produits";

	}

	public String getProduitsSelect() {
		System.out.println("Acces panier");
		this.listeProduits = produitService.getProduitsSelect();
		listeProduits.forEach(System.out::println);

		return "panier";

	}

	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		byte[] contents = uploadedFile.getContents();
		produit.setPhoto(contents);
		image = "data:image/png;base64," + Base64.encodeBase64String(contents);
	}

	public String ajouterProduit() {
		System.out.println("Enregistrement Produit");

		// Récupérer la categorie a partir du nom

		Categorie categorieByName = categorieService.getCategorieByName(this.categorie);

		// Attribuer la catégorie au produit à ajouter
		this.produit.setCategorie(categorieByName);

		// Ajouter le produit
		produitService.addProduit(this.produit);

		// Récupérer la nouvelle liste à partir de la BDD
		this.listeProduits = produitService.getProduitsCategorie(categorieByName);

		// Metre à jour la liste dans la session
		maSession.setAttribute("listeProduits", this.listeProduits);

		produit = new Produit();
		image = "";
		return "accueil";
	}

	public String supprimerProduit() {

		System.out.println("***************SUPPRIMER PRODUIT****************");
		System.out.println(this.produit);
		
		// Supprimer le produit
		produitService.deleteProduit(this.produit);

		System.out.println(this.categorie);
		
		// Récupérer la nouvelle liste à partir de la BDD
		this.listeProduits = produitService.getProduitsCategorie(this.categorie);

		// Metre à jour la liste dans la session
		maSession.setAttribute("listeProduits", this.listeProduits);

		produit = new Produit();
		image = "";
		return "accueil";

	}

	public String updateProduit() {

		// Récupérer la categorie a partir du nom

		Categorie categorieByName = categorieService.getCategorieByName(this.categorie);

		// Attribuer la catégorie au produit à ajouter
		this.produit.setCategorie(categorieByName);

		// Modifier le produit
		produitService.updateProduit(this.produit);

		// Récupérer la nouvelle liste à partir de la BDD
		this.listeProduits = produitService.getProduitsCategorie(categorieByName);

		// Metre à jour la liste dans la session
		maSession.setAttribute("listeProduits", this.listeProduits);

		produit = new Produit();
		image = "";
		return "accueil";
	}
}
