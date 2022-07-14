package com.luv2code.springmvc.services;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repositories.HistoryGradesDao;
import com.luv2code.springmvc.repositories.MathGradesDao;
import com.luv2code.springmvc.repositories.ScienceGradesDao;
import com.luv2code.springmvc.repositories.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    MathGradesDao mathGradeDao;
    @Autowired
    ScienceGradesDao scienceGradeDao;
    @Autowired
    HistoryGradesDao historyGradeDao;
    @Autowired
    StudentGrades studentGrades;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;
    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;
    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    public void createStudent(String firstName, String lastName, String email) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, email);
        student.setId(0);
        studentDao.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if(student.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if(checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
            mathGradeDao.deleteByStudentId(id);
            scienceGradeDao.deleteByStudentId(id);
            historyGradeDao.deleteByStudentId(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int id, String course) {
        if(!checkIfStudentIsNull(id)) {
            return false;
        }
        if(grade >= 0 && grade <= 100) {
            switch (course) {
                case "math":
                    mathGrade.setId(0);
                    mathGrade.setGrade(grade);
                    mathGrade.setStudentId(id);
                    mathGradeDao.save(mathGrade);
                    return true;
                case "science":
                    scienceGrade.setId(0);
                    scienceGrade.setGrade(grade);
                    scienceGrade.setStudentId(id);
                    scienceGradeDao.save(scienceGrade);
                    return true;
                case "history":
                    historyGrade.setId(0);
                    historyGrade.setGrade(grade);
                    historyGrade.setStudentId(id);
                    historyGradeDao.save(historyGrade);
                    return true;
                default:
                    System.out.println("Unknown course type");
            }
        }
        return false;
    }

    public int deleteGrade(int studentId, String course) {
        int response = 0;
        switch (course) {
            case "math":
                Optional<MathGrade> mthGrade = mathGradeDao.findById(studentId);
                if(!mthGrade.isPresent()) {
                    return response;
                }
                response = studentId;
                mathGradeDao.deleteById(studentId);
                break;
            case "science":
                Optional<ScienceGrade> scnGrade = scienceGradeDao.findById(studentId);
                if(!scnGrade.isPresent()) {
                    return response;
                }
                response = studentId;
                scienceGradeDao.deleteById(studentId);
                break;
            case "history":
                Optional<HistoryGrade> hisGrade = historyGradeDao.findById(studentId);
                if(!hisGrade.isPresent()) {
                    return response;
                }
                response = studentId;
                historyGradeDao.deleteById(studentId);
                break;
            default:
                System.out.println("Unknown course type");
        }
        return response;
    }

    public GradebookCollegeStudent getStudentInformation(int id) {
        if(!checkIfStudentIsNull(id)) {
            return null;
        }
        Optional<CollegeStudent> collegeStudent = studentDao.findById(id);
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(id);
        List<Grade> mathGradeList = new ArrayList<>();
        mathGrades.forEach(mathGradeList::add);
        List<Grade> scienceGradeList = new ArrayList<>();
        scienceGrades.forEach(scienceGradeList::add);
        List<Grade> historyGradeList = new ArrayList<>();
        historyGrades.forEach(historyGradeList::add);
        studentGrades.setMathGradeResults(mathGradeList);
        studentGrades.setScienceGradeResults(scienceGradeList);
        studentGrades.setHistoryGradeResults(historyGradeList);
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(
                collegeStudent.get().getId(),
                collegeStudent.get().getFirstname(),
                collegeStudent.get().getLastname(),
                collegeStudent.get().getEmailAddress(),
                studentGrades
        );
        return gradebookCollegeStudent;
    }

    public void configureStudentInformationModel(int id, Model m) {
        GradebookCollegeStudent studentEntity = getStudentInformation(id);
        m.addAttribute("student", studentEntity);
        if(studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
            m.addAttribute("mathAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getMathGradeResults()
            ));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }
        if(studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
            m.addAttribute("scienceAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getScienceGradeResults()
            ));
        } else {
            m.addAttribute("scienceAverage", "N/A");
        }
        if(studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
            m.addAttribute("historyAverage", studentEntity.getStudentGrades().findGradePointAverage(
                    studentEntity.getStudentGrades().getHistoryGradeResults()
            ));
        } else {
            m.addAttribute("historyAverage", "N/A");
        }
    }

}
