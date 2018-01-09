package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.modele.LigneCommande;

@Local
public interface ILigneCommandeService {

	public LigneCommande ajouterLigneCommande(LigneCommande ligne);
	
	public List<LigneCommande> getAllLignes();
	
	public LigneCommande isExistLigneCommande(LigneCommande ligne) throws Exception;
	
	public LigneCommande updateLigneCommande(LigneCommande ligne);
	
	public void supprimerLigneCommande(LigneCommande ligne);
	
	public LigneCommande quantiteLigneCommande(LigneCommande ligne);
	
}
