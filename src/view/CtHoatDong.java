package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import model.SinhVien;
import model.User;

public class CtHoatDong extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTenHoatDong;
	private JTextField tfGiangVienPhuTrach;
	private JTable table;
	private JTextField tfMoTa;
	private database db = new database();

	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLuu;
	private JButton btnHuy;
	private JButton btnThoat;
	private String hanhDong = "";
	private JComboBox cbbTenSinhVien;
	private DefaultTableModel tableModel;

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

	public CtHoatDong(User NguoiDung, String maHoatDong, boolean coTheSua) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		tableModel = new DefaultTableModel(new String[] { "Tên sinh viên", "Mô tả", "MaHD", "MaSV" }, 0);
		table = new JTable(tableModel);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		table.setRowHeight(22); // Adjust row height to fit the font size
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// Retrieve data from the selected row
					Object moTa = table.getValueAt(selectedRow, 1); // Column 1 (Name)
					// Print the data
					tfMoTa.setText(String.valueOf(moTa)); // Convert object to string

					int modelRow = table.convertRowIndexToModel(selectedRow);
					String maSV = String.valueOf(tableModel.getValueAt(modelRow, 3));

					for (int i = 0; i < cbbTenSinhVien.getItemCount(); i++) {
						SinhVien SinhVien = (SinhVien) cbbTenSinhVien.getItemAt(i);
						if (SinhVien.getMaSinhVien().equals(maSV)) {
							cbbTenSinhVien.setSelectedIndex(i);
							break;
						}
					}
				}
			}
		});

		// Wrap the JTable inside a JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
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

		btnThem = new JButton("Thêm");
		btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThem.setBounds(61, 390, 126, 45);
		btnThem.addActionListener(e -> {
			hanhDong = "them";
			btnsKhoaMo();
			cbbTenSinhVien.setEnabled(true);
			tfMoTa.setEnabled(true);

		});
		contentPane.add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnSua.setBounds(221, 390, 114, 45);
		contentPane.add(btnSua);
		btnSua.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					hanhDong = "sua";
					tfMoTa.setEnabled(true);
					btnsKhoaMo();
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
				}
			}
		});

		btnXoa = new JButton("Xóa");
		btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnXoa.setBounds(373, 390, 114, 45);
		contentPane.add(btnXoa);

		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String maSV = String.valueOf(table.getValueAt(selectedRow, 3));
					String maHD = "HD001";
					try {
						db.connect();
						String sql = String.format("CALL XoaCtHoatDong('%s', '%s')", maHD, maSV);
						db.Excute(sql);
						db.close();
						JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						// refresh table
						loadData(maHoatDong);
						hanhDong = "";
						tfMoTa.setText("");
						cbbTenSinhVien.setSelectedIndex(-1);
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa!");
				}
			}
		});

		btnThoat = new JButton("Đóng");
		btnThoat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnThoat.setBounds(721, 5, 107, 52);
		contentPane.add(btnThoat);

		btnLuu = new JButton("Lưu");
		btnLuu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnLuu.setBounds(520, 390, 114, 45);
		btnLuu.setEnabled(false);
		contentPane.add(btnLuu);

		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnHuy.setBounds(666, 390, 120, 45);
		btnHuy.setEnabled(false);
		contentPane.add(btnHuy);

		cbbTenSinhVien = new JComboBox();
		cbbTenSinhVien.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cbbTenSinhVien.setBounds(361, 134, 217, 32);
		cbbTenSinhVien.setEnabled(false);
		contentPane.add(cbbTenSinhVien);
		cbbTenSinhVien.addActionListener(e -> {

		});

		btnThem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				cbbTenSinhVien.setEnabled(true);
				tfMoTa.setEnabled(true);

			}
		});
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hanhDong == "them") {
					// Check if JComboBox has no valid selection
					if (cbbTenSinhVien.getSelectedIndex() == 0) { // Assuming the first option is a placeholder
						JOptionPane.showMessageDialog(null, "Chưa chọn sinh viên", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if (tfMoTa.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Mô tả rỗng", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					SinhVien selectedSV = (SinhVien) cbbTenSinhVien.getSelectedItem();
					String maSV = selectedSV.getMaSinhVien();
					String moTa = tfMoTa.getText().trim();
					try {
						db.connect();
						String sql = String.format("CALL ThemCtHoatDong('%s', '%s', '%s')", maHoatDong, maSV, moTa);
						db.Excute(sql);
						db.close();
						JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						// refresh table
						loadData(maHoatDong);
						hanhDong = "";
						btnsKhoaMo();
						tfMoTa.setText("");
						cbbTenSinhVien.setSelectedIndex(-1);
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (hanhDong.equals("sua")) {
					if (tfMoTa.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Mô tả rỗng", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String moTa = tfMoTa.getText();
					int selectedRow = table.getSelectedRow();
					String maSV = String.valueOf(table.getValueAt(selectedRow, 3));
					try {
						db.connect();
						String sql = String.format("CALL SuaCtHoatDong('%s', '%s', '%s')", maHoatDong, maSV, moTa);
						db.Excute(sql);
						db.close();
						JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						// refresh table
						loadData(maHoatDong);
						hanhDong = "";
						btnsKhoaMo();
						tfMoTa.setText("");
						cbbTenSinhVien.setSelectedIndex(-1);
						tfMoTa.setEnabled(false);
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
				}

			}
		});

		if (NguoiDung.getLoaiUser().equals("LTK002") && coTheSua) {
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		} else {
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnXoa.setEnabled(false);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		}

		this.setVisible(true);
		loadData(maHoatDong);
	}

	public void btnsKhoaMo() {
		if (hanhDong.equals("")) {
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		} else if (hanhDong.equals("them") || hanhDong.equals("sua")) {
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnXoa.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
		}
	}

	public void loadData(String maHoatDong) {
		try {
			db.connect();
			String sql = String.format("CALL XemCtHoatDong('%s')", maHoatDong);
			ResultSet rs = db.Excute(sql);
			tableModel.setRowCount(0);

			// Load data into the table
			while (rs.next()) {
				String maHD = rs.getString("MAHOATDONG");
				String maSV = rs.getString("MASINHVIEN");
				String tenSV = rs.getString("TENSINHVIEN");
				String moTa = rs.getString("MOTA");
				tableModel.addRow(new Object[] { tenSV, moTa, maHD, maSV });
				String tenHD = rs.getString("TENHOATDONG");
				String tenGV = rs.getString("TENGIANGVIEN");
				tfTenHoatDong.setText(tenHD);
				tfGiangVienPhuTrach.setText(tenGV);

			}

			// Load data into the JComboBox
			sql = "call XemSinhVien()";
			rs = db.Excute(sql);
			cbbTenSinhVien.removeAllItems(); // Clear existing items
			while (rs.next()) {
				String maSV = rs.getString("MASINHVIEN");
				String tenSV = rs.getString("TENSINHVIEN");
				cbbTenSinhVien.addItem(new SinhVien(maSV, tenSV));
			}
			rs.close(); // Close ResultSet
			db.close(); // Close connection
			// After populating the JComboBox
			cbbTenSinhVien.setSelectedIndex(-1); // Ensures no item is selected
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
