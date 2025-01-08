package model;

public class User {
	private String MaUser;
	private String TenUser;
	public User () {
		MaUser = "";
		TenUser="";
	}
	public String getMaUser() {
		return MaUser;
	}
	public void setMaUser(String maUser) {
		MaUser = maUser;
	}
	public String getTenUser() {
		return TenUser;
	}
	public void setTenUser(String tenUser) {
		TenUser = tenUser;
	}
}
