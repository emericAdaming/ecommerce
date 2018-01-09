package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.modele.Categorie;

@Stateful
public class CategorieServiceImpl implements ICategorieService{

	@EJB
	ICategorieDao categorieDao;
	
	@Override
	public List<Categorie> getAllCategories() {
		// TODO Auto-generated method stub
		return categorieDao.getAllCategories();
	}

	public Categorie addCategorie(Categorie c){
		
		return categorieDao.addCategorie(c);
	}

	@Override
	public byte[] getCategorieById(int id_categorie) {

		return categorieDao.getCategorieById(id_categorie);
	}
}
