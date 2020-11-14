package my.demo.salarymanager.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import my.demo.salarymanager.entity.Grade;
import my.demo.salarymanager.entity.LowerBasic;

public interface GradeService {
	public Grade getGradeById(Long id);
	public List<Grade> getGrades();
	public List<Grade> getGrades(Integer page, Integer pageSize, String sortBy);
	public void saveGrade(Grade grade);
	public Page<Grade> getGradesPage(Integer page, Integer pageSize, String sortBy);
	public Grade getGradeByName(String name);
	public void saveLowerBasic(LowerBasic lowerBasic);
	public LowerBasic getLowerBasic();
}
