package fr.adaming.managedBeans;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.JFileChooser;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.awt.*;

import fr.adaming.modele.Categorie;
import fr.adaming.service.ICategorieService;
import sun.misc.IOUtils;

@ManagedBean(name = "categorieMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	@EJB
	private ICategorieService categorieService;

	private Categorie categorie;
	private List<String> categorie_designation;
	private String recherche;

	private UploadedFile file;

	private List<Categorie> listeCategories; // permet d'entree une liste de
												// toutes les categories
												// existances dans une liste
												// deroulante

	private String image;

	// Constructeur

	public CategorieManagedBean() {
		this.categorie = new Categorie();
	}

	// Getters & Setteres

	public ICategorieService getCategorieService() {
		return categorieService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Categorie> getListeCategories() {
		return listeCategories;
	}

	public void setListeCategories(List<Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
		// AfficheIMG();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getCategorie_designation() {
		return categorie_designation;
	}

	public void setCategorie_designation(List<String> categorie_designation) {
		this.categorie_designation = categorie_designation;
	}

	public String getRecherche() {
		return recherche;
	}

	public void setRecherche(String recherche) {
		this.recherche = recherche;
	}

	// ***************************************************************************************************************

	@PostConstruct
	public void init() {
		getAllCategories();
		getDesignationList();
	}

	// M�thodes m�tiers
	public String ajouterCategorie() {
		System.out.println("Enregistrement image");

		// Ajouter la cat�gorie
		categorieService.addCategorie(categorie);
		categorie = new Categorie();
		image = " ";

		// R�cup�rer les categories
		getAllCategories();

		return "accueil";
	}

	// M�thodes m�tiers
	public String modifierCategorie() {
		System.out.println("Enregistrement image");

		// Modifier la cat�gorie
		categorieService.updateCategorie(categorie);
		categorie = new Categorie();
		image = " ";

		// R�cup�rer les categories
		getAllCategories();

		return "accueil";
	}

	public String supprimerCategorie() {
		System.out.println("********************SUPPRIMER CAT*******************");
		System.out.println(this.categorie);

		// Supprimer la cat�gorie
		categorieService.deleteCategorie(this.categorie);

		categorie = new Categorie();
		image = " ";

		// R�cup�rer les categories
		getAllCategories();

		return "accueil";
	}

	public void getAllCategories() {

		System.out.println("GET ALL CATEGORIES");
		List<Categorie> listOut = categorieService.getAllCategories();
		this.listeCategories = new ArrayList<Categorie>();

		for (Categorie element : listOut) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
			}
			this.listeCategories.add(element);
		}

	}

	// Cette methode sert a transformer une image UploadedFile en byte array
	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();

		// recuperer contenu de l'image en byte array (pixels)
		byte[] contents = uploadedFile.getContents();
		categorie.setPhoto(contents);

		// Transforme byte array en string (format base64)
		image = "data:image/png;base64," + Base64.encodeBase64String(contents);
	}

	public void recupererCategorie() {

		byte[] p = categorieService.getCategorieById(2);
		System.out.println("Recup categorie photo -> image !!!!!");
		image = "data:image/png;base64," + Base64.encodeBase64String(p);
	}

	public void getDesignationList() {
		categorie_designation = new ArrayList<String>();
		for (Categorie element : listeCategories) {
			if (element.getNomCategorie() != null)
				categorie_designation.add(element.getNomCategorie());
		}

	}

	public void rechercheCategorie() {
		System.out.println("Recherche ajax");
		List<Categorie> list_filtre = new ArrayList<Categorie>();
		List<Categorie> list_filtre_image = new ArrayList<Categorie>();
		listeCategories = categorieService.getAllCategories();

		// on garde seulement les categorie filtrees
		for (Categorie element : listeCategories) {
			if (element.getDescription().startsWith(recherche)) {
				list_filtre.add(element);
			}
		}
		// Rempli les images a afficher
		for (Categorie element : list_filtre) {
			if (element.getPhoto() == null) {
				element.setImage(null);
			} else {
				element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
			}
			list_filtre_image.add(element);
		}
		listeCategories = list_filtre_image;
	}

}
