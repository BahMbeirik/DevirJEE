package model;

import java.io.Serializable;

public class Produit implements Serializable{
	protected int id;
	protected String name;
	protected double prix;
	protected String imagePath; // حقل لمسار الصورة
	
	public Produit() {
		// TODO Auto-generated constructor stub
	}
	
	public Produit(String name, double prix) {
		super();
		this.name = name;
		this.prix = prix;
	}

	public Produit(int id, String name, double prix) {
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
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
	
	
}
