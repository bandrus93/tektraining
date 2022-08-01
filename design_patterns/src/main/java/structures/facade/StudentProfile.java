package main.java.structures.facade;

import java.util.List;

public class StudentProfile {
    private List<String> courseCredits;

    public List<String> getCourseCredits() {
        return this.courseCredits;
    }

    public void setCourseCredits(List<String> courses) {
        this.courseCredits = courses;
    }
}
