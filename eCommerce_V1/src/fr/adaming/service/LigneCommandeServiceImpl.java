package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.modele.LigneCommande;

@Stateless
public class LigneCommandeServiceImpl implements ILigneCommandeService{

	@EJB
	ILigneCommandeDao ligneServiceDao;
	
	@Override
	public LigneCommande ajouterLigneCommande(LigneCommande ligne) {
		// TODO Auto-generated method stub
		return ligneServiceDao.ajouterLigneCommande(ligne);
	}

	@Override
	public List<LigneCommande> getAllLignes() {
		// TODO Auto-generated method stub
		return ligneServiceDao.getAllLignes();
	}

	@Override
	public LigneCommande isExistLigneCommande(LigneCommande ligne) throws Exception {
		// TODO Auto-generated method stub
		return ligneServiceDao.isExistLigneCommande(ligne);
	}

	@Override
	public LigneCommande updateLigneCommande(LigneCommande ligne) {
		// TODO Auto-generated method stub
		return ligneServiceDao.updateLigneCommande(ligne);
	}

	@Override
	public void supprimerLigneCommande(LigneCommande ligne) {
		// TODO Auto-generated method stub
		ligneServiceDao.supprimerLigneCommande(ligne);
	}

	@Override
	public LigneCommande quantiteLigneCommande(LigneCommande ligne) {
		// TODO Auto-generated method stub
		return ligneServiceDao.quantiteLigneCommande(ligne);
	}

	
	
}
