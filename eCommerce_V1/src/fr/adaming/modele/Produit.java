package fr.adaming.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="produits")
public class Produit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProduit;
	private String description;
	private String designation;
	private double prix;
	private int quantite;
	private boolean selectionne;
	private byte[] photo;
	
	// Constructeurs
	
	public Produit() {
		super();
	}


	public Produit(Long idProduit, String description, String designation, double prix, int quantite,
			boolean selectionne, byte[] photo) {
		super();
		this.idProduit = idProduit;
		this.description = description;
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}


	public Produit(String description, String designation, double prix, int quantite, boolean selectionne,
			byte[] photo) {
		super();
		this.description = description;
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}


	// Getters & Setters
	
	public Long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	
	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	// toString

	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", description=" + description + ", designation=" + designation
				+ ", prix=" + prix + ", quantite=" + quantite + ", selectionne=" + selectionne + ", photo=" + photo
				+ "]";
	}
	
	
	
	

}