package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;

@Stateless
public class ProduitDaoImpl implements IProduitDao {

	@PersistenceContext(unitName = "eCommerce_V1")
	private EntityManager em;

	// Setter pour l'em

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Produit> getProduitsCategorie(Categorie c) {

		// Ecrire la requête
		String req = "SELECT p FROM Produit p WHERE p.categorie=:pCategorie";

		// Création du query
		Query query = em.createQuery(req);

		// Definition des parametres
		query.setParameter("pCategorie", c);

		// Envoie de la requete et récupération de la liste
		List<Produit> p_list=query.getResultList();		
		for(Produit element:p_list){
			System.out.println("Element trouve :"+element);
		}
		if(p_list==null)
			System.out.println("Aucun produit trouve");

		return p_list;
	}

	@Override
	public List<Produit> getProduitsSelect() {
		// Ecrire la requête
		String req = "SELECT p FROM Produit p WHERE p.selectionne=true";

		// Création du query
		Query query = em.createQuery(req);

		// Envoie de la requete et récupération de la liste
		
		return query.getResultList();
	}

}
