package model;

import java.io.Serializable;

public class Stock implements Serializable{
		protected int id;
		protected String idP;
		protected String quantite;
		private String productName;
		
	
		public Stock() {
			
		}


		public Stock(String idP, String quantite) {
			super();
			this.idP = idP;
			this.quantite = quantite;
		}


		public Stock(int id, String idP, String quantite) {
			super();
			this.id = id;
			this.idP = idP;
			this.quantite = quantite;
		}


		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getIdP() {
			return idP;
		}


		public void setIdP(String i) {
			this.idP = i;
		}


		public String getQuantite() {
			return quantite;
		}


		public void setQuantite(String quantite) {
			this.quantite = quantite;
		}


		public String getProductName() {
			return productName;
		}


		public void setProductName(String productName) {
			this.productName = productName;
		}
		
		

}
