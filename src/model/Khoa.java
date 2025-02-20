package model;

public class Khoa {
    private String maKhoa;
    private String tenKhoa;

    public Khoa(String maKhoa, String tenKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    @Override
    public String toString() {
        return tenKhoa; // Display tenKhoa in JComboBox
    }
}

