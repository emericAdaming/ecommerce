package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.modele.Categorie;

@Stateless
public class CategorieDaoImpl implements ICategorieDao {
	
	@PersistenceContext(unitName="eCommerce_V1")
	private EntityManager em;

	// Setter pour l'entity Manager
	public void setEm(EntityManager em) {
		this.em = em;
	}
	

	@Override
	public List<Categorie> getAllCategories() {
		
		// Ecrire la requete 
		String req="SELECT c FROM Categorie c";
		
		// On crée la query
		Query query=em.createQuery(req);
		
		// Envoyer la requete et recuperer la liste
		List<Categorie> liste=query.getResultList();
		
		return liste;
	}
	
	

}
