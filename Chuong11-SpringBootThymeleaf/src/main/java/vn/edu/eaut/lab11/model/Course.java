package vn.edu.eaut.lab11.model;

/**
 * Bài 8: Model khóa học gồm mã môn, tên môn, số tín chỉ.
 */
public class Course {

    private String courseCode;
    private String courseName;
    private int credits;

    public Course() {
    }

    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters & Setters
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + credits + " TC)";
    }
}
