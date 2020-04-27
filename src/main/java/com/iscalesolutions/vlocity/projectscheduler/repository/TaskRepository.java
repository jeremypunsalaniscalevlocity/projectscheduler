package com.iscalesolutions.vlocity.projectscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iscalesolutions.vlocity.projectscheduler.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
