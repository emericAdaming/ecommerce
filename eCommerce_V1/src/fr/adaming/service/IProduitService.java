package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;

@Local
public interface IProduitService {

	public List<Produit> getProduitsCategorie(Categorie c);
	
	public List<Produit> getProduitsSelect();
	
	public Produit addProduit(Produit p);
	
}
