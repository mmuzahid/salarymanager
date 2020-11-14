package my.demo.salarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import my.demo.salarymanager.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
