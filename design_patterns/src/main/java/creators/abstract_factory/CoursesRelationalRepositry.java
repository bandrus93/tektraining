package main.java.creators.abstract_factory;

import java.util.Arrays;
import java.util.List;

public class CoursesRelationalRepositry implements CourseRepository {

    @Override
    public List<String> listCourses() {
        return Arrays.asList("relational course");
    }
    
}
