package main.java.creators.abstract_factory;

import java.util.Arrays;
import java.util.List;

public class StudentsRelationalRepository implements StudentRepository {

    @Override
    public List<String> listStudents() {
        return Arrays.asList("relational student");
    }
    
}
