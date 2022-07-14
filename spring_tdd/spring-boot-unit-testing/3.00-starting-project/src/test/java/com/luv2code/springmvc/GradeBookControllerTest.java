package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.repositories.MathGradesDao;
import com.luv2code.springmvc.repositories.StudentDao;
import com.luv2code.springmvc.services.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class GradeBookControllerTest {
    private static MockHttpServletRequest request;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private MathGradesDao mathGradesDao;
    @Autowired
    private StudentAndGradeService studentService;
    @Mock
    private StudentAndGradeService studentCreateServiceMock;
    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    @BeforeAll
    public static void setup() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Chad");
        request.setParameter("lastname", "Darby");
        request.setParameter("emailAddress", "chad.darby@luv2code_school.com");
    }

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }

    @Test
    void getStudentsHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby", "eric.roby@luv2code_school.com");
        CollegeStudent studentTwo = new GradebookCollegeStudent("Chad", "Darby", "chad.darby@luv2code_school.com");
        List<CollegeStudent> studentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(studentList);
        assertIterableEquals(studentList, studentCreateServiceMock.getGradeBook());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "index");
    }

    @Test
    void createStudentHttpRequest() throws Exception {
        CollegeStudent studentOne = new CollegeStudent("Eric", "Roby", "eric.roby@luv2code_school.com");
        List<CollegeStudent> studentList = new ArrayList<>(Arrays.asList(studentOne));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(studentList);
        assertIterableEquals(studentList, studentCreateServiceMock.getGradeBook());
        MvcResult result = this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstname", request.getParameterValues("firstname"))
                .param("lastname", request.getParameterValues("lastname"))
                .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();
        ModelAndView modelAndView = result.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        CollegeStudent verifyStudent = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");
        assertNotNull(verifyStudent);
    }

    @Test
    void deleteStudentHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/delete/student/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "index");
        assertFalse(studentDao.findById(1).isPresent());
    }

    @Test
    void deleteStudentHttpRequestError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/delete/student/{id}", 0))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

    @Test
    void studentInformationHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/studentInformation/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
    }

    @Test
    void studentInformationHttpStudentDoesNotExistRequest() throws Exception {
        assertFalse(studentDao.findById(0).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/studentInformation/{id}", 0))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

    @Test
    void createValidGradeHttpRequest() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        GradebookCollegeStudent student = studentService.getStudentInformation(1);
        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());
        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("course", "math")
                .param("studentId", "1"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
        student = studentService.getStudentInformation(1);
        assertEquals(2, student.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    void createValidGradeEmptyResponseHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("course", "history")
                .param("studentId", "0"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView,"error");
    }

    @Test
    void createNonValidGradeHttpRequestCourseDoesNotExistEmptyResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("course", "literature")
                .param("studentId", "1"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

    @Test
    void deleteValidGradeHttpRequest() throws Exception {
        Optional<MathGrade> mathGrade = mathGradesDao.findById(1);
        assertTrue(mathGrade.isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/grades/{id}/{course}", 1, "math"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
        mathGrade = mathGradesDao.findById(1);
        assertFalse(mathGrade.isPresent());
    }

    @Test
    void deleteValidGradeHttpRequestStudentIdDoesNotExistEmptyResponse() throws Exception {
        Optional<MathGrade> mathGrade = mathGradesDao.findById(2);
        assertFalse(mathGrade.isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/grades/{id}/{course}", 2, "math"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

    @Test
    void deleteNonValidGradeHttpRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/grades/{id}/{course}", 1, "literature"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(modelAndView, "error");
    }

}
