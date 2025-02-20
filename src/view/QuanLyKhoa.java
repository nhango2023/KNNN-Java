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
import helper.Id;

public class QuanLyKhoa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfTenKhoa;
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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyKhoa frame = new QuanLyKhoa();
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
	public QuanLyKhoa() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 831, 530);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Tên khoa:");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(279, 61, 79, 24);
        contentPane.add(lblNewLabel_1);

        tfTenKhoa = new JTextField();
        tfTenKhoa.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tfTenKhoa.setColumns(10);
        tfTenKhoa.setBounds(382, 58, 166, 30);
        contentPane.add(tfTenKhoa);

        JLabel lblNewLabel_2 = new JLabel("Khoa");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 32));
        lblNewLabel_2.setBounds(387, 10, 73, 38);
        contentPane.add(lblNewLabel_2);

        String[] columnNames = {"Mã khoa", "Tên khoa"};
         tableModel = new DefaultTableModel(columnNames, 0); // 0 là số hàng ban đầu

        // Tạo JTable với TableModel
        JTable table = new JTable(tableModel);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object tenKhoa = table.getValueAt(selectedRow, 1);  // Column 2 (Age)
                    // Print the data
                    tfTenKhoa.setText(String.valueOf(tenKhoa)); 
                }
            }
        });

        // Đặt bảng vào JScrollPane để hỗ trợ cuộn
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 141, 807, 348); // Set bounds for the scroll pane
        contentPane.add(scrollPane); // Add JScrollPane to the content pane
        
         btnThem = new JButton("Thêm");
         btnThem.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		hanhDong="them";
         		tfTenKhoa.setText("");
         		tfTenKhoa.setEnabled(true);
         		btnsKhoaMo();
         	}
         });
        btnThem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnThem.setBounds(10, 98, 127, 33);
        contentPane.add(btnThem);
        
         btnSua = new JButton("Sửa");
         btnSua.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    hanhDong="sua";
                    tfTenKhoa.setEnabled(true);
                    btnsKhoaMo();
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần sửa!");
                }
         	}
         });
        btnSua.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnSua.setBounds(147, 98, 127, 33);
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
        btnXoa.setBounds(284, 100, 127, 33);
        contentPane.add(btnXoa);
        
         btnLuu = new JButton("Lưu");
         btnLuu.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		if (hanhDong.equals("them")) {
                   String tenKhoa = tfTenKhoa.getText();
                    if (!tenKhoa.isEmpty()) {
                    	try {
        			        db.connect();
        			        String maKhoa = "KH"+id.TaoId();
        			        String sql =String.format( "CALL ThemKhoa('%s', '%s')",maKhoa, tenKhoa);
        			        db.Excute(sql);
        			        // Show success message
        			        JOptionPane.showMessageDialog(null, "Thêm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
        			        loadDataTable();
        			        btnsKhoaMo();
        			        tfTenKhoa.setEnabled(false);
        			    } catch (SQLException ex) {
        			        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        			    } 
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sinh viên!");
                    }
    		    }
    		    else if (hanhDong.equals("sua")) {           		
            		String tenKhoa = tfTenKhoa.getText();
            		
            		int selectedRow = table.getSelectedRow();
            		String maKhoa = String.valueOf(table.getValueAt(selectedRow, 0));
            		if (!tenKhoa.isEmpty()) {              	
                  	  try {
                  		  db.connect();
                  		 String sql = String.format("CALL SuaKhoa('%s', '%s')", maKhoa, tenKhoa);
                  		 db.Excute(sql);
                  		  db.close();
                  		  JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                  		//refresh table	
                  		  loadDataTable();
                  		  hanhDong="";	  
                  		  btnsKhoaMo();
                  		tfTenKhoa.setText("");                  
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
         
        btnLuu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnLuu.setBounds(421, 100, 127, 33);
        contentPane.add(btnLuu);
        
        btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (hanhDong.equals("sua")) {
        			table.setRowSelectionAllowed(false);        			
        		}
        		hanhDong="";
        		tfTenKhoa.setEnabled(false);
        		tfTenKhoa.setText("");
        		btnsKhoaMo();
        	}
        });
        
        btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        
        
        btnHuy.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnHuy.setBounds(558, 100, 127, 33);
        contentPane.add(btnHuy);
        
       btnThoat = new JButton("Thoát");
        btnThoat.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        btnThoat.setBounds(695, 100, 127, 33);
        contentPane.add(btnThoat);
        tfTenKhoa.setEnabled(false); 
        btnsKhoaMo();
        loadDataTable();
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
	public void loadDataTable() {
        try {
            db.connect();
            String sql = "Call XemKhoa()";
            ResultSet rs = db.Excute(sql);
            tableModel.setRowCount(0);
            while (rs.next()) {
                String maKhoa = rs.getString("MAKHOA");
                String tenKhoa = rs.getString("TENKHOA");

               

                tableModel.addRow(new Object[]{maKhoa, tenKhoa});
            }
            rs.close();  // Close ResultSet
            db.close();  // Close connection
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
