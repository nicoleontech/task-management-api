package com.sarrou.taskmanagementapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    User findByEmail(String email);
}