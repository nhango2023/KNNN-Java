package ui;
import java.awt.BorderLayout;
import java.awt.Component;
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

import database.database;
import helper.Id;

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
    
    public QuanLySinhVien() {
        setTitle("Quản Lý Sinh Viên");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        BackgroundPanel mainPanel = new BackgroundPanel("src/images/sv.jpg");
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setOpaque(false);

        tfTenSV = new JTextField();

        JTextField tfLop  = new JTextField();

        
        
        inputPanel.add(new JLabel("Tên Sinh Viên:"));
        inputPanel.add(tfTenSV);
        
        inputPanel.add(new JLabel("Lớp:"));
        inputPanel.add(tfLop);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        JButton btnThoat = new JButton("Thoát");
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnThoat);

        
        tableModel = new DefaultTableModel(new String[]{"Mã SV", "Tên SV", "Lớp", "Tài khoản"}, 0);
        table = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only make the button column editable
            }
        };
        
        table.getColumn("Tài khoản").setCellRenderer(new ButtonRenderer());
        table.getColumn("Tài khoản").setCellEditor(new ButtonEditor(new JCheckBox()));

        
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
                    Object lop = table.getValueAt(selectedRow, 2);  // Column 2 (Age)
                    // Print the data
                    tfTenSV.setText(String.valueOf(tenSinhVien)); // Convert object to string
                    tfLop.setText(String.valueOf(lop)); 
                }
            }
        });
        
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	tfTenSV.setText("");
            	tfLop.setText("");
            	hanhDong="them";
                btnsKhoaMo();
                tfTenSV.setEnabled(true);
                tfLop.setEnabled(true);
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hanhDong="sua";
                    tfTenSV.setEnabled(true);
                    tfLop.setEnabled(true);
                    btnsKhoaMo(); 
                    tfTenSV.setText(String.valueOf(table.getValueAt(selectedRow, 1)));
                    tfLop.setText(String.valueOf(table.getValueAt(selectedRow, 2)));              
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
                }
            }
            
        });
        
        
        btnLuu.addActionListener(e -> { 
		    if (hanhDong.equals("them")) {
		    	// Check if text field is empty
                String tenSV = tfTenSV.getText();
               String lop = tfLop.getText();

                if (!tenSV.isEmpty()||!lop.isEmpty()) {
                	try {
    			        db.connect();
    			        String maSinhVien = "SV"+id.TaoId();
    			        String sql =String.format( "CALL ThemSinhVien('%s', '%s', '%s')",maSinhVien, tenSV, lop);
    			        db.Excute(sql);
    			        // Show success message
    			        JOptionPane.showMessageDialog(null, "Thêm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
    			        loadDataTable();
    			    } catch (SQLException ex) {
    			        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    			    } 
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sinh viên!");
                }
		    }
		    else if (hanhDong.equals("sua")) {
        		
        		String tenSV = tfTenSV.getText();
        		String lop = tfLop.getText();
        		int selectedRow = table.getSelectedRow();
        		String maSinhVien = String.valueOf(table.getValueAt(selectedRow, 0));
        		if (!tenSV.isEmpty() && !lop.isEmpty()) {              	
              	  try {
              		  db.connect();
              		 String sql = String.format("CALL SuaSinhVien('%s', '%s', '%s')", maSinhVien, tenSV, lop);
              		 db.Excute(sql);
              		  db.close();
              		  JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
              		//refresh table	
              		  loadDataTable();
              		  hanhDong="";	  
              		  btnsKhoaMo();
              		tfTenSV.setText("");
                    tfLop.setText("");
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
        tfTenSV.setEnabled(false);
        tfLop.setEnabled(false);
        loadDataTable();
        btnsKhoaMo();
    }
    
 // Custom renderer for displaying buttons in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                JButton button = (JButton) value;
                return button;
            }
            return this;
        }
    }

    // Custom editor for handling button clicks in the table
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                button = (JButton) value;
                label = button.getText();
            }
            isPushed = true;
            return button;
        }

        
        public Object getCellEditorValue() {
            isPushed = false;
            return button;
        }

        
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
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
    
    public void loadDataTable() {
        try {
            db.connect();
            String sql = "Call XemSinhVien()";
            ResultSet rs = db.Excute(sql);
            tableModel.setRowCount(0);
            while (rs.next()) {
                String tenSinhVien = rs.getString("TENSINHVIEN");
                String maSinhVien = rs.getString("MASINHVIEN");
                String maTaiKhoan = rs.getString("MATAIKHOAN");
                String lop = rs.getString("LOP");

                JButton btn = new JButton("Xem tài khoản");
                btn.addActionListener(e -> {
                    QuanLyTaiKhoan frmQuanLyTaiKhoan = new QuanLyTaiKhoan();
                    frmQuanLyTaiKhoan.setVisible(true);
                });

                tableModel.addRow(new Object[]{maSinhVien, tenSinhVien, lop, btn});
            }
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
