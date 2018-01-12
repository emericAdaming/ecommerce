package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Produit;

@Stateless
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@PersistenceContext(unitName = "eCommerce_V1")
	private EntityManager em;

	// Setter pour l'em

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public LigneCommande ajouterLigneCommande(LigneCommande ligne) {

		// Ajouter la ligne de commande
		em.persist(ligne);

		// Modifier l'état du produit

		// Récupérer le produit
		Produit produitOut = em.find(Produit.class, ligne.getProduit().getIdProduit());

		// Lui attribuer la valeur selectionne:TRUE
		produitOut.setSelectionne(true);

		// Mettre à jour le produit
		em.merge(produitOut);

		return ligne;
	}

	@Override
	public List<LigneCommande> getAllLignes() {
		// Ecrire la requete

		String req = "SELECT l FROM LigneCommande l";

		// Ecriture du query

		Query query = em.createQuery(req);

		// Envoyer la requete et récupérer le resultat

		List<LigneCommande> liste = query.getResultList();

		return liste;
	}

	@Override
	public LigneCommande isExistLigneCommande(LigneCommande ligne) throws Exception {

		// Ecrire la requete
		String req = "SELECT l FROM LigneCommande l WHERE l.produit.idProduit=:pIdProduit";

		// Query
		Query query = em.createQuery(req);

		// Definir les paramètres
		query.setParameter("pIdProduit", ligne.getProduit().getIdProduit());

		// Envoyer la requête et récupérer le resultat

		LigneCommande ligneOut = (LigneCommande) query.getSingleResult();

		return ligneOut;
	}

	@Override
	public LigneCommande updateLigneQte(LigneCommande ligne) {

		// Ecrire la requête pour récupérer la qté d'origine
		String req = "SELECT l.quantite FROM LigneCommande l WHERE l.id=:pIdLigne";

		// Query
		Query query = em.createQuery(req);

		query.setParameter("pIdLigne", ligne.getId());

		// Récupérer la quantité initiale de la ligne de commande
		int qteInit = (int) query.getSingleResult();

		int qteNew = qteInit + ligne.getQuantite();

		// Mettre à jour la ligne de commande

		// Récupérer la ligne de commande
		LigneCommande ligneOut = em.find(LigneCommande.class, ligne.getId());
		ligneOut.setQuantite(ligne.getQuantite() + qteInit);

		em.merge(ligneOut);

		return ligneOut;
	}

	@Override
	public void supprimerLigneCommande(LigneCommande ligne) {

		// Ecrire la requete
		String req = "DELETE FROM LigneCommande AS l WHERE l.id=:pIdLigne";

		// Query
		Query query = em.createQuery(req);

		// Definir les paramètres
		query.setParameter("pIdLigne", ligne.getId());

		// Executer la requete
		query.executeUpdate();

		// Modifier l'état du produit

		// Récupérer le produit
		Produit produitOut = em.find(Produit.class, ligne.getProduit().getIdProduit());

		// Lui attribuer la valeur selectionne:TRUE
		produitOut.setSelectionne(false);

		// Mettre à jour le produit
		em.merge(produitOut);

	}

	@Override
	public LigneCommande quantiteLigneCommande(LigneCommande ligne) {

		// Récupérer la ligne de commande
		LigneCommande ligneOut = em.find(LigneCommande.class, ligne.getId());
		ligneOut.setQuantite(ligne.getQuantite());

		em.merge(ligneOut);

		return ligneOut;
	}

	@Override
	public int totalPanier(List<LigneCommande> listeLignes) {
		int total = 0;

		for (LigneCommande ligne : listeLignes) {
			total = total + ligne.getPrix();
		}

		System.out.println("**************************************TOTAL*" + total);

		return total;
	}

	@Override
	public LigneCommande updateLigneCommande(LigneCommande ligne) {
		// Récupérer la ligne de commande
		LigneCommande ligneOut = em.find(LigneCommande.class, ligne.getId());
//		ligneOut.setPrix(ligne.getPrix());
//		ligneOut.setProduit(ligne.getProduit());
//		ligneOut.setQuantite(ligne.getQuantite());
		ligneOut.setCommande(ligne.getCommande());

		em.merge(ligneOut);

		return ligneOut;
	}

}
