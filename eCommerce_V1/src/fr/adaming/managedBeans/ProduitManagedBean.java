package fr.adaming.managedBeans;

import java.io.Serializable;
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
import fr.adaming.service.IProduitService;

@ManagedBean(name = "produitMB")
@SessionScoped
public class ProduitManagedBean implements Serializable {

	@EJB
	private IProduitService produitService;

	private Produit produit;

	private List<Produit> listeProduits;

	private Categorie categorie;

	private String image;
	
	private HttpSession maSession;

	// Constructeur

	public ProduitManagedBean() {
		this.produit = new Produit();
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

	// M�thodes m�tier

	public String getProduitsCategorie() {

		System.out.println("Categorie du produit: " + this.categorie);
		System.out.println(" %%%%%%%%%%%%%%% Categorie du produit: " + this.categorie.getIdCategorie()
				+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		this.listeProduits = produitService.getProduitsCategorie(this.categorie);

		// On ajoute la liste de produits dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeProduits", listeProduits);
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
		produitService.addProduit(produit);

		// R�cup�rer la nouvelle liste � partir de la BDD
		this.listeProduits = produitService.getProduitsCategorie(this.categorie);

		// Metre � jour la liste dans la session
		maSession.setAttribute("listeProduits", this.listeProduits);
		
		produit = null;
		image = null;
		return "accueil";
	}

}
