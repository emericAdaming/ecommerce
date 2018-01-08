package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import fr.adaming.modele.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name="categorieMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	@EJB
	private ICategorieService categorieService;
	
	private Categorie categorie;
	
	private List<Categorie> listeCategories;

	// Constructeur
	
	public CategorieManagedBean() {
		this.categorie=new Categorie();
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
	
	@PostConstruct
	public void init(){
		getAllCategories();
	}
	
	// Méthodes métiers
	
	public void getAllCategories(){
		
		System.out.println("GET ALL CATEGORIES");
		this.listeCategories=categorieService.getAllCategories();
		listeCategories.forEach(System.out::println);
	}
	
}
