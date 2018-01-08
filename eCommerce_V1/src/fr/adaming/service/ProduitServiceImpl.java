package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;

import fr.adaming.dao.IProduitDao;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;

@Stateful
public class ProduitServiceImpl implements IProduitService{

	@EJB
	IProduitDao produitDao;

	@Override
	public List<Produit> getProduitsCategorie(Categorie c) {
		// TODO Auto-generated method stub
		return produitDao.getProduitsCategorie(c);
	}

	@Override
	public List<Produit> getProduitsSelect() {
		// TODO Auto-generated method stub
		return produitDao.getProduitsSelect();
	}
	
}
