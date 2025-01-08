package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.database;
import model.User;

public class CtHoatDong extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTenHoatDong;
	private JTextField tfGiangVienPhuTrach;
	private JTable tbCtHoatDong;
	private JTextField tfMoTa;
	private database db = new database();
	private Map<String, String> sinhVienMap = new HashMap<>();
	private String HanhDong = "";
	private User NguoiDung;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CtHoatDong frame = new CtHoatDong();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */

	public CtHoatDong (User NguoiDung) {
		this.NguoiDung = NguoiDung;
		System.out.println(NguoiDung.getMaUser());
		System.out.println(NguoiDung.getTenUser());
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tên hoạt động");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(231, 70, 116, 24);
		contentPane.add(lblNewLabel);
		
		tfTenHoatDong = new JTextField();
		tfTenHoatDong.setForeground(new Color(0, 0, 0));
		tfTenHoatDong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tfTenHoatDong.setBounds(361, 67, 291, 30);
		tfTenHoatDong.setEnabled(false);
		tfTenHoatDong.setDisabledTextColor(Color.BLACK);
		contentPane.add(tfTenHoatDong);
		tfTenHoatDong.setColumns(10);
		
		JLabel lblGingVinPh = new JLabel("Giảng viên phụ trách");
		lblGingVinPh.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGingVinPh.setBounds(184, 104, 167, 24);
		contentPane.add(lblGingVinPh);
		
		tfGiangVienPhuTrach = new JTextField();
		tfGiangVienPhuTrach.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tfGiangVienPhuTrach.setEnabled(false);
		tfGiangVienPhuTrach.setDisabledTextColor(Color.BLACK);
		tfGiangVienPhuTrach.setColumns(10);
		tfGiangVienPhuTrach.setBounds(361, 101, 217, 30);
		contentPane.add(tfGiangVienPhuTrach);
		
		// Create the JTable
		tbCtHoatDong = new JTable();
		tbCtHoatDong.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tbCtHoatDong.setRowHeight(22); // Adjust row height to fit the font size
		tbCtHoatDong.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 sinh vi\u00EAn", "T\u00EAn Sinh Vi\u00EAn", "M\u00F4 t\u1EA3"
			}
		));

		// Wrap the JTable inside a JScrollPane
		JScrollPane scrollPane = new JScrollPane(tbCtHoatDong);
		scrollPane.setBounds(10, 227, 818, 153); // Set bounds for the JScrollPane
		contentPane.add(scrollPane); // Add the JScrollPane to the contentPane

		
		JLabel lblChiTitHot = new JLabel("Chi tiết hoạt động");
		lblChiTitHot.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblChiTitHot.setBounds(316, 10, 234, 35);
		contentPane.add(lblChiTitHot);
		
		JLabel lblTnSinhVin = new JLabel("Tên sinh viên");
		lblTnSinhVin.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblTnSinhVin.setBounds(244, 138, 107, 24);
		contentPane.add(lblTnSinhVin);
		
		JLabel lblMT = new JLabel("Mô tả");
		lblMT.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblMT.setBounds(300, 175, 47, 24);
		contentPane.add(lblMT);
		
		tfMoTa = new JTextField();
		tfMoTa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tfMoTa.setEnabled(false);
		tfMoTa.setColumns(10);
		tfMoTa.setBounds(361, 172, 291, 30);
		contentPane.add(tfMoTa);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThem.setBounds(61, 390, 126, 45);
		
		contentPane.add(btnThem);
		
		JButton btnSua = new JButton("Sửa");
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnSua.setBounds(221, 390, 114, 45);
		contentPane.add(btnSua);
		
		JButton btnXoa = new JButton("Xóa");
		btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnXoa.setBounds(373, 390, 114, 45);
		contentPane.add(btnXoa);
		
		JButton btnThoat = new JButton("Thoát");
		btnThoat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThoat.setBounds(721, 5, 107, 52);
		contentPane.add(btnThoat);
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnLuu.setBounds(520, 390, 114, 45);
		btnLuu.setEnabled(false);
		contentPane.add(btnLuu);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnHuy.setBounds(666, 390, 120, 45);
		btnHuy.setEnabled(false);
		contentPane.add(btnHuy);
		
		JComboBox cbbTenSinhVien = new JComboBox();
		cbbTenSinhVien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cbbTenSinhVien.setBounds(361, 134, 217, 32);
		cbbTenSinhVien.setEnabled(false);
		contentPane.add(cbbTenSinhVien);
		cbbTenSinhVien.addActionListener(e -> {
		    String selectedTenSinhVien = (String) cbbTenSinhVien.getSelectedItem();
		    if (selectedTenSinhVien != null && sinhVienMap.containsKey(selectedTenSinhVien)) {
		        String selectedMasv = sinhVienMap.get(selectedTenSinhVien);
		        System.out.println("Selected MASV: " + selectedMasv);
		    }
		});
		
	    DefaultTableModel model = (DefaultTableModel) tbCtHoatDong.getModel(); // Get the model
	    try {
	    	String sql = "CALL XemCtHoatDong('HD001')"; // SQL querysql = "CALL XemCtHoatDong('HD001')"; // SQL query
	        db.connect(); // Establish connection

	        ResultSet rs = db.Excute(sql); // Execute the query
	        if (rs != null && rs.next()) {
	            // Retrieve the values of the desired columns
	            String tenHoatDong = rs.getString("TENHOATDONG");
	            String tenGiangVien = rs.getString("TENGIANGVIEN");

	            // Set values to the JTextFields
	            tfTenHoatDong.setText(tenHoatDong);
	            tfGiangVienPhuTrach.setText(tenGiangVien);
	        }
            // Set values to the JTextFields
            

	        // Clear existing rows in the table
	        model.setRowCount(0);
	        
	        // Iterate through the ResultSet and add rows to the model
	        while (rs != null && rs.next()) {
	            String maSinhVien = rs.getString("MASINHVIEN");
	            String tenSinhVien = rs.getString("TENSINHVIEN");
	            String moTa = rs.getString("MOTA");
	            model.addRow(new Object[] { maSinhVien, tenSinhVien, moTa }); // Add row to the model
	        }
	        sql = "CALL XemSinhVien()"; // SQL query
	        rs = db.Excute(sql);
	        cbbTenSinhVien.removeAllItems();
	        sinhVienMap.clear();

	        // Populate JComboBox and Map
	        while (rs != null && rs.next()) {
	            String masv = rs.getString("MASINHVIEN");
	            String tenSinhVien = rs.getString("TENSINHVIEN");
	            sinhVienMap.put(tenSinhVien, masv); // Map TENSINHVIEN to MASV
	            cbbTenSinhVien.addItem(tenSinhVien); // Add TENSINHVIEN to JComboBox
	        }
	        db.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	    btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
               cbbTenSinhVien.setEnabled(true);
               tfMoTa.setEnabled(true);
               HanhDong="Them";
            }
        });
	    btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(HanhDong=="Them") {
            		if (tfMoTa.getText().trim().isEmpty()) {
            			JOptionPane.showMessageDialog(null, "Mô tả rỗng", "Error", JOptionPane.ERROR_MESSAGE);
            		} else {
            			String tenSinhVien = (String) cbbTenSinhVien.getSelectedItem();
            			int response = JOptionPane.showConfirmDialog(
            			        null, 
            			        "Bạn có chắc muốn thêm "+tenSinhVien+ " với mô tả: "+tfMoTa.getText(), 
            			        "Confirm", 
            			        JOptionPane.YES_NO_OPTION, 
            			        JOptionPane.QUESTION_MESSAGE
            			);

            			if (response == JOptionPane.YES_OPTION) {
            				JOptionPane.showMessageDialog(null, "Thêm thành công", "Info", JOptionPane.INFORMATION_MESSAGE);
            				//refresh table
            				btnThem.setEnabled(true);
                            btnSua.setEnabled(true);
                            btnXoa.setEnabled(true);
                            btnLuu.setEnabled(false);
                            btnHuy.setEnabled(false);
                           cbbTenSinhVien.setEnabled(false);
                           tfMoTa.setEnabled(false);
                           HanhDong="";
            			} else if (response == JOptionPane.NO_OPTION) {
            				JOptionPane.showMessageDialog(null, "Bấm nút hủy để thêm khỏi hành động thêm", "Info", JOptionPane.INFORMATION_MESSAGE);
            			}
            		}
            	}
//                btnThem.setEnabled(false);
//                btnSua.setEnabled(false);
//                btnXoa.setEnabled(false);
//                btnLuu.setEnabled(true);
//                btnHuy.setEnabled(true);
//               cbbTenSinhVien.setEnabled(true);
//               tfMoTa.setEnabled(true);
               
            }
        });
		this.setVisible(true);
		
	}
}
