package model;

public class GiangVien {
    private String maGiangVien;
    private String tenGiangVien;

    public GiangVien(String maGiangVien, String tenGiangVien) {
        this.maGiangVien = maGiangVien;
        this.tenGiangVien = tenGiangVien;
    }

    public String getMaGiangVien() {
        return maGiangVien;
    }

    public String getTenGiangVien() {
        return tenGiangVien;
    }

    @Override
    public String toString() {
        return tenGiangVien; // Display tenGiangVien in JComboBox
    }
}