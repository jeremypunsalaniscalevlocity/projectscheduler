package com.iscalesolutions.vlocity.projectscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iscalesolutions.vlocity.projectscheduler.entities.TaskDependencies;

public interface TaskDependenciesRepository extends JpaRepository<TaskDependencies, Integer> {
	
	public List<TaskDependencies> findByMainTaskId(Integer taskId);

}
