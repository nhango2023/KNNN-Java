package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String HanhDong="";
    private database db = new database();

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
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");
        JButton btnThoat = new JButton("Thoát");
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
                    tableModel.setValueAt(cbMaGV.getSelectedItem(), selectedRow, 0);
                    tableModel.setValueAt(tfTenGV.getText(), selectedRow, 1);
                    tableModel.setValueAt(tfKhoa.getText(), selectedRow, 2);
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
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
               tfTenGV.setEnabled(true);
               tfKhoa.setEnabled(true);
               HanhDong="Them";
            }
        });
	    btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(HanhDong=="Them") {
            		String tenGV = tfTenGV.getText();
            		String khoa = tfKhoa.getText();
            		
                  if (!tenGV.isEmpty() && !khoa.isEmpty()) {
                	  try {
                		  db.connect();
//                		  String sql = String.format("CALL  ", maTaiKhoan);
                		  db.close();
                		  btnThem.setEnabled(true);
                          btnSua.setEnabled(true);
                          btnXoa.setEnabled(true);
                          btnLuu.setEnabled(false);
                          btnHuy.setEnabled(false);
                         tfTenGV.setEnabled(false);
                         tfKhoa.setEnabled(false);
                         HanhDong="";
                         
                	  }
                	  catch (SQLException err) {
              	        err.printStackTrace();
              	        JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
              	    }
                      
                	  
                	  
                	//refresh table
      				
                  } else {
                      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
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
}

