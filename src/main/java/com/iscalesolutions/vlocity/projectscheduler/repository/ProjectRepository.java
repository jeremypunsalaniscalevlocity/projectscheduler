package com.iscalesolutions.vlocity.projectscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iscalesolutions.vlocity.projectscheduler.entities.Project;


public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
