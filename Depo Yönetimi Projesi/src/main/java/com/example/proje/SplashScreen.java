package com.example.proje;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SplashScreen extends JFrame {

    public SplashScreen() {
        // Splash ekranının boyutlarını belirle
        setSize(600, 400);
        setLocationRelativeTo(null); // Ekranın ortasına yerleştir
        setUndecorated(true); // Başlık çubuğunu gizle

        // Arka plan rengi turuncu
        getContentPane().setBackground(new Color(255, 140, 0)); // Koyu turuncu

        // Başlık ekle
        JLabel titleLabel = new JLabel("Altay Depo ve Stok Yönetimi", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Üst boşluk
        add(titleLabel, BorderLayout.CENTER);

        // Animasyon paneli
        AnimationPanel animationPanel = new AnimationPanel();
        animationPanel.setPreferredSize(new Dimension(getWidth(), 50)); // Alt şerit yüksekliği
        add(animationPanel, BorderLayout.SOUTH);

        // Splash ekranını görünür yap
        setVisible(true);

        // Timer ile 3 saniye sonra AnaSayfa.html aç
        Timer timer = new Timer(3000, e -> {
            ((Timer) e.getSource()).stop(); // Timer'ı durdur
            dispose(); // Splash ekranını kapat
            openHtmlPage("src/main/resources/static/AnaSayfa.html"); // AnaSayfa.html dosyasını aç
        });
        timer.setRepeats(false); // Timer yalnızca bir kez çalışsın
        timer.start();
    }

    public static void main(String[] args) {
        // Splash ekranını başlat
        new SplashScreen();
    }

    // Alt şerit animasyon paneli
    static class AnimationPanel extends JPanel {
        private int xOffset = 0;

        public AnimationPanel() {
            Timer timer = new Timer(10, e -> {
                xOffset += 5; // Çubuğun hızını kontrol et
                if (xOffset > getWidth()) {
                    xOffset = -100; // Çubuğu sıfırla
                }
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Arka plan
            g2d.setColor(new Color(230, 230, 230)); // Açık gri arka plan
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Animasyon çubuğu
            g2d.setColor(new Color(255, 69, 0)); // Kırmızımsı turuncu
            g2d.fillRect(xOffset, 10, 100, 30); // Çubuğun boyutları
        }
    }

    // HTML sayfasını açmak için bir metot
    private void openHtmlPage(String filePath) {
        try {
            // HTML dosyasının tam yolu
            File htmlFile = new File(filePath);

            // Varsayılan tarayıcıda aç
            if (htmlFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } else {
                    System.err.println("Desktop özelliği desteklenmiyor!");
                }
            } else {
                System.err.println("HTML dosyası bulunamadı: " + filePath);
            }
        } catch (IOException ex) {
            System.err.println("HTML dosyası açılamadı: " + ex.getMessage());
        }
    }
}
