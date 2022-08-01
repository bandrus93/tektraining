package main.java.creators.abstract_factory;

public class NoSqlFactory implements AbstractFactory {

    @Override
    public StudentRepository createStudentRepository() {
        return new StudentNoSqlRepository();
    }

    @Override
    public CourseRepository createCourseRepository() {
        return new CourseNoSqlRepository();
    }
    
}
