package main.java.creators.abstract_factory;

import java.util.Arrays;
import java.util.List;

public class CourseNoSqlRepository implements CourseRepository {

    @Override
    public List<String> listCourses() {
        return Arrays.asList("non-relational course");
    }
    
}
