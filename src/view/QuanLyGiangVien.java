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

public class QuanLyGiangVien extends JFrame {
    private JComboBox<String> cbMaGV;
    private JTextField tfTenGV;
    private JTextField tfKhoa;
    private JButton btnThem, btnSua, btnXoa;
    private JTable table;
    private DefaultTableModel tableModel;
    private Id id = new Id();
    private database db = new database();
    private JButton btnLuu;
    private JButton btnHuy;
    private String hanhDong = "";
    private JComboBox cbbTenKhoa;
    private JButton btnXemTaiKhoan;

    public QuanLyGiangVien() {
        setTitle("Quản Lý Giảng Viên");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    
        BackgroundPanel mainPanel = new BackgroundPanel("src/images/teacher.png");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

    
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setOpaque(false);

//        cbMaGV = new JComboBox<>(new String[]{"GV01", "GV02", "GV03"});
        tfTenGV = new JTextField();
        tfTenGV.setEnabled(false);


//        inputPanel.add(new JLabel("Mã Giảng Viên:"));
//        inputPanel.add(cbMaGV);
        inputPanel.add(new JLabel("Tên Giảng Viên:"));
        inputPanel.add(tfTenGV);
        inputPanel.add(new JLabel("Khoa"));

        cbbTenKhoa = new JComboBox();
        cbbTenKhoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        cbbTenKhoa.setBounds(382, 104, 376, 32);
        cbbTenKhoa.setEnabled(false);
        inputPanel.add(cbbTenKhoa);

        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu  = new JButton("Lưu");
         btnHuy = new JButton("Hủy");
         btnXemTaiKhoan = new JButton("Xem tài khoản");
         btnHuy.addActionListener(e -> {
             // Event handling logic
             hanhDong="";
             btnsKhoaMo();
             tfTenGV.setText("");
             tfKhoa.setText("");
             table.clearSelection();
         });
         
        JButton btnThoat = new JButton("Đóng");
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);      
        buttonPanel.add(btnXemTaiKhoan);
        buttonPanel.add(btnThoat);
        

        tableModel = new DefaultTableModel(new String[]{"Mã GV", "Tên GV", "Khoa", "Mã khoa"}, 0);
        
        table = new JTable(tableModel);           
        TableColumn column = table.getColumnModel().getColumn(3);
        table.getColumnModel().removeColumn(column);
              
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve data from the selected row
                    Object tenGiangVien = table.getValueAt(selectedRow, 1);  // Column 1 (Name)
                    // Print the data
                    tfTenGV.setText(String.valueOf(tenGiangVien)); // Convert object to string

                    int modelRow = table.convertRowIndexToModel(selectedRow); 
                    String maKhoa = String.valueOf(tableModel.getValueAt(modelRow, 3)); 
                    System.out.println(maKhoa);
                    for (int i = 0; i < cbbTenKhoa.getItemCount(); i++) {
            	        Khoa khoa = (Khoa) cbbTenKhoa.getItemAt(i); 
            	        if (khoa.getMaKhoa().equals(maKhoa)) { 
            	        	cbbTenKhoa.setSelectedIndex(i);
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

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hanhDong="sua";
                    tfTenGV.setEnabled(true);
                    cbbTenKhoa.setEnabled(true);
                    btnsKhoaMo();
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
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
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               hanhDong="them";
               btnsKhoaMo();
               tfTenGV.setEnabled(true);
               cbbTenKhoa.setEnabled(true);
               cbbTenKhoa.setSelectedIndex(-1);
               tfTenGV.setText("");
            }
        });
	    btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {           	
            	if(hanhDong.equals("them")) {
            		String tenGV = tfTenGV.getText();     
            		Khoa selectedKhoa = (Khoa) cbbTenKhoa.getSelectedItem();
                  if (!tenGV.isEmpty() && selectedKhoa!=null) {
                	  String maGiangVien  = "GV"+id.TaoId();
                	  String maKhoa= selectedKhoa.getMaKhoa();
                	  try {
                		  db.connect();
                		 String sql = String.format("CALL ThemGiangVien('%s', '%s', '%s')", maGiangVien, tenGV, maKhoa);
                		 db.Excute(sql);
                		  db.close();
                		  JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                		//refresh table	
                		  loadDataTable();
                		  hanhDong="";	  
                		  btnsKhoaMo();
                		  tfTenGV.setText("");
                          cbbTenKhoa.setSelectedIndex(-1);
                          tfTenGV.setEnabled(false);
                          cbbTenKhoa.setEnabled(false);
                	  }
                	  catch (SQLException err) {
              	        JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
              	    }                                  	       	
                  } else {
                      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                  }
            		
            	}
            	else if (hanhDong.equals("sua")) {
            		
            		String tenGV = tfTenGV.getText();
            		
            		int selectedRow = table.getSelectedRow();
            		String maGiangVien = String.valueOf(table.getValueAt(selectedRow, 0));
            		
            		if (!tenGV.isEmpty()) {              	
                  	  try {
                  		Khoa selectedKhoa = (Khoa) cbbTenKhoa.getSelectedItem();
            			String maKhoa = selectedKhoa.getMaKhoa();
                  		  db.connect();
                  		 String sql = String.format("CALL SuaGiangVien('%s', '%s', '%s')", maGiangVien, tenGV, maKhoa);
                  		 db.Excute(sql);
                  		  db.close();
                  		  JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                  		//refresh table	
                  		  loadDataTable();
                  		  hanhDong="";	  
                  		  btnsKhoaMo();
                  		tfTenGV.setText("");
                        cbbTenKhoa.setSelectedIndex(-1);
                        tfTenGV.setEnabled(false);
                        cbbTenKhoa.setEnabled(false);
                  	  }
                  	  catch (SQLException err) {
                	        JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                	    }                                  	       	
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                    }    
                    
            	}
               
            }
        });
	    btnXemTaiKhoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	Object tenSinhVien = table.getValueAt(selectedRow, 0);  // Column 1 (Name)
                    new QuanLyTaiKhoan("LTK002", String.valueOf(tenSinhVien));
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên muốn xem tài khoản!");
                }
            }
        });
	    
	    btnsKhoaMo();
	    loadDataTable();
	    this.setVisible(true);
    }

    // Lớp BackgroundPanel để hiển thị ảnh nền
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
            QuanLyGiangVien frame = new QuanLyGiangVien();
            frame.setVisible(true);
        });
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
    public void loadDataTable() {
    	tableModel.setRowCount(0);
    	try {
    		db.connect();
    		String sql = "Call XemGiangVien()";
    		ResultSet rs = db.Excute(sql);
    		while (rs.next()) {
				String tenGiangVien = rs.getString("TENGIANGVIEN");
				String maGiangVien = rs.getString("MAGIANGVIEN");
				String tenKhoa = rs.getString("TENKHOA");
				String maKhoa = rs.getString("MAKHOA");
				
				tableModel.addRow(new Object[] { maGiangVien, tenGiangVien,tenKhoa, maKhoa});
			}
    		// Load data into the JComboBox
	        sql = "call XemKhoa()";
	        rs = db.Excute(sql);
	        cbbTenKhoa.removeAllItems(); // Clear existing items
	        while (rs.next()) {
	            String maLop = rs.getString("MAKHOA");
	            String tenLop = rs.getString("TENKHOA");
	            // Add Khoa object to JComboBox
	            cbbTenKhoa.addItem(new Khoa(maLop, tenLop));
	        }
	        cbbTenKhoa.setSelectedIndex(-1);
    	}
    	catch (SQLException err) {
  	        JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
  	    }
    } 

}

