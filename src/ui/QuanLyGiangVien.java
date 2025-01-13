package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import database.database;
import helper.Id;

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
    

    public QuanLyGiangVien() {
        setTitle("Quản Lý Giảng Viên");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        tfKhoa = new JTextField();
        tfKhoa.setEnabled(false);

//        inputPanel.add(new JLabel("Mã Giảng Viên:"));
//        inputPanel.add(cbMaGV);
        inputPanel.add(new JLabel("Tên Giảng Viên:"));
        inputPanel.add(tfTenGV);
        inputPanel.add(new JLabel("Khoa"));
        inputPanel.add(tfKhoa);

        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu  = new JButton("Lưu");
         btnHuy = new JButton("Hủy");
         
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
        buttonPanel.add(btnThoat);
        

        tableModel = new DefaultTableModel(new String[]{"Mã GV", "Tên GV", "Khoa"}, 0);
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
                    Object maGiangVien = table.getValueAt(selectedRow, 0);  // Column 0 (ID)
                    Object tenGiangVien = table.getValueAt(selectedRow, 1);  // Column 1 (Name)
                    Object khoa = table.getValueAt(selectedRow, 2);  // Column 2 (Age)
                    // Print the data
                    tfTenGV.setText(String.valueOf(tenGiangVien)); // Convert object to string
                    tfKhoa.setText(String.valueOf(khoa)); 
                }
            }
        });

        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

       

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hanhDong="sua";
                    tfTenGV.setEnabled(true);
                    tfKhoa.setEnabled(true);
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
               tfKhoa.setEnabled(true);
            }
        });
	    btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {           	
            	if(hanhDong.equals("them")) {
            		String tenGV = tfTenGV.getText();
            		String khoa = tfKhoa.getText();
            		
                  if (!tenGV.isEmpty() && !khoa.isEmpty()) {
                	  String maGiangVien  = "GV"+id.TaoId();
                	  try {
                		  db.connect();
                		 String sql = String.format("CALL ThemGiangVien('%s', '%s', '%s')", maGiangVien, tenGV, khoa);
                		 db.Excute(sql);
                		  db.close();
                		  JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                		//refresh table	
                		  loadDataTable();
                		  hanhDong="";	  
                		  btnsKhoaMo();
                		  tfTenGV.setText("");
                          tfKhoa.setText("");
                	  }
                	  catch (SQLException err) {
              	        JOptionPane.showMessageDialog(null, "Lỗi vui lòng thử lại", "Error", JOptionPane.ERROR_MESSAGE);
              	    }                                  	       	
                  } else {
                      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                  }
            		
            	}
            	else if (hanhDong.equals("sua")) {
            		
            		String tenGV = tfTenGV.getText();
            		String khoa = tfKhoa.getText();
            		int selectedRow = table.getSelectedRow();
            		String maGiangVien = String.valueOf(table.getValueAt(selectedRow, 0));
            		if (!tenGV.isEmpty() && !khoa.isEmpty()) {              	
                  	  try {
                  		  db.connect();
                  		 String sql = String.format("CALL SuaGiangVien('%s', '%s', '%s')", maGiangVien, tenGV, khoa);
                  		 db.Excute(sql);
                  		  db.close();
                  		  JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                  		//refresh table	
                  		  loadDataTable();
                  		  hanhDong="";	  
                  		  btnsKhoaMo();
                  		tfTenGV.setText("");
                        tfKhoa.setText("");
                  	  }
                  	  catch (SQLException err) {
                	        JOptionPane.showMessageDialog(null, "Lỗi vui lòng thử lại", "Error", JOptionPane.ERROR_MESSAGE);
                	    }                                  	       	
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                    }    
                    
            	}
               
            }
        });
	    btnsKhoaMo();
	    loadDataTable();
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
				String khoa = rs.getString("KHOA");
				
				tableModel.addRow(new Object[] { maGiangVien, tenGiangVien,khoa  });
			}
    	}
    	catch (SQLException err) {
  	        JOptionPane.showMessageDialog(null, "Lỗi vui lòng thử lại", "Error", JOptionPane.ERROR_MESSAGE);
  	    }
    }
}

