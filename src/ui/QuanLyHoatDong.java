package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuanLyHoatDong extends JFrame {
	  private static final long serialVersionUID = 1L;
    private JComboBox<String> cbMaHoatDong, cbMaGV, cbMaDonVi;
    private JTextField tfTenHoatDong;
    private JSpinner dpNgayBatDau, dpNgayKetThuc;
    private JButton btnThem, btnSua, btnXoa;
    private JTable table;
    private DefaultTableModel tableModel;

    public QuanLyHoatDong() {
        setTitle("Quản Lý Hoạt Động");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

    
        JLabel lblMaHoatDong = new JLabel("Mã Hoạt Động:");
        cbMaHoatDong = new JComboBox<>(new String[]{"HD01", "HD02", "HD03"});
        JLabel lblTenHoatDong = new JLabel("Tên Hoạt Động:");
        tfTenHoatDong = new JTextField();
        JLabel lblMaGV = new JLabel("Mã Giảng Viên:");
        cbMaGV = new JComboBox<>(new String[]{"GV01", "GV02", "GV03"});
        JLabel lblNgayBatDau = new JLabel("Ngày Bắt Đầu:");
        dpNgayBatDau = new JSpinner(new SpinnerDateModel());
        JLabel lblNgayKetThuc = new JLabel("Ngày Kết Thúc:");
        dpNgayKetThuc = new JSpinner(new SpinnerDateModel());
        JLabel lblMaDonVi = new JLabel("Mã Đơn Vị:");
        cbMaDonVi = new JComboBox<>(new String[]{"DV01", "DV02", "DV03"});

      
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.weightx = 0.2;
        inputPanel.add(lblMaHoatDong, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 0.8;
        inputPanel.add(cbMaHoatDong, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(lblTenHoatDong, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        inputPanel.add(tfTenHoatDong, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(lblMaGV, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(cbMaGV, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(lblNgayBatDau, gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        inputPanel.add(dpNgayBatDau, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(lblNgayKetThuc, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        inputPanel.add(dpNgayKetThuc, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(lblMaDonVi, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        inputPanel.add(cbMaDonVi, gbc);

    
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

     
        tableModel = new DefaultTableModel(new String[]{"Mã HD", "Tên HD", "Mã GV", "Ngày BĐ", "Ngày KT", "Mã Đơn Vị"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

     
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tabHoatDong.add(topPanel, BorderLayout.NORTH);
        tabHoatDong.add(scrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Quản Lý Hoạt Động", tabHoatDong);

        add(tabbedPane);

   
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maHD = (String) cbMaHoatDong.getSelectedItem();
                String tenHD = tfTenHoatDong.getText();
                String maGV = (String) cbMaGV.getSelectedItem();
                String ngayBD = new SimpleDateFormat("dd/MM/yyyy").format((Date) dpNgayBatDau.getValue());
                String ngayKT = new SimpleDateFormat("dd/MM/yyyy").format((Date) dpNgayKetThuc.getValue());
                String maDonVi = (String) cbMaDonVi.getSelectedItem();

                if (!tenHD.isEmpty()) {
                    tableModel.addRow(new Object[]{maHD, tenHD, maGV, ngayBD, ngayKT, maDonVi});
                    tfTenHoatDong.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                }
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.setValueAt(cbMaHoatDong.getSelectedItem(), selectedRow, 0);
                    tableModel.setValueAt(tfTenHoatDong.getText(), selectedRow, 1);
                    tableModel.setValueAt(cbMaGV.getSelectedItem(), selectedRow, 2);
                    tableModel.setValueAt(new SimpleDateFormat("dd/MM/yyyy").format((Date) dpNgayBatDau.getValue()), selectedRow, 3);
                    tableModel.setValueAt(new SimpleDateFormat("dd/MM/yyyy").format((Date) dpNgayKetThuc.getValue()), selectedRow, 4);
                    tableModel.setValueAt(cbMaDonVi.getSelectedItem(), selectedRow, 5);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuanLyHoatDong frame = new QuanLyHoatDong();
            frame.setVisible(true);
        });
    }
}
