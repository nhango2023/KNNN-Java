package view;

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
import javax.swing.table.TableColumn;

import database.database;
import helper.Id;
import model.Khoa;


public class QuanLyLop extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTenLop;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLuu;
	private JButton btnHuy;
	private JButton btnThoat;
	private String hanhDong = "";
	private database db = new database();
	private Id id  = new Id();
	private DefaultTableModel tableModel;
	private JLabel lblNewLabel;
	private JComboBox cbbTenKhoa;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyLop frame = new QuanLyLop();
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
	public QuanLyLop() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 831, 530);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Tên lớp");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(279, 61, 79, 24);
        contentPane.add(lblNewLabel_1);

        tfTenLop = new JTextField();
        tfTenLop.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tfTenLop.setColumns(10);
        tfTenLop.setBounds(382, 58, 166, 30);
        contentPane.add(tfTenLop);

        JLabel lblNewLabel_2 = new JLabel("Lớp");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 32));
        lblNewLabel_2.setBounds(387, 10, 73, 38);
        contentPane.add(lblNewLabel_2);

        String[] columnNames = {"Mã lớp", "Tên lớp", "Tên khoa", "Mã khoa"};
         tableModel = new DefaultTableModel(columnNames, 0);

        // Tạo JTable với TableModel
        JTable table = new JTable(tableModel);
        TableColumn column = table.getColumnModel().getColumn(3);
        table.getColumnModel().removeColumn(column);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                	 int modelRow = table.convertRowIndexToModel(selectedRow); // Convert view index to model index
                	    String maKhoa = String.valueOf(tableModel.getValueAt(modelRow, 3)); // Access "Mã khoa" column
                	    String tenLop = String.valueOf(tableModel.getValueAt(modelRow, 1)); // Access "Mã khoa" column
                	    tfTenLop.setText(tenLop);
                	    for (int i = 0; i < cbbTenKhoa.getItemCount(); i++) {
                	        Khoa khoa = (Khoa) cbbTenKhoa.getItemAt(i); // Assuming Khoa is the object used in JComboBox
                	        if (khoa.getMaKhoa().equals(maKhoa)) { // Compare "Mã khoa"
                	            cbbTenKhoa.setSelectedIndex(i); // Set the matching item as selected
                	            cbbTenKhoa.setEnabled(false);
                	            break;
                	        }
                	    }
                }
            }
        });

        // Đặt bảng vào JScrollPane để hỗ trợ cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 269, 807, 220); // Set bounds for the scroll pane
        contentPane.add(scrollPane); // Add JScrollPane to the content pane
        
         btnThem = new JButton("Thêm");
         btnThem.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		hanhDong="them";
         		tfTenLop.setText("");
         		tfTenLop.setEnabled(true);
         		cbbTenKhoa.setEnabled(true);
         		cbbTenKhoa.setSelectedIndex(-1);
         		btnsKhoaMo();
         	}
         });
        btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnThem.setBounds(10, 202, 127, 33);
        contentPane.add(btnThem);
        
         btnSua = new JButton("Sửa");
         btnSua.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hanhDong="sua";
                    tfTenLop.setEnabled(true);
                    btnsKhoaMo();
                    cbbTenKhoa.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
                }
         	}
         });
        btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnSua.setBounds(147, 202, 127, 33);
        contentPane.add(btnSua);
        
         btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xóa!");
                }
        	}
        });
        btnXoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnXoa.setBounds(284, 204, 127, 33);
        contentPane.add(btnXoa);
        
         btnLuu = new JButton("Lưu");
         btnLuu.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		if (hanhDong.equals("them")) {
                   String tenLop = tfTenLop.getText();
                   Khoa selectedKhoa = (Khoa) cbbTenKhoa.getSelectedItem();
                    if (!tenLop.isEmpty() || selectedKhoa != null) {
                    	try {
        			        db.connect();
        			        String maKhoa = selectedKhoa.getMaKhoa();
        			        String maLop = "LP"+id.TaoId();
        			        String sql =String.format( "CALL ThemLop('%s', '%s', '%s')",maLop, tenLop, maKhoa);
        			        db.Excute(sql);
        			        // Show success message
        			        JOptionPane.showMessageDialog(null, "Thêm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
        			        loadData();
        			        btnsKhoaMo();
        			        tfTenLop.setEnabled(false);
        			        cbbTenKhoa.setEditable(false);
        			        cbbTenKhoa.setSelectedIndex(-1);
        			    } catch (SQLException ex) {
        			        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        			    } 
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập hoặc chọn đủ thông tin!");
                    }
    		    }
    		    else if (hanhDong.equals("sua")) {           		
            		String tenLop = tfTenLop.getText();            		
            		if (!tenLop.isEmpty()) { 
            			int selectedRow = table.getSelectedRow();
            			Khoa selectedKhoa = (Khoa) cbbTenKhoa.getSelectedItem();
            			String maKhoa = selectedKhoa.getMaKhoa();
                		String maLop = String.valueOf(table.getValueAt(selectedRow, 0));
                  	  try {
                  		  db.connect();
                  		 String sql = String.format("CALL SuaLop('%s', '%s', '%s')", maLop, tenLop, maKhoa);
                  		 db.Excute(sql);
                  		  db.close();
                  		  JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                  		//refresh table	
                  		  loadData();
                  		  hanhDong="";	  
                  		  btnsKhoaMo();
                  		tfTenLop.setText("");
                  		tfTenLop.setEnabled(false);
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
         
        btnLuu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnLuu.setBounds(421, 204, 127, 33);
        contentPane.add(btnLuu);
        
        btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (hanhDong.equals("sua")) {
        			table.setRowSelectionAllowed(false);        			
        		}
        		hanhDong="";
        		tfTenLop.setEnabled(false);
        		tfTenLop.setText("");
        		btnsKhoaMo();
        	}
        });
        btnHuy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnHuy.setBounds(558, 204, 127, 33);
        contentPane.add(btnHuy);
        
       btnThoat = new JButton("Thoát");
        btnThoat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnThoat.setBounds(695, 204, 127, 33);
        contentPane.add(btnThoat);
        
        btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        
        tfTenLop.setEnabled(false); 
        
        lblNewLabel = new JLabel("Tên khoa");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel.setBounds(279, 112, 79, 24);
        contentPane.add(lblNewLabel);
        
		cbbTenKhoa = new JComboBox();
		cbbTenKhoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cbbTenKhoa.setBounds(382, 104, 376, 32);
		contentPane.add(cbbTenKhoa);
        
       tfTenLop.setEnabled(false);
       cbbTenKhoa.setEnabled(false);
        btnsKhoaMo();
        loadData();
        this.setVisible(true);
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
	public void loadData() {
	    try {
	        db.connect();
	        String sql = "Call XemLop()";
	        ResultSet rs = db.Excute(sql);
	        tableModel.setRowCount(0);

	        // Load data into the table
	        while (rs.next()) {
	            String maLop = rs.getString("MALOP");
	            String tenLop = rs.getString("TENLOP");
	            String tenKhoa = rs.getString("TENKHOA");
	            String maKhoa = rs.getString("MAKHOA");

	            tableModel.addRow(new Object[]{maLop, tenLop, tenKhoa, maKhoa});
	        }

	        // Load data into the JComboBox
	        sql = "call XemKhoa()";
	        rs = db.Excute(sql);
	        cbbTenKhoa.removeAllItems(); // Clear existing items
	        while (rs.next()) {
	            String maKhoa = rs.getString("MAKHOA");
	            String tenKhoa = rs.getString("TENKHOA");
	            // Add Khoa object to JComboBox
	            cbbTenKhoa.addItem(new Khoa(maKhoa, tenKhoa));
	        }
	        rs.close();  // Close ResultSet
	        db.close();  // Close connection
	     // After populating the JComboBox
	        cbbTenKhoa.setSelectedIndex(-1); // Ensures no item is selected
	    } catch (SQLException err) {
	        JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

}
