package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLySinhVien extends JFrame {
    private JComboBox<String> cbMaSV;
    private JTextField tfTenSV;
//    private JComboBox<String> cbLop;
    private JButton btnThem, btnSua, btnXoa;
    private JTable table;
    private DefaultTableModel tableModel;

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

//        cbMaSV = new JComboBox<>(new String[]{"SV01", "SV02", "SV03"});
        tfTenSV = new JTextField();
//        cbLop = new JComboBox<>(new String[]{"Lớp A", "Lớp B", "Lớp C"});
        JTextField tfLop  = new JTextField();
//        inputPanel.add(new JLabel("Mã Sinh Viên:"));
//        inputPanel.add(cbMaSV);
        
        
        inputPanel.add(new JLabel("Tên Sinh Viên:"));
        inputPanel.add(tfTenSV);
        
        inputPanel.add(new JLabel("Lớp:"));
        inputPanel.add(tfLop);
//        inputPanel.add(cbLop);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        
        tableModel = new DefaultTableModel(new String[]{"Mã SV", "Tên SV", "Lớp"}, 0);
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
//        String maSV = (String) cbMaSV.getSelectedItem();
//                String tenSV = tfTenSV.getText();
//               String lop = (String) cbLop.getSelectedItem();
//
//                if (!tenSV.isEmpty()) {
//                    tableModel.addRow(new Object[]{maSV, tenSV, lop});
//                    tfTenSV.setText(""); 
//                } else {
//                    JOptionPane.showMessageDialog(null, "Vui lòng nhập tên sinh viên!");
//                }
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
//                    tableModel.setValueAt(cbMaSV.getSelectedItem(), selectedRow, 0);
                    tableModel.setValueAt(tfTenSV.getText(), selectedRow, 1);
//                    tableModel.setValueAt(cbLop.getSelectedItem(), selectedRow, 2);
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
//    public void btnsKhoaMo () {
//		if(hanhDong.equals("")) {
//			btnThem.setEnabled(true);
//			btnSua.setEnabled(true);
//			btnXoa.setEnabled(true);
//			btnLuu.setEnabled(false);
//			btnHuy.setEnabled(false);
//		}else if(hanhDong.equals("them") || hanhDong.equals("sua")) {
//			btnThem.setEnabled(false);
//			btnSua.setEnabled(false);
//			btnXoa.setEnabled(false);
//			btnLuu.setEnabled(true);
//			btnHuy.setEnabled(true);
//		}
//	}
}
