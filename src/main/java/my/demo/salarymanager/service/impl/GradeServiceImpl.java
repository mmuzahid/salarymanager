package my.demo.salarymanager.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import my.demo.salarymanager.entity.Grade;
import my.demo.salarymanager.entity.LowerBasic;
import my.demo.salarymanager.exception.GradeException;
import my.demo.salarymanager.repository.GradeRepository;
import my.demo.salarymanager.repository.LowerBasiciRepository;
import my.demo.salarymanager.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private LowerBasiciRepository lowerBasiciRepository;
	
	@Override
	@Cacheable(value = "grades", key="#id")
	public Grade getGradeById(Long id) {
		Optional<Grade> grade = gradeRepository.findById(id);
        if(grade.isPresent()) {
            return grade.get();
        } else {
            throw new GradeException("No grade record exist for given id");
        }
	}

	@Override
	public Grade getGradeByName(String name) {
		return gradeRepository.findByName(name);
	}
	
	@Override
	public List<Grade> getGrades() {		
		return gradeRepository.findAll();
	}
	
	@Override
	public List<Grade> getGrades(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Grade> gradePage = gradeRepository.findAll(pageable);
		return gradePage.getContent();
	}
	
	@Override
	public Page<Grade> getGradesPage(Integer page, Integer pageSize, String sortBy) {		
		Pageable pageable = PageRequest.of(page.intValue(), pageSize, Sort.by(sortBy));
		Page<Grade> gradePage = gradeRepository.findAll(pageable);
		return gradePage;
	}

	@Override
	@CacheEvict(value = "grades", key = "#grade.id")
	public void saveGrade(Grade grade) {
		gradeRepository.save(grade);
	}

	@Override
	public LowerBasic getLowerBasic() {
		return lowerBasiciRepository.findAll().get(0);
	}
	
	@Override
	public void saveLowerBasic(LowerBasic lowerBasic) {
		lowerBasiciRepository.save(lowerBasic);
	}
	



}
