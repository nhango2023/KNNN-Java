package view;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import database.database;
import helper.Id;
import model.Khoa;
import model.Lop;

public class QuanLySinhVien extends JFrame {
    private JComboBox<String> cbMaSV;
    private JTextField tfTenSV;
//    private JComboBox<String> cbLop;
    private JButton btnThem, btnSua, btnXoa;
    private JTable table;
    private DefaultTableModel tableModel;
    private database db = new database();
    private JButton btnLuu;
    private JButton btnHuy;
    private String hanhDong="";
    private Id id = new Id();
    private JComboBox cbbTenLop;
    private JButton btnThoat;
    private JButton btnXemTaiKhoan;
    
    public QuanLySinhVien() {
        setTitle("Quản Lý Sinh Viên");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        
        BackgroundPanel mainPanel = new BackgroundPanel("src/images/sv.jpg");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setOpaque(false);

        tfTenSV = new JTextField();
        inputPanel.add(new JLabel("Tên Sinh Viên:"));
        inputPanel.add(tfTenSV);
        
        inputPanel.add(new JLabel("Lớp:"));
        cbbTenLop = new JComboBox();
        cbbTenLop.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cbbTenLop.setBounds(382, 104, 376, 32);

        inputPanel.add(cbbTenLop);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnThoat = new JButton("Thoát");
        
        btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        
        btnXemTaiKhoan = new JButton("Xem tài khoản");
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnXemTaiKhoan);
        buttonPanel.add(btnThoat);

        
        tableModel = new DefaultTableModel(new String[]{"Mã SV", "Tên SV", "Lớp","Mã lớp"}, 0);
        table = new JTable(tableModel);
        TableColumn column = table.getColumnModel().getColumn(3);
        table.getColumnModel().removeColumn(column);
              
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

       
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object tenSinhVien = table.getValueAt(selectedRow, 1);  // Column 1 (Name)
                    // Print the data
                    tfTenSV.setText(String.valueOf(tenSinhVien)); // Convert object to string
                    int modelRow = table.convertRowIndexToModel(selectedRow); // Convert view index to model index
                    String maLop = String.valueOf(tableModel.getValueAt(modelRow, 3)); // Access "Mã khoa" column 
                    for (int i = 0; i < cbbTenLop.getItemCount(); i++) {
            	        Lop lop = (Lop) cbbTenLop.getItemAt(i); 
            	        if (lop.getMaLop().equals(maLop)) { 
            	        	cbbTenLop.setSelectedIndex(i);
            	            break;
            	        }
            	    }
                }
            }
        });
        
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	tfTenSV.setText("");
            	cbbTenLop.setEnabled(true);
            	cbbTenLop.setSelectedIndex(-1);
            	hanhDong="them";
                btnsKhoaMo();
                tfTenSV.setEnabled(true);
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hanhDong="sua";
                    tfTenSV.setEnabled(true);
                    cbbTenLop.setEnabled(true);
                    btnsKhoaMo(); 
                    tfTenSV.setText(String.valueOf(table.getValueAt(selectedRow, 1)));                                
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
                }
            }
            
        });
        
        
        btnLuu.addActionListener(e -> { 
		    if (hanhDong.equals("them")) {
		    	// Check if text field is empty
                String tenSV = tfTenSV.getText();
               Lop selectedLop = (Lop) cbbTenLop.getSelectedItem();
                if (!tenSV.isEmpty()||selectedLop!=null) {
                	try {
    			        db.connect();
    			        String maSinhVien = "SV"+id.TaoId();
    			        String maLop= selectedLop.getMaLop();
    			        String sql =String.format( "CALL ThemSinhVien('%s', '%s', '%s')",maSinhVien, tenSV, maLop);
    			        db.Excute(sql);
    			        // Show success message
    			        JOptionPane.showMessageDialog(null, "Thêm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
    			        loadData();
    			        hanhDong="";
    			        btnsKhoaMo();
    			        tfTenSV.setText("");
    			        cbbTenLop.setSelectedIndex(-1);
    			        tfTenSV.setEnabled(false);
    			        cbbTenLop.setEnabled(false);
    			    } catch (SQLException ex) {
    			        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    			    } 
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sinh viên!");
                }
		    }
		    else if (hanhDong.equals("sua")) {       		
        		String tenSV = tfTenSV.getText();        		
        		if (!tenSV.isEmpty()) {              	
              	  try {
              		int selectedRow = table.getSelectedRow();
            		String maSinhVien = String.valueOf(table.getValueAt(selectedRow, 0));

            		Lop selectedLop = (Lop) cbbTenLop.getSelectedItem();
        			String maLop = selectedLop.getMaLop();        			
             		  db.connect();
              		 String sql = String.format("CALL SuaSinhVien('%s', '%s', '%s')", maSinhVien, tenSV, maLop);
              		 db.Excute(sql);
              		  db.close();
              		  JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
              		//refresh table	
              		  loadData();
              		  hanhDong="";	  
              		  btnsKhoaMo();
              		tfTenSV.setText("");
              		tfTenSV.setEnabled(false);
                    cbbTenLop.setSelectedIndex(-1);
                    cbbTenLop.setEnabled(false);
              	  }
              	  catch (SQLException err) {
            	        JOptionPane.showMessageDialog(null, "Lỗi vui lòng thử lại", "Error", JOptionPane.ERROR_MESSAGE);
            	    }                                  	       	
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
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
        btnXemTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	Object tenSinhVien = table.getValueAt(selectedRow, 0);  // Column 1 (Name)
                    new QuanLyTaiKhoan("LTK003", String.valueOf(tenSinhVien));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên muốn xem tài khoản!");
                }
            }
        });
        tfTenSV.setEnabled(false);
        cbbTenLop.setEnabled(false);
        loadData();
        btnsKhoaMo();
        this.setVisible(true);
    }
    
 
  
    class BackgroundPanel extends JPanel {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuanLySinhVien frame = new QuanLySinhVien();
            frame.setVisible(true);
        });
    }
    
    public void loadData() {
        try {
            db.connect();
            String sql = "Call XemSinhVien()";
            ResultSet rs = db.Excute(sql);
            tableModel.setRowCount(0);
            while (rs.next()) {
                String tenSinhVien = rs.getString("TENSINHVIEN");
                String maSinhVien = rs.getString("MASINHVIEN");
                String lop = rs.getString("TENLOP");
                String maLop = rs.getString("MALOP");

               

                tableModel.addRow(new Object[]{maSinhVien, tenSinhVien, lop, maLop});
            }
         // Load data into the JComboBox
	        sql = "call XemLop()";
	        rs = db.Excute(sql);
	        cbbTenLop.removeAllItems(); // Clear existing items
	        while (rs.next()) {
	            String maLop = rs.getString("MALOP");
	            String tenLop = rs.getString("TENLOP");
	            // Add Khoa object to JComboBox
	            cbbTenLop.addItem(new Lop(maLop, tenLop));
	        }
            cbbTenLop.setSelectedIndex(-1);
            rs.close();  // Close ResultSet
            db.close();  // Close connection
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
