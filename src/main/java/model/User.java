package model;

public class User {
	protected int id;
	protected String username;
	protected String password;
	protected String uemail;
	protected String umobile;
	protected String role;
	
	public User() {
	}
	
	public User(String username,String password, String uemail,String umobile,String role) {
		super();
		this.username = username;
		this.password = password;
		this.uemail = uemail;
		this.umobile = umobile;
		this.role = role;
		
	}

	public User(int id, String username,String password, String uemail,String umobile,String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.uemail = uemail;
		this.umobile = umobile;
		this.role = role;
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getUmobile() {
		return umobile;
	}

	public void setUmobile(String umobile) {
		this.umobile = umobile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
