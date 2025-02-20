package model;

public class User {
	private String MaUser;
	private String TenUser;
	private String LoaiUser;	
	public String getLoaiUser() {
		return LoaiUser;
	}
	public void setLoaiUser(String loaiUser) {
		LoaiUser = loaiUser;
	}
	public User () {
		MaUser = "";
		TenUser="";
		LoaiUser="";
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
