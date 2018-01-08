package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.modele.Client;

@Local
public interface IClientService {

	public Client ajouterClient(Client c);
	
}
