package vn.edu.eaut.lab2;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== LAB 2 - MAVEN PROJECT VA DONG GOI JAR =====");
        System.out.println("    Tinh diem tong ket hoc phan - Java SE Console");
        System.out.println("=================================================");

        System.out.print("Nhap ma sinh vien : ");
        String studentId = scanner.nextLine().trim();

        System.out.print("Nhap ho ten       : ");
        String fullName = scanner.nextLine().trim();

        double attendanceScore = inputScore(scanner, "Diem chuyen can (10%)");
        double midtermScore    = inputScore(scanner, "Diem giua ky    (30%)");
        double finalScore      = inputScore(scanner, "Diem cuoi ky    (60%)");

        Student student = new Student(studentId, fullName,
                                      attendanceScore, midtermScore, finalScore);

        double totalScore = GradeCalculator.calculateFinalScore(student);
        String grade      = GradeCalculator.classify(totalScore);

        System.out.println("\n----- KET QUA HOC PHAN -----");
        System.out.printf("Ma SV       : %s%n",    student.getStudentId());
        System.out.printf("Ho ten      : %s%n",    student.getFullName());
        System.out.printf("Chuyen can  : %.1f%n",  student.getAttendanceScore());
        System.out.printf("Giua ky     : %.1f%n",  student.getMidtermScore());
        System.out.printf("Cuoi ky     : %.1f%n",  student.getFinalScore());
        System.out.printf("Tong ket    : %.2f%n",  totalScore);
        System.out.printf("Xep loai    : %s%n",    grade);
        System.out.println("----------------------------");

        scanner.close();
    }

    private static double inputScore(Scanner scanner, String label) {
        while (true) {
            try {
                System.out.print("Nhap " + label + ": ");
                double score = Double.parseDouble(scanner.nextLine().trim());
                GradeCalculator.validateScore(score, label);
                return score;
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so hop le.");
            } catch (IllegalArgumentException e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }
    }
}
