package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.modele.LigneCommande;

@Local
public interface ILigneCommandeDao {

	public LigneCommande ajouterLigneCommande(LigneCommande ligne);
	
	public List<LigneCommande> getAllLignes();
	
	public LigneCommande isExistLigneCommande(LigneCommande ligne);
	
	public LigneCommande updateLigneCommande(LigneCommande ligne);
	
}
