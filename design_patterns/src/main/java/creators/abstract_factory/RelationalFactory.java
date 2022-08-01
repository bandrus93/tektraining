package main.java.creators.abstract_factory;

public class RelationalFactory implements AbstractFactory {

    @Override
    public StudentRepository createStudentRepository() {
        return new StudentsRelationalRepository();
    }

    @Override
    public CourseRepository createCourseRepository() {
        return new CoursesRelationalRepositry();
    }
    
}
