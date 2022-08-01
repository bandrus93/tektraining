package main.java.structures.facade;

import java.util.Arrays;
import java.util.List;

public class Curriculum {
    List<String> requiredCourses = Arrays.asList("Calculus", "Physics", "Chemistry");
    List<String> optionalCourses = Arrays.asList("Basket Weaving", "Metal Working", "Computer Science");

    public Curriculum getProspectus(StudentProfile student) {
        if(verifyPrerequisites(student)) {
            return this;
        }
        return null;
    }

    public boolean verifyPrerequisites(StudentProfile student) {
        return true;
    }

}
