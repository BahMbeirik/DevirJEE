package model;

public class Order extends Produit{
	private int orderId;
	private int uId;
	private int quantite;
	private String date;
	
	public Order() {}

	public Order(int orderId, int uId, int quantite, String date) {
		super();
		this.orderId = orderId;
		this.uId = uId;
		this.quantite = quantite;
		this.date = date;
	}

	public Order(int uId, int quantite, String date) {
		super();
		this.uId = uId;
		this.quantite = quantite;
		this.date = date;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}



	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	

}
