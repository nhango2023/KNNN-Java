package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.database;
import helper.Id;

public class QuanLyTaiKhoan extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTenDangNhap;
	private JPasswordField  tfMatKhau;
	private final ButtonGroup groupLoaiTaiKhoan = new ButtonGroup();
	private database db = new database();
	private JTable tbTaiKhoan;
	private DefaultTableModel model;
	private Map<String, String> cbbMapData = new HashMap<>();
	private JComboBox cbb;
	private String hanhDong = "";
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLuu;
	private JButton btnHuy;
	private Id id = new Id();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyTaiKhoan frame = new QuanLyTaiKhoan();
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

	public QuanLyTaiKhoan (String loaiTaiKhoan, String maTaiKhoan, String maNguoiSoHuu) {
		init();
		if (loaiTaiKhoan.equals("LTK002")) {
			
            for (int i = 0; i < tbTaiKhoan.getRowCount(); i++) {
                if (tbTaiKhoan.getValueAt(i, 0).equals(maTaiKhoan)) {
                    // Select the row
                	
                	tbTaiKhoan.setRowSelectionInterval(i, i);

                    // Scroll to the selected row
                	tbTaiKhoan.scrollRectToVisible(tbTaiKhoan.getCellRect(i, 0, true));
                    break;
                }else if(tbTaiKhoan.getValueAt(i, 3).equals(maNguoiSoHuu)) {
                	JOptionPane.showMessageDialog(null, "Chưa có tài khoản");
                	tbTaiKhoan.setRowSelectionInterval(i, i);              	
                    // Scroll to the selected row
                	tbTaiKhoan.scrollRectToVisible(tbTaiKhoan.getCellRect(i, 0, true));               	
                    break;
                }
            }
		}
		else if(loaiTaiKhoan.equals("LTK003")) {
			loadDataTableAndCombobox(loaiTaiKhoan);
		}
	}
	
	public QuanLyTaiKhoan() {
		init();
	}
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 772, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tài khoản");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setBounds(341, 10, 131, 35);
		contentPane.add(lblNewLabel);

		tfTenDangNhap = new JTextField();
		tfTenDangNhap.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tfTenDangNhap.setBounds(323, 102, 166, 30);
		contentPane.add(tfTenDangNhap);
		tfTenDangNhap.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(182, 105, 119, 24);
		contentPane.add(lblNewLabel_1);

		JLabel lblMatKhau = new JLabel("Mật khẩu");
		lblMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMatKhau.setBounds(225, 152, 76, 24);
		contentPane.add(lblMatKhau);

		tfMatKhau = new JPasswordField();
		tfMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tfMatKhau.setColumns(10);
		tfMatKhau.setBounds(323, 149, 166, 30);
		contentPane.add(tfMatKhau);
		tfMatKhau.setEchoChar('*');

		tbTaiKhoan = new JTable();
		tbTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tbTaiKhoan.setRowHeight(22); // Adjust row height to fit the font size


		// Wrap the JTable inside a JScrollPane
		JScrollPane scrollPane = new JScrollPane(tbTaiKhoan);
		scrollPane.setBounds(10, 308, 748, 191); // Set bounds for the JScrollPane
		contentPane.add(scrollPane); // Add the JScrollPane to the contentPane = new JTable();
		tbTaiKhoan.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tbTaiKhoan.setRowHeight(22); // Adjust row height to fit the font size
		model = new DefaultTableModel(new Object[][] {},
				new String[] { "Mã tài khoản", "Tên đăng nhập", "Tên người sở hữu", "Mã người sỡ hữu" });
		tbTaiKhoan.setModel(model);

		JRadioButton rdbtnGiangVien = new JRadioButton("Giảng viên");
		rdbtnGiangVien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		groupLoaiTaiKhoan.add(rdbtnGiangVien);
		rdbtnGiangVien.setBounds(238, 63, 111, 33);
		contentPane.add(rdbtnGiangVien);
		rdbtnGiangVien.setSelected(true);

		rdbtnGiangVien.addActionListener(e -> { 
			loadDataTableAndCombobox("LTK002");
		});
		
		JPasswordField  tfXacNhanMatKhau = new JPasswordField ();
		tfXacNhanMatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tfXacNhanMatKhau.setEnabled(true);
		tfXacNhanMatKhau.setColumns(10);
		tfXacNhanMatKhau.setBounds(323, 186, 166, 30);
		contentPane.add(tfXacNhanMatKhau);
		tfXacNhanMatKhau.setEchoChar('*');
		tfXacNhanMatKhau.setEnabled(false);
		
		JRadioButton rdbtnSinhVien = new JRadioButton("Sinh viên");
		rdbtnSinhVien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		groupLoaiTaiKhoan.add(rdbtnSinhVien);
		rdbtnSinhVien.setBounds(407, 63, 101, 33);
		contentPane.add(rdbtnSinhVien);
		
		rdbtnSinhVien.addActionListener(e -> { 
			loadDataTableAndCombobox("LTK003");
		});

		btnThem = new JButton("Thêm");
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThem.setBounds(86, 265, 85, 33);
		contentPane.add(btnThem);
		
		btnThem.addActionListener(e -> { 
		    hanhDong="them";
		    btnsKhoaMo();
		    tfTenDangNhap.setEnabled(true);
			tfMatKhau.setEnabled(true);
			cbb.setEnabled(true);
			tfXacNhanMatKhau.setEnabled(true);
			
		});

		btnSua = new JButton("Sửa");
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnSua.setBounds(192, 265, 79, 33);
		contentPane.add(btnSua);

		btnXoa = new JButton("Xóa");
		btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnXoa.setBounds(292, 265, 79, 33);
		contentPane.add(btnXoa);

		btnLuu = new JButton("Lưu");
		btnLuu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnLuu.setBounds(392, 265, 79, 33);
		contentPane.add(btnLuu);
		
		btnLuu.addActionListener(e -> { 
		    if (hanhDong.equals("them")) {
		    	// Check if text field is empty
		    	if (tfTenDangNhap.getText().trim().isEmpty()) {
		    	    JOptionPane.showMessageDialog(null, "Tên đăng nhập bị trống!", "Error", JOptionPane.ERROR_MESSAGE);
		    	    tfTenDangNhap.requestFocus(); // Focus the text field
		    	    return;
		    	}
		    	
		    	if (tfMatKhau.getPassword().length == 0) {
		    	    JOptionPane.showMessageDialog(null, "Mật khẩu trống!", "Error", JOptionPane.ERROR_MESSAGE);
		    	    tfMatKhau.requestFocus(); // Focus on the password field for user correction
		    	    return;
		    	}
		    	
		    	char[] password1 = tfMatKhau.getPassword();
                char[] password2 = tfXacNhanMatKhau.getPassword();

                // Compare passwords
                if (java.util.Arrays.equals(password1, password2)) {
                	// Check if combobox has no selection
    		    	if (cbb.getSelectedItem() == null) {
    		    	    JOptionPane.showMessageDialog(null, "Chưa chọn người sở hữu tài khoản!", "Error", JOptionPane.ERROR_MESSAGE);
    		    	    cbb.requestFocus(); // Focus the combo box
    		    	    return;
    		    	}
    		    	
    		    	String tenDangNhap = tfTenDangNhap.getText();
    		    	String matKhau = new String(password1);
    		    	 String maNguoiSoHuu="";
    		    	 String maTaiKhoan = "TK"+id.TaoId();
    		    	String tenNguoiSoHuu = (String) cbb.getSelectedItem();
    			    if (tenNguoiSoHuu != null && cbbMapData.containsKey(tenNguoiSoHuu)) {
    			         maNguoiSoHuu = cbbMapData.get(tenNguoiSoHuu);
    			    }
    			    String loaiTaiKhoan = "";
    			    if (rdbtnGiangVien.isSelected()) {
                        loaiTaiKhoan="LTK002";
                    } else if (rdbtnSinhVien.isSelected()) {
                    	loaiTaiKhoan="LTK003";
                    }
    			    try {
    			        db.connect();
    			        db.conn.setAutoCommit(false);

    			        // Create the account
    			        String sql = String.format("CALL ThemTaiKhoan('%s', '%s', '%s', '%s')", maTaiKhoan, loaiTaiKhoan, tenDangNhap, matKhau);
    			        db.Excute(sql);

    			        // Assign the account to a person based on account type
    			        if (loaiTaiKhoan.equals("LTK002")) {
    			            sql = String.format("CALL ThemTaiKhoanChoGiangVien('%s', '%s')", maNguoiSoHuu, maTaiKhoan);
    			        } else if (loaiTaiKhoan.equals("LTK003")) {
    			            sql = String.format("CALL ThemTaiKhoanChoSinhVien('%s', '%s')", maNguoiSoHuu, maTaiKhoan);
    			        }

    			        // Execute the stored procedure
    			        db.Excute(sql);

    			        // Commit the transaction
    			        db.conn.commit();

    			        // Show success message
    			        JOptionPane.showMessageDialog(null, "Thêm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
    			    } catch (SQLException ex) {
    			        // Rollback transaction on error
    			        if (db.conn != null) {
    			            try {
    			                db.conn.rollback();
    			            } catch (SQLException rollbackEx) {
    			                JOptionPane.showMessageDialog(null, "Failed to rollback transaction: " + rollbackEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    			            }
    			        }

    			        // Show error message from the stored procedure
    			        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    			    } finally {
    			        // Close the database connection
    			        if (db.conn != null) {
    			            try {
    			                db.conn.setAutoCommit(true); // Reset to default behavior
    			                db.close();
    			            } catch (SQLException closeEx) {
    			                JOptionPane.showMessageDialog(null, "Failed to close connection: " + closeEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    			            }
    			        }
    			    } 		    	
                } else {
                    JOptionPane.showMessageDialog(null, "Mật khẩu không giống nhau!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
		    	    	
		    }
		});
		
		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnHuy.setBounds(492, 265, 79, 33);
		contentPane.add(btnHuy);

		JButton btnThoat = new JButton("Thoát");
		btnThoat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThoat.setBounds(592, 265, 90, 33);
		contentPane.add(btnThoat);
		btnThoat.addActionListener(e -> {
			this.dispose();
		});

		 cbb = new JComboBox();
		cbb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cbb.setBounds(323, 223, 166, 32);
		contentPane.add(cbb);

		JLabel lblTnNgiS = new JLabel("Tên người sở hữu");
		lblTnNgiS.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTnNgiS.setBounds(159, 231, 142, 24);
		contentPane.add(lblTnNgiS);
		loadDataTableAndCombobox("LTK002");
		btnsKhoaMo();
		tfTenDangNhap.setEnabled(false);
		tfMatKhau.setEnabled(false);
		cbb.setEnabled(false);
		
		JLabel lblXac = new JLabel("Xác nhận mật khẩu");
		lblXac.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblXac.setBounds(159, 186, 156, 24);
		contentPane.add(lblXac);
		
		
	}

	public void loadDataTableAndCombobox(String loaiTaiKhoan) {
		String sql = "";
		if (loaiTaiKhoan.equals("LTK002")) {
			sql = "call XemTaiKhoanGiangVien";
		} else {
			sql = "call XemTaiKhoanSinhVien";
		}
		try {
			db.connect();	
			ResultSet rs = db.Excute(sql);
			cbb.removeAllItems();
	        cbbMapData.clear();
	        model.setRowCount(0);
			if (loaiTaiKhoan.equals("LTK002")) {
				
				while (rs.next()) {
					String tenGiangVien = rs.getString("TENGIANGVIEN");
					String maTaiKhoan = rs.getString("MATAIKHOAN");
					String tenDangNhap = rs.getString("TENDANGNHAP");
					String ma = rs.getString("MAGIANGVIEN");
					model.addRow(new Object[] { maTaiKhoan, tenDangNhap, tenGiangVien, ma });
				}
				sql="call XemGiangVien()";
				rs = db.Excute(sql);
				
		        // Populate JComboBox and Map
		        while (rs != null && rs.next()) {
		            String maGiangVien = rs.getString("MAGIANGVIEN");
		            String tenGiangVien = rs.getString("TENGIANGVIEN");
		            cbbMapData.put(tenGiangVien, maGiangVien); // Map TENSINHVIEN to MASV
		            cbb.addItem(tenGiangVien); // Add TENSINHVIEN to JComboBox
		        }	
			}else {
				while (rs.next()) {
					String tenSinhVien = rs.getString("TENSINHVIEN");
					String maTaiKhoan = rs.getString("MATAIKHOAN");
					String tenDangNhap = rs.getString("TENDANGNHAP");
					String ma = rs.getString("MASINHVIEN");
					model.addRow(new Object[] { maTaiKhoan, tenDangNhap, tenSinhVien, ma });
				}
				sql="call XemSinhVien()";
				rs = db.Excute(sql);

		        // Populate JComboBox and Map
		        while (rs != null && rs.next()) {
		            String maSinhVien = rs.getString("MASINHVIEN");
		            String tenSinhVien = rs.getString("TENSINHVIEN");
		            cbbMapData.put(tenSinhVien, maSinhVien); // Map TENSINHVIEN to MASV
		            cbb.addItem(tenSinhVien); // Add TENSINHVIEN to JComboBox
		        }
			}
			cbb.setSelectedItem(null);
			db.close();
		} catch (SQLException err) {
			err.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void btnsKhoaMo () {
		if(hanhDong.equals("")) {
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		}else if(hanhDong.equals("them") || hanhDong.equals("sua")) {
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnXoa.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
		}
	}
}
