package com.uxpsystems.assignment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uxpsystems.assignment.model.User;

/**
 * Data repository class to manage all the Database related transaction. Spring
 * Data JPA repository which manages all the implementation to reduce the
 * boilerplate code from developer perspective.
 * 
 * @author Umesh .Chavan
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(long id);

	Optional<User> findByUserName(String userName);

}
