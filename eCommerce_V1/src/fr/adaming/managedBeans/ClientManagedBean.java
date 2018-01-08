package fr.adaming.managedBeans;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import fr.adaming.modele.Client;
import fr.adaming.service.IClientService;

@ManagedBean(name="clientMB")
@RequestScoped
public class ClientManagedBean {

	@EJB
	private IClientService clientService;
	
	private Client client;

	// Constructeur
	
	public ClientManagedBean() {
		this.client = new Client();
	}
	
	// Getters & Setters

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	// Méthodes métiers
	
	public String ajouterClient(){
		this.client=clientService.ajouterClient(this.client);
		return "ajoutClient";
	}
	
}
