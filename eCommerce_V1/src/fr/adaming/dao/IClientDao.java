package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.modele.Client;

@Local
public interface IClientDao {

	public Client ajouterClient(Client c);
	
}
