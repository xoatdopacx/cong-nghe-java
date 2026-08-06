package vn.edu.eaut.lab2;

public class GradeCalculator {

    /**
     * Điểm tổng kết = chuyên cần x 10% + giữa kỳ x 30% + cuối kỳ x 60%
     */
    public static double calculateFinalScore(Student student) {
        return student.getAttendanceScore() * 0.10
             + student.getMidtermScore()    * 0.30
             + student.getFinalScore()      * 0.60;
    }

    /**
     * Xếp loại: A >= 8.5 | B >= 7.0 | C >= 5.5 | D >= 4.0 | F < 4.0
     */
    public static String classify(double score) {
        if (score >= 8.5) return "A";
        if (score >= 7.0) return "B";
        if (score >= 5.5) return "C";
        if (score >= 4.0) return "D";
        return "F";
    }

    /**
     * Kiểm tra điểm hợp lệ trong khoảng [0, 10]
     */
    public static void validateScore(double score, String fieldName) {
        if (score < 0 || score > 10) {
            throw new IllegalArgumentException(
                fieldName + " phai nam trong khoang 0 den 10, gia tri nhap: " + score);
        }
    }
}
