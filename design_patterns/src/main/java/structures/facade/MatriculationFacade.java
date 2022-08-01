package main.java.structures.facade;

import java.util.Arrays;
import java.util.List;

public class MatriculationFacade {
    //Dependencies would be Autowired in a Spring app
    private final Secretary cleric;
    private Curriculum curriculum;

    public MatriculationFacade(Secretary secretary) {
        this.cleric = secretary;
        this.curriculum = new Curriculum();
    }
    
    public void matriculateStudent() {
        List<String> studies = Arrays.asList("Math", "History", "Science");
        StudentProfile student = cleric.preMatriculate(studies);
        curriculum = curriculum.getProspectus(student);
    }

}
