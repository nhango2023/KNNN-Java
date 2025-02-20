package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import database.database;
import helper.Id;
import model.GiangVien;
import model.User;

public class QuanLyHoatDong extends JFrame {
	private static final long serialVersionUID = 1L;
	private JComboBox cbbTenGV;
	private JTextField tfTenHoatDong;
	private JTextField tfTimKiem;
	private JSpinner dpNgayBatDau, dpNgayKetThuc;
	private JButton btnThem, btnSua, btnXoa;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnLuu;
	private JButton btnHuy;
	private JButton btnThoat;
	private String hanhDong = "";
	private database db = new database();
	private Id id = new Id();
	private JTextArea taMoTa;
	private JButton btnXemCTHD;
	private final ButtonGroup groupFilter = new ButtonGroup();
	private JRadioButton rdbtnHDDangQuanLy;
	private JRadioButton rdbtnHDDangThamGia;
	private User NguoiDung = new User();
	private JRadioButton rdbtnTatCa;

	public QuanLyHoatDong(User NguoiDung) {
		this.NguoiDung=NguoiDung;
		setTitle("Quản Lý Hoạt Động");
		setSize(1000, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel tabHoatDong = new BackgroundPanel("src/images/hdd.jpg");
		tabHoatDong.setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		inputPanel.setOpaque(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 150, 2, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0; // Allow buttons to resize evenly

		JLabel lblTenHoatDong = new JLabel("Tên Hoạt Động:");
		tfTenHoatDong = new JTextField();
		
		JLabel lblMaGV = new JLabel("Giảng Viên phụ trách:");
		cbbTenGV = new JComboBox();
		JLabel lblNgayBatDau = new JLabel("Ngày Bắt Đầu:");
		dpNgayBatDau = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor1 = new JSpinner.DateEditor(dpNgayBatDau, "yyyy-MM-dd HH:mm");
		dpNgayBatDau.setEditor(dateEditor1);

		JLabel lblNgayKetThuc = new JLabel("Ngày Kết Thúc:");
		dpNgayKetThuc = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(dpNgayKetThuc, "yyyy-MM-dd HH:mm");
		dpNgayKetThuc.setEditor(dateEditor2);


		JLabel lblMoTa = new JLabel("Mô tả:");
		taMoTa = new JTextArea(5, 30); // 10 rows and 30 columns
		taMoTa.setLineWrap(true); // Enable line wrapping
		taMoTa.setWrapStyleWord(true); // Wrap at word boundaries

		// Add JTextArea to a JScrollPane for scrollability
		JScrollPane scrollPaneMoTa = new JScrollPane(taMoTa);
		
		JLabel lblTimKiem = new JLabel("Tìm kiếm: ");
		tfTimKiem = new JTextField();
		
		tfTimKiem.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                textChanged();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                textChanged();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                textChanged();
	            }

	            private void textChanged() {
	                loadData();
	            }
	        });

