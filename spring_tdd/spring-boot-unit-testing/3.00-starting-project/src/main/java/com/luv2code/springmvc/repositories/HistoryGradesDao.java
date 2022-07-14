package com.luv2code.springmvc.repositories;

import com.luv2code.springmvc.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;

public interface HistoryGradesDao extends CrudRepository<HistoryGrade,Integer> {
    Iterable<HistoryGrade> findGradeByStudentId(int id);
    void deleteByStudentId(int id);
}
