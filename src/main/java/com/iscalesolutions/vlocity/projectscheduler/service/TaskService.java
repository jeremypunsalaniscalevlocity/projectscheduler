package com.iscalesolutions.vlocity.projectscheduler.service;


import java.util.List;

import com.iscalesolutions.vlocity.projectscheduler.builders.TaskViewBuilder;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

public interface TaskService {

	ProjectView addUpdateTaskToProject(ProjectView project, TaskView task);

	ProjectView calculateStartEndDates(ProjectView project) throws Exception;
	
	List<TaskView> provideTasksInChronologicalOrder(ProjectView project) throws Exception;
	
	TaskView saveTask(TaskView task) throws Exception;

	TaskView getTaskById(Integer taskId) throws Exception;
	
	TaskView getTask(TaskViewBuilder taskViewBuilder) throws Exception;

	List<TaskView> getAllTaskIdTaskName() throws Exception;

	List<Integer> getAllDependentTaskId(Integer taskId) throws Exception;
	
}
