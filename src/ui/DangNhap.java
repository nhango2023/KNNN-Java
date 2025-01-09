package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.App;
import database.database;
import model.User;

public class DangNhap extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnExit;
	private User NguoiDung;
	private database db = new database();
	private String sql;

	public DangNhap(App app) {
		this.NguoiDung = NguoiDung;
		setTitle("Đăng nhập hệ thống");
		setSize(800, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel backgroundPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon backgroundIcon = new ImageIcon("src/images/login.jpg");
				Image backgroundImage = backgroundIcon.getImage();
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		};
		backgroundPanel.setLayout(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		formPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel lblUsername = new JLabel("Tên đăng nhập:");
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 18));
		lblUsername.setForeground(Color.BLACK);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		formPanel.add(lblUsername, gbc);

		txtUsername = new JTextField(20);
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		formPanel.add(txtUsername, gbc);

		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPassword.setForeground(Color.BLACK);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		formPanel.add(lblPassword, gbc);

		txtPassword = new JPasswordField(20);
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		formPanel.add(txtPassword, gbc);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		btnLogin = new JButton("Đăng nhập");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
		btnLogin.setBackground(new Color(0, 153, 51));
		btnLogin.setForeground(Color.WHITE);
		buttonPanel.add(btnLogin);

		btnExit = new JButton("Thoát");
		btnExit.setFont(new Font("Arial", Font.BOLD, 18));
		btnExit.setBackground(new Color(204, 0, 0));
		btnExit.setForeground(Color.WHITE);
		buttonPanel.add(btnExit);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		formPanel.add(buttonPanel, gbc);

		backgroundPanel.add(formPanel);
		add(backgroundPanel);

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				String password = new String(txtPassword.getPassword());
				sql = String.format("CALL DangNhap('%s', '%s')", username, password);
				try {
					db.connect();
					ResultSet rs = db.Excute(sql);
					if (rs != null && rs.next()) {
						JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						String maLoaiTaiKhoan = rs.getString("MALOAITAIKHOAN");
						String maTaiKhoan = rs.getString("MATAIKHOAN");
						if (maLoaiTaiKhoan.equals("LTK001")) {
							sql = String.format("CALL AdminDangDangNhap('%s')", maTaiKhoan);
						} else if (maLoaiTaiKhoan.equals("LTK002")) {
							sql = String.format("CALL GiangVienDangDangNhap('%s')", maTaiKhoan);
						} else if (maLoaiTaiKhoan.equals("LTK003")) {
							sql = String.format("CALL SinhVienDangDangNhap('%s')", maTaiKhoan);
						}
						rs = db.Excute(sql);
						String maNguoiDung = "";
						String tenNguoiDung = "";
						app.mnQuanLy.setVisible(true);
						if (rs != null && rs.next()) {
						    if (maLoaiTaiKhoan.equals("LTK001")) {
						        maNguoiDung = rs.getString("MAADMIN");
						        tenNguoiDung = rs.getString("TEN");
						    } else if (maLoaiTaiKhoan.equals("LTK002")) {
						        maNguoiDung = rs.getString("MAGIANGVIEN");
						        tenNguoiDung = rs.getString("TENGIANGVIEN");
						        app.mntmQuanLyGiangVien.setVisible(false);
						        app.mntmQuanLySinhVien.setVisible(false);
						    } else if (maLoaiTaiKhoan.equals("LTK003")) {
						        maNguoiDung = rs.getString("MASINHVIEN");
						        tenNguoiDung = rs.getString("TENSINHVIEN");
						        app.mnQuanLy.setVisible(false);
						        app.mnHoatDong.setVisible(true);
						    }
						}
						db.close();
						
						app.mntmDangXuat.setVisible(true);
						app.mntmDangNhap.setVisible(false);
						app.NguoiDung.setMaUser(maNguoiDung);
						app.NguoiDung.setTenUser(tenNguoiDung);
						app.lblTenNguoiDung.setText(tenNguoiDung);
						
						dispose();

					} else {
						JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						db.close();
					}

				} catch (SQLException error) {
					error.printStackTrace();
					JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});

		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		this.setVisible(true);
	}

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                LoginForm loginForm = new LoginForm();
//                loginForm.setVisible(true);
//            }
//        });
//    }
}
