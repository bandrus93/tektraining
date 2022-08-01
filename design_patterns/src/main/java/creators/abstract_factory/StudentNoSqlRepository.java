package main.java.creators.abstract_factory;

import java.util.Arrays;
import java.util.List;

public class StudentNoSqlRepository implements StudentRepository {

    @Override
    public List<String> listStudents() {
        return Arrays.asList("non-relational student");
    }
    
}
