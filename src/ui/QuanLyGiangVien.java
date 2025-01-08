package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyGiangVien extends JFrame {
    private JComboBox<String> cbMaGV;
    private JTextField tfTenGV;
    private JTextField tfKhoa;
    private JButton btnThem, btnSua, btnXoa;
    private JTable table;
    private DefaultTableModel tableModel;

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

       
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maGV = (String) cbMaGV.getSelectedItem();
                String tenGV = tfTenGV.getText();
                String sdt = tfKhoa.getText();

                if (!tenGV.isEmpty() && !sdt.isEmpty()) {
                    tableModel.addRow(new Object[]{maGV, tenGV, sdt});
                    tfTenGV.setText(""); 
                    tfKhoa.setText("");
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

