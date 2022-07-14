package com.luv2code.component;

import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
public class ApplicationExampleTest {

    private static int count = 0;
    @Value("${info.app.name}")
    private String appInfo;
    @Value("${info.app.description}")
    private String appDesc;
    @Value("${info.app.version}")
    private String appVersion;
    @Value("${info.school.name}")
    private String schoolName;
    @Autowired
    CollegeStudent student;
    @Autowired
    StudentGrades studentGrades;
    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void setup() {
        count = count + 1;
        System.out.println("Testing " + appInfo + " which is " + appDesc + " Version: " + appVersion + ". Execution of test method " + count);
        student.setFirstname("Eric");
        student.setLastname("Roby");
        student.setEmailAddress("eric.roby@luv2code_school.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0,85.0,76.50,91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Add grade results for student grades")
    void addGradeResultsForStudentGrades() {
        Assertions.assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Add grade results for student grades not equal")
    void addGradeResultsForStudentGradesAssertNotEquals() {
        Assertions.assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    @DisplayName("Is grade greater")
    void isGradeGreaterForStudentGrades() {
        Assertions.assertTrue(studentGrades.isGradeGreater(90, 75));
    }

    @Test
    @DisplayName("Create student without grades init")
    void createStudentWithoutGradesInit() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Chad");
        studentTwo.setLastname("Darby");
        studentTwo.setEmailAddress("chad.darby@luv2code_school.com");
        Assertions.assertNotNull(studentTwo.getFirstname());
        Assertions.assertNotNull(studentTwo.getLastname());
        Assertions.assertNotNull(studentTwo.getEmailAddress());
        Assertions.assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @Test
    @DisplayName("Verify students are prototypes")
    void verifyStudentsArePrototypes() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        Assertions.assertNotSame(student, studentTwo);
    }

    @Test
    @DisplayName("Find GPA")
    void findGradePointAverage() {
        Assertions.assertAll("Testing all assertEquals",
                () -> Assertions.assertEquals(353.25,
                        studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults())),
                () -> Assertions.assertEquals(88.31,
                        studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults()))
        );
    }

}
