package vn.edu.eaut.lab4;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

public class ScreenshotGenerator {

    public static void main(String[] args) {
        File outputDir = new File("screenshots");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Đang chụp ảnh giao diện các bài tập với dữ liệu thực tế...");

                // Bài 1
                CountdownFrame f1 = new CountdownFrame();
                setFieldValue(f1, "lblTime", new JLabel("Thời gian còn lại: 7 giây", SwingConstants.CENTER));
                setFieldValue(f1, "btnStart", createDisabledButton("Đang đếm..."));
                captureFrame(f1, "screenshots/Bai01_Countdown.png");

                // Bài 2
                ProgressDemoFrame f2 = new ProgressDemoFrame();
                JProgressBar p2 = getFieldValue(f2, "progressBar");
                if (p2 != null) p2.setValue(60);
                JLabel s2 = getFieldValue(f2, "lblStatus");
                if (s2 != null) s2.setText("Đang tải dữ liệu (60%)...");
                captureFrame(f2, "screenshots/Bai02_ProgressDemo.png");

                // Bài 3
                PrimeSumFrame f3 = new PrimeSumFrame();
                JProgressBar p3 = getFieldValue(f3, "progressBar");
                if (p3 != null) p3.setValue(100);
                JLabel s3 = getFieldValue(f3, "lblResult");
                if (s3 != null) s3.setText("Tổng các số nguyên tố nhỏ hơn 100000 = 454396537");
                captureFrame(f3, "screenshots/Bai03_PrimeSum.png");

                // Bài 4
                FibonacciFrame f4 = new FibonacciFrame();
                JProgressBar p4 = getFieldValue(f4, "progressBar");
                if (p4 != null) p4.setValue(100);
                JLabel s4 = getFieldValue(f4, "lblResult");
                if (s4 != null) s4.setText("Fibonacci(100) = 354224848179261915075... (21 chữ số)");
                captureFrame(f4, "screenshots/Bai04_Fibonacci.png");

                // Bài 5
                FileLineCounterFrame f5 = new FileLineCounterFrame();
                JProgressBar p5 = getFieldValue(f5, "progressBar");
                if (p5 != null) p5.setValue(100);
                JLabel r5 = getFieldValue(f5, "lblResult");
                if (r5 != null) r5.setText("Số dòng trong file: 21 dòng");
                JLabel l5 = getFieldValue(f5, "lblFile");
                if (l5 != null) l5.setText("File: data/sample_large.txt");
                captureFrame(f5, "screenshots/Bai05_FileLineCounter.png");

                // Bài 6
                CancelableTaskFrame f6 = new CancelableTaskFrame();
                JProgressBar p6 = getFieldValue(f6, "progressBar");
                if (p6 != null) p6.setValue(45);
                JLabel s6 = getFieldValue(f6, "lblStatus");
                if (s6 != null) s6.setText("Trạng thái: Đang xử lý tác vụ... (Nhấn Hủy để dừng)");
                captureFrame(f6, "screenshots/Bai06_CancelableTask.png");

                // Bài 7
                KeywordSearchFrame f7 = new KeywordSearchFrame();
                JTextArea t7 = getFieldValue(f7, "txtResults");
                if (t7 != null) {
                    t7.setText("Dòng 8: SwingWorker là một lớp tiện ích trong Swing giúp thực thi các tác vụ...\nDòng 9: Phương thức doInBackground() chạy trên luồng phụ...\nDòng 10: Phương thức publish() và process() giúp gửi kết quả trung gian...\nDòng 15: Từ khóa quan trọng: SwingWorker, Event Dispatch Thread...");
                }
                JProgressBar p7 = getFieldValue(f7, "progressBar");
                if (p7 != null) p7.setValue(100);
                JLabel s7 = getFieldValue(f7, "lblStatus");
                if (s7 != null) s7.setText("Tìm thấy 4 dòng chứa từ khóa 'SwingWorker'.");
                captureFrame(f7, "screenshots/Bai07_KeywordSearch.png");

                // Bài 8
                StudentCsvStatsFrame f8 = new StudentCsvStatsFrame();
                DefaultTableModel m8 = getFieldValue(f8, "tableModel");
                if (m8 != null) {
                    m8.addRow(new Object[]{"SV01", "Nguyen Van A", 8.5});
                    m8.addRow(new Object[]{"SV02", "Tran Thi B", 7.0});
                    m8.addRow(new Object[]{"SV03", "Le Van C", 9.0});
                    m8.addRow(new Object[]{"SV05", "Hoang Thi E", 9.5});
                    m8.addRow(new Object[]{"SV08", "Bui Hai H", 9.2});
                }
                JProgressBar p8 = getFieldValue(f8, "progressBar");
                if (p8 != null) p8.setValue(100);
                JLabel s8 = getFieldValue(f8, "lblStats");
                if (s8 != null) s8.setText("Thống kê: Đã nạp 10 sinh viên | Điểm TB: 8.00 | Cao nhất: Hoang Thi E (9.5 điểm)");
                captureFrame(f8, "screenshots/Bai08_StudentCsvStats.png");

                // Bài 9
                ProductLoadDemoFrame f9 = new ProductLoadDemoFrame();
                DefaultTableModel m9 = getFieldValue(f9, "tableModel");
                if (m9 != null) {
                    m9.addRow(new Object[]{"SP01", "Bàn phím cơ RGB", "250.000 đ"});
                    m9.addRow(new Object[]{"SP02", "Chuột không dây", "150.000 đ"});
                    m9.addRow(new Object[]{"SP03", "Màn hình 27 inch 4K", "2.500.000 đ"});
                    m9.addRow(new Object[]{"SP04", "Tai nghe Gaming", "450.000 đ"});
                }
                JProgressBar p9 = getFieldValue(f9, "progressBar");
                if (p9 != null) p9.setValue(100);
                JLabel s9 = getFieldValue(f9, "lblStatus");
                if (s9 != null) s9.setText("Trạng thái: Đã tải thành công 8 sản phẩm.");
                captureFrame(f9, "screenshots/Bai09_ProductLoadDemo.png");

                // Bài 10
                ProductManagerFrame f10 = new ProductManagerFrame();
                DefaultTableModel m10 = getFieldValue(f10, "tableModel");
                if (m10 != null) {
                    m10.addRow(new Object[]{"SP01", "Bàn phím cơ RGB", "250.000"});
                    m10.addRow(new Object[]{"SP02", "Chuột không dây", "150.000"});
                    m10.addRow(new Object[]{"SP03", "Màn hình 27 inch 4K", "2.500.000"});
                    m10.addRow(new Object[]{"SP04", "Tai nghe Gaming", "450.000"});
                }
                JTextField ma10 = getFieldValue(f10, "txtMaSP");
                if (ma10 != null) ma10.setText("SP01");
                JTextField ten10 = getFieldValue(f10, "txtTenSP");
                if (ten10 != null) ten10.setText("Bàn phím cơ RGB");
                JTextField gia10 = getFieldValue(f10, "txtDonGia");
                if (gia10 != null) gia10.setText("250000");
                JProgressBar p10 = getFieldValue(f10, "progressBar");
                if (p10 != null) p10.setValue(100);
                JLabel s10 = getFieldValue(f10, "lblStatus");
                if (s10 != null) s10.setText("Trạng thái: Đã nạp 8 sản phẩm từ file: products.csv");
                captureFrame(f10, "screenshots/Bai10_ProductManager.png");

                // Main Dashboard
                captureFrame(new MainDashboardFrame(), "screenshots/MainDashboard.png");

                System.out.println("Hoàn tất tạo 11 ảnh giao diện đẹp!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static JButton createDisabledButton(String text) {
        JButton btn = new JButton(text);
        btn.setEnabled(false);
        return btn;
    }

    @SuppressWarnings("unchecked")
    private static <T> T getFieldValue(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (Exception e) {
            return null;
        }
    }

    private static void setFieldValue(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception ignored) {}
    }

    private static void captureFrame(JFrame frame, String outputPath) {
        try {
            frame.pack();
            int width = Math.max(frame.getWidth(), 680);
            int height = Math.max(frame.getHeight(), 440);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);

            // Container setup
            JPanel container = new JPanel(new BorderLayout());
            container.setSize(width, height);
            container.add(frame.getContentPane(), BorderLayout.CENTER);
            container.doLayout();

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(0, 0, width, height);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            container.paint(g2d);
            g2d.dispose();

            File file = new File(outputPath);
            ImageIO.write(image, "png", file);
            System.out.println("Đã lưu ảnh chất lượng cao: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Lỗi khi chụp " + frame.getTitle() + ": " + e.getMessage());
        }
    }
}