		gbc.gridx = 0;
		gbc.gridy = 1;
		inputPanel.add(lblTenHoatDong, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		inputPanel.add(tfTenHoatDong, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		inputPanel.add(lblMaGV, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		inputPanel.add(cbbTenGV, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		inputPanel.add(lblNgayBatDau, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		inputPanel.add(dpNgayBatDau, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		inputPanel.add(lblNgayKetThuc, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		inputPanel.add(dpNgayKetThuc, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		inputPanel.add(lblMoTa, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		inputPanel.add(scrollPaneMoTa, gbc);

		 rdbtnTatCa = new JRadioButton("Tất cả");
		 rdbtnTatCa.setSelected(true);
		rdbtnTatCa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		groupFilter.add(rdbtnTatCa);

		gbc.gridx = 0;
		gbc.gridy = 6;
		inputPanel.add(lblTimKiem, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		inputPanel.add(tfTimKiem, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.weightx = 1.0; // Distribute space evenly
		inputPanel.add(rdbtnTatCa, gbc);

		rdbtnHDDangQuanLy = new JRadioButton("Hoạt động đang quản lý");
		rdbtnHDDangQuanLy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		groupFilter.add(rdbtnHDDangQuanLy);
		rdbtnHDDangQuanLy.setVisible(false);
		gbc.gridx = 1;
		inputPanel.add(rdbtnHDDangQuanLy, gbc);

		rdbtnHDDangThamGia = new JRadioButton("Hoạt động đang tham gia");
		rdbtnHDDangThamGia.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		groupFilter.add(rdbtnHDDangThamGia);
		rdbtnHDDangThamGia.setVisible(false);
		gbc.gridx = 2;
		inputPanel.add(rdbtnHDDangThamGia, gbc);
		
		ActionListener actionListener = e -> loadData();
		rdbtnHDDangThamGia.addActionListener(actionListener);
		rdbtnHDDangQuanLy.addActionListener(actionListener);
		rdbtnTatCa.addActionListener(actionListener);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setOpaque(false);
		btnThem = new JButton("Thêm");
		btnSua = new JButton("Sửa");
		btnXoa = new JButton("Xóa");
		btnLuu = new JButton("Lưu");
		btnXoa = new JButton("Xóa");
		btnHuy = new JButton("Hủy");
		btnThoat = new JButton("Đóng");
		btnXemCTHD = new JButton("Xem CTHD");

		buttonPanel.add(btnThem);
		buttonPanel.add(btnSua);
		buttonPanel.add(btnXoa);
		buttonPanel.add(btnLuu);
		buttonPanel.add(btnHuy);
		buttonPanel.add(btnThoat);
		buttonPanel.add(btnXemCTHD);

		tableModel = new DefaultTableModel(
				new String[] { "Hoạt động", "Giảng viên phụ trách", "Mô tả", "Ngày BĐ", "Ngày KT", "Mã HD", "Mã GV" },
				0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					// Retrieve data from the selected row
					Object tenHoatDong = table.getValueAt(selectedRow, 0);
					tfTenHoatDong.setText(String.valueOf(tenHoatDong));

					Object moTa = table.getValueAt(selectedRow, 2);
					taMoTa.setText(String.valueOf(moTa));

					Object ngayBatDau = table.getValueAt(selectedRow, 3); // Column 1 (Name)
					Object ngayKetThuc = table.getValueAt(selectedRow, 4); // Column 1 (Name)

					// Create a SimpleDateFormat instance for parsing
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

					try {
						Date specificDate = sdf.parse(String.valueOf(ngayBatDau));
						dpNgayBatDau.setValue(specificDate);
						specificDate = sdf.parse(String.valueOf(ngayKetThuc));
						dpNgayKetThuc.setValue(specificDate);
					} catch (Exception err) {
						err.printStackTrace();
					}
					int modelRow = table.convertRowIndexToModel(selectedRow);
					String maGV = String.valueOf(tableModel.getValueAt(modelRow, 6));
					for (int i = 0; i < cbbTenGV.getItemCount(); i++) {
						GiangVien GV = (GiangVien) cbbTenGV.getItemAt(i);
						if (GV.getMaGiangVien().equals(maGV)) {
							cbbTenGV.setSelectedIndex(i);
							break;
						}
					}
				}
			}
		});

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setOpaque(false);
		topPanel.add(inputPanel, BorderLayout.CENTER);
		topPanel.add(buttonPanel, BorderLayout.SOUTH);

		tabHoatDong.add(topPanel, BorderLayout.NORTH);
		tabHoatDong.add(scrollPane, BorderLayout.CENTER);

		tabbedPane.addTab("Quản Lý Hoạt Động", tabHoatDong);

		add(tabbedPane);
		btnXemCTHD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					Object objMaHoatDong = table.getValueAt(selectedRow, 5);
					String maHoatDong = String.valueOf(objMaHoatDong);
					Object objMaGiangVien = table.getValueAt(selectedRow, 6);
					String maGiangVien = String.valueOf(objMaGiangVien);
					boolean coTheSua=false;
					if (NguoiDung.getMaUser().equals(maGiangVien)) {
						coTheSua=true;
					}
					new CtHoatDong(NguoiDung, maHoatDong, coTheSua);
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn hoạt động cần xem chi tiết!");
				}
			}
		});

		btnThem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hanhDong = "them";
				tfTenHoatDong.setText("");
				taMoTa.setText("");
				cbbTenGV.setSelectedIndex(-1);
				btnsKhoaMo();
			}
		});

		btnThoat.addActionListener(e -> {
			dispose();
		});

		btnThem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		btnSua.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					hanhDong = "sua";
					btnsKhoaMo();
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
				}
			}
		});

		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hanhDong == "them") {
					String tenHoatDong = tfTenHoatDong.getText();
					if (tenHoatDong.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Tên hoạt động không được để trống.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					GiangVien selectedGiangVien = (GiangVien) cbbTenGV.getSelectedItem();

					if (selectedGiangVien == null) {
						JOptionPane.showMessageDialog(null, "Giảng viên phải được chọn.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Date dateNgayBatDau = (Date) dpNgayBatDau.getValue();

					if (dateNgayBatDau == null) {
						JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được để trống.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Date dateNgayKetThuc = (Date) dpNgayKetThuc.getValue();

					if (dateNgayKetThuc == null) {
						JOptionPane.showMessageDialog(null, "Ngày kết thúc không được để trống.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (dateNgayKetThuc.before(dateNgayBatDau)) {
						JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String maGV = selectedGiangVien.getMaGiangVien();
					String ngayBatDau = sdf.format(dateNgayBatDau);
					String ngayKetThuc = sdf.format(dateNgayKetThuc);
					String moTa = taMoTa.getText();
					String maHoatDong = "HD" + id.TaoId();
					try {
						db.connect();
						String sql = String.format("CALL ThemHoatdong('%s', '%s', '%s','%s','%s','%s')", maHoatDong,
								maGV, tenHoatDong, ngayBatDau, ngayKetThuc, moTa);
						db.Excute(sql);
						db.close();
						JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						// refresh table
						loadData();
						hanhDong = "";
						btnsKhoaMo();

					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}

				} else if (hanhDong == "sua") {
					String tenHoatDong = tfTenHoatDong.getText();
					if (tenHoatDong.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Tên hoạt động không được để trống.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					GiangVien selectedGiangVien = (GiangVien) cbbTenGV.getSelectedItem();

					if (selectedGiangVien == null) {
						JOptionPane.showMessageDialog(null, "Giảng viên phải được chọn.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Date dateNgayBatDau = (Date) dpNgayBatDau.getValue();

					if (dateNgayBatDau == null) {
						JOptionPane.showMessageDialog(null, "Ngày bắt đầu không được để trống.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					Date dateNgayKetThuc = (Date) dpNgayKetThuc.getValue();

					if (dateNgayKetThuc == null) {
						JOptionPane.showMessageDialog(null, "Ngày kết thúc không được để trống.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (dateNgayKetThuc.before(dateNgayBatDau)) {
						JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu.", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String maGV = selectedGiangVien.getMaGiangVien();
					String ngayBatDau = sdf.format(dateNgayBatDau);
					String ngayKetThuc = sdf.format(dateNgayKetThuc);
					String moTa = taMoTa.getText();
					int selectedRow = table.getSelectedRow();
					Object maHoatDong = table.getValueAt(selectedRow, 5);
					String maHD = String.valueOf(maHoatDong);
					try {
						db.connect();
						String sql = String.format("CALL SuaHoatDong('%s', '%s', '%s', '%s', '%s', '%s')", maHD,
								tenHoatDong, ngayBatDau, ngayKetThuc, maGV, moTa);
						db.Excute(sql);
						db.close();
						JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						// refresh table
						loadData();
						hanhDong = "";
						btnsKhoaMo();
					} catch (SQLException err) {
						JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					tableModel.removeRow(selectedRow);
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa!");
				}
			}
		});
		loadData();
		tfTenHoatDong.setEnabled(false);
		cbbTenGV.setEnabled(false);
		dpNgayBatDau.setEnabled(false);
		dpNgayKetThuc.setEnabled(false);
		taMoTa.setEnabled(false);
		this.setVisible(true);
		if (NguoiDung.getLoaiUser().equals("LTK001")) {			
			rdbtnHDDangThamGia.setVisible(false);
			rdbtnHDDangQuanLy.setVisible(false);
		} else {
			if (NguoiDung.getLoaiUser().equals("LTK002")) {				
				rdbtnHDDangQuanLy.setVisible(true);
			} else if (NguoiDung.getLoaiUser().equals("LTK003")) {
				rdbtnHDDangThamGia.setVisible(true);
			}
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnXoa.setEnabled(false);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
		}

	}

	class BackgroundPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Image backgroundImage;

		public BackgroundPanel(String filePath) {
			try {
				backgroundImage = new ImageIcon(filePath).getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}

//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			QuanLyHoatDong frame = new QuanLyHoatDong();
//			frame.setVisible(true);
//		});
//	}

	public void btnsKhoaMo() {
		if (hanhDong.equals("")) {
			btnThem.setEnabled(true);
			btnSua.setEnabled(true);
			btnXoa.setEnabled(true);
			btnLuu.setEnabled(false);
			btnHuy.setEnabled(false);
			tfTenHoatDong.setEnabled(false);
			cbbTenGV.setEnabled(false);
			dpNgayBatDau.setEnabled(false);
			dpNgayKetThuc.setEnabled(false);
			taMoTa.setEnabled(false);
			cbbTenGV.setSelectedIndex(-1);
			tfTenHoatDong.setText("");
			taMoTa.setText("");
		} else if (hanhDong.equals("them") || hanhDong.equals("sua")) {
			btnThem.setEnabled(false);
			btnSua.setEnabled(false);
			btnXoa.setEnabled(false);
			btnLuu.setEnabled(true);
			btnHuy.setEnabled(true);
			tfTenHoatDong.setEnabled(true);
			cbbTenGV.setEnabled(true);
			dpNgayBatDau.setEnabled(true);
			dpNgayKetThuc.setEnabled(true);
			taMoTa.setEnabled(true);
		}
	}

	public void loadData() {
		try {
			db.connect();
			String TenHoatDong = tfTimKiem.getText();
			String sql="";
			if (rdbtnTatCa.isSelected()) {
				sql = String.format("Call XemHoatDong(NULL, '%s')", TenHoatDong);
			}
			else if (rdbtnHDDangQuanLy.isSelected()) {
				sql = String.format("Call XemHoatDong('%s', '%s')", NguoiDung.getMaUser(),TenHoatDong);				
			}else if (rdbtnHDDangThamGia.isSelected()) {
				sql = String.format("Call XemHoatDongDangThamGia('%s', '%s')", NguoiDung.getMaUser(),TenHoatDong);	
			}			
			ResultSet rs = db.Excute(sql);
			tableModel.setRowCount(0);
			// Load data into the table
			while (rs.next()) {
				String tenHoatDong = rs.getString("TENHOATDONG");
				String tenGiangVien = rs.getString("TENGIANGVIEN");
				String moTa = rs.getString("MOTA");
				String ngayBatDau = rs.getString("NGAYBATDAU");
				String ngayKetThuc = rs.getString("NGAYKETTHUC");
				String maHoatDong = rs.getString("MAHOATDONG");
				String maGiangVien = rs.getString("MAGIANGVIEN");

				tableModel.addRow(new Object[] { tenHoatDong, tenGiangVien, moTa, ngayBatDau, ngayKetThuc, maHoatDong,
						maGiangVien });
			}

			// Load data into the JComboBox
			sql = "call XemGiangVien()";
			rs = db.Excute(sql);
			cbbTenGV.removeAllItems(); // Clear existing items
			while (rs.next()) {
				String maGV = rs.getString("MAGIANGVIEN");
				String tenGV = rs.getString("TENGIANGVIEN");
				// Add Khoa object to JComboBox
				cbbTenGV.addItem(new GiangVien(maGV, tenGV));
			}
			cbbTenGV.setSelectedIndex(-1);
			rs.close(); // Close ResultSet
			db.close(); // Close connection
		} catch (SQLException err) {
			JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
