package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.modele.Commande;

@Local
public interface ICommandeDao {
	
	public Commande ajouterCommande(Commande c);

}
