package my.demo.salarymanager.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Account;
import my.demo.salarymanager.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {
	public Grade findByName(String name);
}
