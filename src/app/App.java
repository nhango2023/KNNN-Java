package app;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.User;
import ui.DangNhap;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public User NguoiDung= new User();
	public JMenuItem mntmDangNhap;
	public JLabel lblTenNguoiDung;
	public JMenuItem mntmQuanLySinhVien;
	public JMenuItem mntmQuanLyHoatDong;
	public JMenuItem mntmQuanLyGiangVien;
	public JMenu mnQuanLy;
	public JMenuItem mntmDangXuat;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 629, 22);
		contentPane.add(menuBar);
		
		JMenu mnTaiKhoan = new JMenu("Tài khoản");
		menuBar.add(mnTaiKhoan);
		
		mntmDangNhap = new JMenuItem("Đăng nhập");
		mnTaiKhoan.add(mntmDangNhap);
		
		
		mntmDangXuat = new JMenuItem("Đăng xuất");
		mnTaiKhoan.add(mntmDangXuat);
		mntmDangXuat.setVisible(false);
		
		mnQuanLy = new JMenu("Quản lý");
		menuBar.add(mnQuanLy);
		mnQuanLy.setVisible(false);
		mntmQuanLySinhVien = new JMenuItem("Quản lý sinh viên");
		mnQuanLy.add(mntmQuanLySinhVien);
		
		mntmQuanLyHoatDong = new JMenuItem("Quản lý hoạt động");
		mnQuanLy.add(mntmQuanLyHoatDong);
		
		mntmQuanLyGiangVien = new JMenuItem("Quản lý giảng viên");
		mnQuanLy.add(mntmQuanLyGiangVien);
		
		lblTenNguoiDung = new JLabel("Chưa đăng nhập");
		lblTenNguoiDung.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTenNguoiDung.setBounds(10, 357, 196, 23);
		contentPane.add(lblTenNguoiDung);
		
		mntmDangNhap.addActionListener(e -> {
		    new DangNhap(this);
		});
		mntmDangXuat.addActionListener(e -> {
		    NguoiDung.setMaUser("");
		    NguoiDung.setTenUser("");
		    lblTenNguoiDung.setText("Chưa đăng nhập");
		    mnQuanLy.setVisible(false);
		    mntmDangXuat.setVisible(false);
		    mntmDangNhap.setVisible(true);
		   
		});
		this.setVisible(true);
	}
}
