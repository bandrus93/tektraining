package main.java.creators.abstract_factory;

public interface AbstractFactory {
    StudentRepository createStudentRepository();
    CourseRepository createCourseRepository();
}
