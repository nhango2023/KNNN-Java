package model;

public class SinhVien {
    private String maSinhVien;
    private String tenSinhVien;

    public SinhVien(String maSinhVien, String tenSinhVien) {
		super();
		this.maSinhVien = maSinhVien;
		this.tenSinhVien = tenSinhVien;
	}



    public String getMaSinhVien() {
		return maSinhVien;
	}



	public void setMaSinhVien(String maSinhVien) {
		this.maSinhVien = maSinhVien;
	}



	public String getTenSinhVien() {
		return tenSinhVien;
	}



	public void setTenSinhVien(String tenSinhVien) {
		this.tenSinhVien = tenSinhVien;
	}



	@Override
    public String toString() {
        return tenSinhVien; // Display tenLop in JComboBox
    }
}
