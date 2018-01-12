package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.modele.Commande;

@Local
public interface ICommandeService {

	public Commande ajouterCommande(Commande c);
	
}
