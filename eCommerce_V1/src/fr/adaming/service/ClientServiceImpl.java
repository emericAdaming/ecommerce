package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.IClientDao;
import fr.adaming.modele.Client;

@Stateful
public class ClientServiceImpl implements IClientService{

	@EJB
	private IClientDao clientDao;
	
	@Override
	public Client ajouterClient(Client c) {
		// TODO Auto-generated method stub
		return clientDao.ajouterClient(c);
	}

}
