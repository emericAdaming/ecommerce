package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.modele.Commande;

@Stateless
public class CommandeServiceImpl implements ICommandeService{

	@EJB
	ICommandeDao commandeDao;
	
	@Override
	public Commande ajouterCommande(Commande c) {
		// TODO Auto-generated method stub
		return commandeDao.ajouterCommande(c);
	}

}
