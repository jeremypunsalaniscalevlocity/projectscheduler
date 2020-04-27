package com.iscalesolutions.vlocity.projectscheduler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iscalesolutions.vlocity.projectscheduler.builders.ProjectBuilder;
import com.iscalesolutions.vlocity.projectscheduler.builders.ProjectViewBuilder;
import com.iscalesolutions.vlocity.projectscheduler.entities.Project;
import com.iscalesolutions.vlocity.projectscheduler.entities.ProjectTasks;
import com.iscalesolutions.vlocity.projectscheduler.repository.ProjectRepository;
import com.iscalesolutions.vlocity.projectscheduler.repository.ProjectTasksRepository;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

@Component("ProjectService")
public class ProjectServiceImpl implements ProjectService {

	private ProjectRepository projectRepository;

	private ProjectTasksRepository projectTasksRepository;

	@Autowired
	public void setProjectRepository(ProjectRepository repository) {
		this.projectRepository = repository;
	}

	@Autowired
	public void setProjectTasksRepository(ProjectTasksRepository repository) {
		this.projectTasksRepository = repository;
	}

	@Override
	public ProjectView save(final ProjectView projectView) throws Exception {

		Project saveProject = new ProjectBuilder(projectView.getProjectName(), projectView.getStartDate())
				.setId(projectView.getProjectId()).setProjectDescription(projectView.getProjectDescription()).build();
		projectRepository.save(saveProject);

		AtomicReference<Integer> projectId = new AtomicReference<Integer>();
		projectId.set(saveProject.getProjectId());

		if (Optional.ofNullable(projectView.getTaskViews()).isPresent()) {
			List<TaskView> tasks = projectView.getTaskViews();

			List<ProjectTasks> tasksToRemove = projectTasksRepository.findByProjectId(projectId.get());
			if (Optional.ofNullable(tasksToRemove).isPresent()) {
				projectTasksRepository.deleteAll(tasksToRemove);
			}

			List<ProjectTasks> tasksToAdd = new ArrayList<ProjectTasks>();
			tasks.parallelStream().forEach(t -> {
				ProjectTasks pt = new ProjectTasks();
				pt.setProjectId(projectId.get());
				pt.setTaskId(t.getTaskId());
				tasksToAdd.add(pt);
			});

			projectTasksRepository.saveAll(tasksToAdd);

		} else {

			List<ProjectTasks> tasksToRemove = projectTasksRepository.findByProjectId(projectId.get());
			if (Optional.ofNullable(tasksToRemove).isPresent()) {
				projectTasksRepository.deleteAll(tasksToRemove);
			}

		}

		return projectView;
	}

	@Override
	public ProjectView getProjectById(final Integer projectId) throws Exception {
		
		Project project = projectRepository.findById(projectId).orElseThrow(() -> new Exception("No project exists with it: " + projectId));
		
		return new ProjectViewBuilder(project.getProjectName(), project.getStartDate())
				.setId(project.getProjectId())
				.setProjectDescription(project.getProjectDescription())
				.build();
		
	}

	@Override
	public List<ProjectTasks> getProjectTasksByProjectId(Integer projectId) throws Exception {
		return projectTasksRepository.findByProjectId(projectId);
	}

	@Override
	public List<ProjectView> getAllProjects() throws Exception {
		List<Project> projects = projectRepository.findAll();
		if(!Optional.ofNullable(projects).isPresent() || projects.size() <= 0)
			throw new Exception("no data in DB");
		List<ProjectView> projectViews = new ArrayList<ProjectView>();
		for(Project pr: projects) {
			projectViews.add(new ProjectViewBuilder(pr.getProjectName(), pr.getStartDate())
					.setId(pr.getProjectId())
					.setProjectDescription(pr.getProjectDescription())
					.build());
		}
		return projectViews;
		
	}

	@Override
	public List<ProjectView> getAllProjectsWithTasks() throws Exception {
		List<Project> projects = projectRepository.findAll();
		if(!Optional.ofNullable(projects).isPresent() || projects.size() <= 0)
			throw new Exception("no data in DB");
		List<ProjectView> projectViews = new ArrayList<ProjectView>();
		for(Project pr: projects) {
			if(projectTasksRepository.findByProjectId(pr.getProjectId()).size() > 0) {
				projectViews.add(new ProjectViewBuilder(pr.getProjectName(), pr.getStartDate())
						.setId(pr.getProjectId())
						.setProjectDescription(pr.getProjectDescription())
						.build());
			}
			
		}
		return projectViews;
	}



}
