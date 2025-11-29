/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul7;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author hary
 */
    public class ManajemenNilaiSiswaApp extends JFrame {

        private JTextField txtNama;
        private JTextField txtNilai;
        private JComboBox<String> cmbMatkul;
        private JTable tableData;
        private DefaultTableModel tableModel;
        private JTabbedPane tabbedPane;

        public ManajemenNilaiSiswaApp() {
            // 1. Konfigurasi Frame Utama
            setTitle("Aplikasi Manajemen Nilai Siswa (Final: Tugas 1-4)");
            setSize(500, 450); 
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null); 

            // 2. Inisialisasi Tabbed Pane
            tabbedPane = new JTabbedPane();

            // 3. Membuat Panel untuk Tab 1 (Form Input)
            JPanel panelInput = createInputPanel();
            tabbedPane.addTab("Input Data", panelInput);

            // 4. Membuat Panel untuk Tab 2 (Tabel Data)
            JPanel panelTabel = createTablePanel();
            tabbedPane.addTab("Daftar Nilai", panelTabel);

            // Menambahkan TabbedPane ke Frame
            add(tabbedPane);
        }

        private JPanel createInputPanel() {
            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Komponen Nama
            panel.add(new JLabel("Nama Siswa:"));
            txtNama = new JTextField();
            panel.add(txtNama);

            // Komponen Mata Pelajaran (ComboBox)
            panel.add(new JLabel("Mata Pelajaran:"));
            String[] matkul = {"Matematika Dasar", "Bahasa Indonesia",
                    "Algoritma dan Pemrograman I", "Praktikum Pemrograman II"};
            cmbMatkul = new JComboBox<>(matkul);
            panel.add(cmbMatkul);

            // Komponen Nilai
            panel.add(new JLabel("Nilai (0-100):"));
            txtNilai = new JTextField();
            panel.add(txtNilai);

            // Button reset
            JButton btnReset = new JButton("Reset Form");
            panel.add(btnReset); 
            
            // Tombol Simpan
            JButton btnSimpan = new JButton("Simpan Data");
            panel.add(btnSimpan); 

            // Event Handling Tombol Simpan
            btnSimpan.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    prosesSimpan();
                }
            });

            // Event Handling Tombol Reset (TUGAS 4)
            btnReset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetForm();
                }
            });

            return panel;
        }
    
        private JPanel createTablePanel() {
            JPanel panel = new JPanel(new BorderLayout());

            // Setup Model Tabel (Kolom)
            String[] kolom = {"Nama Siswa", "Mata Pelajaran", "Nilai", "Grade"};
            tableModel = new DefaultTableModel(kolom, 0);

            // Inisialisasi JTable
            tableData = new JTable(tableModel);

            // Membungkus tabel dengan ScrollPane
            JScrollPane scrollPane = new JScrollPane(tableData);
            panel.add(scrollPane, BorderLayout.CENTER);

            // --- TUGAS 2: TOMBOL HAPUS ---
            JButton btnHapus = new JButton("Hapus Data Terpilih");
            panel.add(btnHapus, BorderLayout.SOUTH);

            // Event Handling Tombol Hapus
            btnHapus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hapusData();
                }
            });

            return panel;
        }
        
        private void resetForm() {
            txtNama.setText("");
            txtNilai.setText("");
            cmbMatkul.setSelectedIndex(0);
            txtNama.requestFocus(); 
        }
       
        private void hapusData() {
            int selectedRow = tableData.getSelectedRow();
            
            // Tetap perlu cek -1 agar tidak error/crash
            if (selectedRow != -1) {
                // Langsung hapus tanpa tanya-tanya
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Pilih baris yang ingin dihapus terlebih dahulu!", 
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        }
       
        private void prosesSimpan() {
            // 1. Ambil data dari input
            String nama = txtNama.getText();
            String matkul = (String) cmbMatkul.getSelectedItem();
            String strNilai = txtNilai.getText();

            // 2. VALIDASI INPUT
            // Validasi Kosong
            if (nama.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong!",
                        "Error Validasi", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            if (nama.trim().length() < 3) {
                JOptionPane.showMessageDialog(this, "Nama harus minimal 3 karakter!",
                        "Error Validasi", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // Validasi Angka
            int nilai;
            try {
                nilai = Integer.parseInt(strNilai);
                if (nilai < 0 || nilai > 100) {
                    JOptionPane.showMessageDialog(this, "Nilai harus antara 0-100!",
                            "Error Validasi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nilai harus berupa angka!",
                        "Error Validasi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String grade;
            int range = nilai / 10; 
            switch (range) {
                case 10: 
                case 9: grade = "A"; break;
                case 8: grade = "A"; break;
                case 7: grade = "AB"; break;
                case 6: grade = "B"; break;
                case 5: grade = "BC"; break;
                case 4: grade = "C"; break;
                case 3: grade = "D"; break;
                default: grade = "E"; break;
            }

            // 4. Masukkan ke Tabel
            Object[] dataBaris = {nama, matkul, nilai, grade};
            tableModel.addRow(dataBaris);

            // 5. Reset Form dan Pindah Tab
            resetForm(); 
            
            JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan!");
            tabbedPane.setSelectedIndex(1); 
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new ManajemenNilaiSiswaApp().setVisible(true);
            });
        }
    }
