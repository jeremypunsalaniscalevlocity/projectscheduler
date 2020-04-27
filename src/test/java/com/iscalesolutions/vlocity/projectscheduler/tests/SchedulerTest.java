package com.iscalesolutions.vlocity.projectscheduler.tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;


import com.iscalesolutions.vlocity.projectscheduler.builders.TaskViewBuilder;
import com.iscalesolutions.vlocity.projectscheduler.service.TaskService;
import com.iscalesolutions.vlocity.projectscheduler.service.TaskServiceImpl;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

public class SchedulerTest {
	
	TaskService taskService = new TaskServiceImpl();
	
	@Test
	public void sanityTest() throws Exception {

		// tests that everything is not throwing exception

		ProjectView project1 = new ProjectView();
		project1.setProjectId(1);
		project1.setProjectName("project1");
		project1.setProjectDescription("project 1");

		TaskView task1 = new TaskViewBuilder("task1", 3).setId(1).setDescription("task 1").build();
		TaskView task2 = new TaskViewBuilder("task2", 4).setId(2).setDescription("task 2").build();
		TaskView task3 = new TaskViewBuilder("task3", 6).setId(3).setDescription("task 3").addDependencyTask(task2)
				.build();
		TaskView task4 = new TaskViewBuilder("task4", 5).setId(4).addDependencyTask(task2).setDescription("task 4")
				.addDependencyTask(task1).build();
		TaskView task6 = new TaskViewBuilder("task6", 2).setId(6).setDescription("task 6").addDependencyTask(task4)
				.build();
		TaskView task7 = new TaskViewBuilder("task7", 9).setId(7).setDescription("task 7").addDependencyTask(task1)
				.build();
		TaskView task8 = new TaskViewBuilder("task8", 12).setId(8).setDescription("task 8").build();
		TaskView task5 = new TaskViewBuilder("task5", 7).setId(5).setDescription("task 5").addDependencyTask(task6)
				.addDependencyTask(task7).addDependencyTask(task8).build();
		TaskView task9 = new TaskViewBuilder("task9", 1).setId(9).setDescription("task 9").build();
		TaskView task10 = new TaskViewBuilder("task10", 2).setId(10).addDependencyTask(task9).setDescription("task 10")
				.build();

		this.addUpdateTaskToProject(project1, task1);
		this.addUpdateTaskToProject(project1, task2);
		this.addUpdateTaskToProject(project1, task3);
		this.addUpdateTaskToProject(project1, task4);
		this.addUpdateTaskToProject(project1, task5);
		this.addUpdateTaskToProject(project1, task7);
		this.addUpdateTaskToProject(project1, task10);

		// set dates to all tasks inside the project
		project1.setStartDate(LocalDate.now());
		project1 = taskService.calculateStartEndDates(project1);
		taskService.provideTasksInChronologicalOrder(project1);

		project1.setStartDate(LocalDate.now().plusDays(2));
		project1 = taskService.calculateStartEndDates(project1);

		taskService.provideTasksInChronologicalOrder(project1);

	}

	@Test
	public void firstTest() throws Exception {

		// Test if the setting of date for two tasks (independent) is correct

		ProjectView project1 = new ProjectView();
		project1.setProjectId(1);
		project1.setProjectName("project1");
		project1.setProjectDescription("project 1");

		TaskView task1 = new TaskViewBuilder("task1", 3).setId(1).setDescription("task 1").build();
		TaskView task2 = new TaskViewBuilder("task2", 4).setId(2).setDescription("task 2").build();

		this.addUpdateTaskToProject(project1, task1);
		this.addUpdateTaskToProject(project1, task2);

		
		project1.setStartDate(LocalDate.now());
		project1 = taskService.calculateStartEndDates(project1);

		List<TaskView> list = taskService.provideTasksInChronologicalOrder(project1);

		// expected: first in list is task1
		assertEquals(Integer.valueOf(1), list.get(0).getTaskId());
		// expected: second in list is task2
		assertEquals(Integer.valueOf(2), list.get(1).getTaskId());

		// expected: task 1 startdate is now and enddate is today + 2 since duration is
		// 3
		assertEquals(LocalDate.now(), list.get(0).getStartDate());
		assertEquals(LocalDate.now().plusDays(2), list.get(0).getEndDate());

		// expected: task 2 startdate is today + 3 and enddate is today + 6 since
		// duration is 4
		assertEquals(LocalDate.now().plusDays(3), list.get(1).getStartDate());
		assertEquals(LocalDate.now().plusDays(6), list.get(1).getEndDate());

	}

	@Test
	public void secondTest() throws Exception {

		// Test dependency

		ProjectView project1 = new ProjectView();
		project1.setProjectId(1);
		project1.setProjectName("project1");
		project1.setProjectDescription("project 1");

		TaskView task1 = new TaskViewBuilder("task1", 1).setId(1).setDescription("task 1").build();
		TaskView task3 = new TaskViewBuilder("task3", 3).setId(3).setDescription("task 3").build();
		TaskView task2 = new TaskViewBuilder("task2", 2).setId(2).setDescription("task 2").addDependencyTask(task3)
				.build();

		this.addUpdateTaskToProject(project1, task1);
		this.addUpdateTaskToProject(project1, task2);
		this.addUpdateTaskToProject(project1, task3);

		project1.setStartDate(LocalDate.now());
		project1 = taskService.calculateStartEndDates(project1);

		List<TaskView> list = taskService.provideTasksInChronologicalOrder(project1);

		// expected: first in list is task1
		assertEquals(Integer.valueOf(1), list.get(0).getTaskId());
		// expected: second in list is task3 since it is dependent task of task2
		assertEquals(Integer.valueOf(3), list.get(1).getTaskId());
		// expected: last in list is task2
		assertEquals(Integer.valueOf(2), list.get(2).getTaskId());

		// check dates
		// expected: task 1 startdate is today and enddate is today
		assertEquals(LocalDate.now(), task1.getStartDate());
		assertEquals(LocalDate.now(), task1.getEndDate());

		// expected: task 2 startdate is today + 4 and enddate is today + 5 
		assertEquals(LocalDate.now().plusDays(4), task2.getStartDate());
		assertEquals(LocalDate.now().plusDays(5), task2.getEndDate());
		
		// expected: task 3 startdate is today + 1 and enddate is today + 3 
		assertEquals(LocalDate.now().plusDays(1), task3.getStartDate());
		assertEquals(LocalDate.now().plusDays(3), task3.getEndDate());

	}

	private ProjectView addUpdateTaskToProject(ProjectView project, TaskView task) {

		List<TaskView> list = Optional.ofNullable(project.getTaskViews()).orElse(new ArrayList<TaskView>());
		if (!containsTaskIdInList(list, task))
			list.add(task);
		else {
			list.parallelStream().forEach(t -> {
				if (t.getTaskId().equals(task.getTaskId()))
					t = task;
			});
		}
		project.setTaskViews(list);
		return project;
	}

	private boolean containsTaskIdInList(List<TaskView> list, TaskView task) {
		return list.parallelStream().anyMatch(t -> t.getTaskId().equals(task.getTaskId()));
	}

}
