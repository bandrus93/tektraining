package main.java.structures.facade;

import java.util.List;

public class Secretary {

    public StudentProfile preMatriculate(List<String> studies) {
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setCourseCredits(studies);
        return studentProfile;
    }
    
}
