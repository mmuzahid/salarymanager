package my.demo.salarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
