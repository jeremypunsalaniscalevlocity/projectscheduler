package com.iscalesolutions.vlocity.projectscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iscalesolutions.vlocity.projectscheduler.entities.ProjectTasks;

public interface ProjectTasksRepository extends JpaRepository<ProjectTasks, Integer> {
	
	public List<ProjectTasks> findByProjectId(Integer projectId);

}
