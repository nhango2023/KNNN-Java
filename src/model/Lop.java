package model;

public class Lop {
    private String maLop;
    private String tenLop;

    public Lop(String maLop, String tenLop) {
        this.maLop = maLop;
        this.tenLop = tenLop;
    }

    public String getMaLop() {
        return maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    @Override
    public String toString() {
        return tenLop; // Display tenLop in JComboBox
    }
}

