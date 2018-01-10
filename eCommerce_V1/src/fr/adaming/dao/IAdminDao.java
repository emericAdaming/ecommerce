package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.modele.Admin;

@Local
public interface IAdminDao {

	public Admin isExist(Admin admin) throws Exception;
	
}
