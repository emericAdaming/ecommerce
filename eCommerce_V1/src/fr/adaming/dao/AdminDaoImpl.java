package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.modele.Admin;

@Stateless
public class AdminDaoImpl implements IAdminDao {

	@PersistenceContext(unitName="eCommerce_V1")
	EntityManager em;

	@Override
	public Admin isExist(Admin admin) throws Exception {
		// Pas besoin de cr�er l'emf, l'em et d'ouvrir la tx

		// Construire directement la requete JPQL
		String req = "SELECT a FROM Admin AS a WHERE a.mail=:pMail AND a.mdp=:pMdp";

		// Cr�ation d'un Query
		Query query = em.createQuery(req);

		// Passage des param�tres
		query.setParameter("pMail", admin.getMail());
		query.setParameter("pMdp", admin.getMdp());

		// Envoyer la requ�te et r�cup�rer l'agent
		Admin adminOut = (Admin) query.getSingleResult();

		return adminOut;

	}

}
