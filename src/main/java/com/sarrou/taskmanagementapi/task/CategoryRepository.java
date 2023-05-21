package com.sarrou.taskmanagementapi.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

@Query("SELECT C.id, C.name from CategoryEntity C where C.name = ?1")
CategoryEntity findCategoryEntityByName(String name);

}
