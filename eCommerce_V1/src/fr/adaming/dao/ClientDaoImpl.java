package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.adaming.modele.Client;

@Stateless
public class ClientDaoImpl implements IClientDao {

	@PersistenceContext(unitName="eCommerce_V1")
	private EntityManager em;
	
	// Setter pour l'injection de la dépendance
	public void setEm(EntityManager em) {
		this.em = em;
	}


	@Override
	public Client ajouterClient(Client c) {
		
		// Ajouter un client à la BDD
		em.persist(c); // Le client rentré est sans ID
		
		return c; // Le client retourné est avec ID
	}

}
