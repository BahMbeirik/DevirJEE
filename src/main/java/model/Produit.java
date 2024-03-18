package model;

import java.io.Serializable;

public class Produit implements Serializable{
	protected int id;
	protected String name;
	protected String prix;
	public Produit() {
		// TODO Auto-generated constructor stub
	}
	
	public Produit(String name, String prix) {
		super();
		this.name = name;
		this.prix = prix;
	}

	public Produit(int id, String name, String prix) {
		super();
		this.id = id;
		this.name = name;
		this.prix = prix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrix() {
		return prix;
	}
	public void setPrix(String prix) {
		this.prix = prix;
	}
	
	
}
