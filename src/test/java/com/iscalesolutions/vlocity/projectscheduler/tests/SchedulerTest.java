package com.iscalesolutions.vlocity.projectscheduler.tests;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.iscalesolutions.vlocity.projectscheduler.builders.TaskViewBuilder;
import com.iscalesolutions.vlocity.projectscheduler.service.TaskService;
import com.iscalesolutions.vlocity.projectscheduler.service.TaskServiceImpl;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

public class SchedulerTest {

	@Test
	public void sanityTest() {
		
		ProjectView project1 = new ProjectView();
		project1.setProjectId(1);
		project1.setProjectName("project1");
		project1.setProjectDescription("project 1");
		
		
		
		TaskView task1 = new TaskViewBuilder("task1", 3).setId(1).setDescription("task 1").build();
		TaskView task2 = new TaskViewBuilder("task2", 4).setId(2).setDescription("task 2").build();
		TaskView task3 = new TaskViewBuilder("task3", 6).setId(3).setDescription("task 3").addDependencyTask(task2).build();
		TaskView task4 = new TaskViewBuilder("task4", 5).setId(4).addDependencyTask(task2).setDescription("task 4").addDependencyTask(task1).build();
		TaskView task6 = new TaskViewBuilder("task6", 2).setId(6).setDescription("task 6").addDependencyTask(task4).build();
		TaskView task7 = new TaskViewBuilder("task7", 9).setId(7).setDescription("task 7").addDependencyTask(task1).build();
		TaskView task8 = new TaskViewBuilder("task8", 12).setId(8).setDescription("task 8").build();
		TaskView task5 = new TaskViewBuilder("task5", 7).setId(5).setDescription("task 5").addDependencyTask(task6).addDependencyTask(task7).addDependencyTask(task8).build();
		TaskView task9 = new TaskViewBuilder("task9", 1).setId(9).setDescription("task 9").build();
		TaskView task10 = new TaskViewBuilder("task10", 2).setId(10).addDependencyTask(task9).setDescription("task 10").build();
		
		TaskService taskService = new TaskServiceImpl();

		taskService.addUpdateTaskToProject(project1, task1);
		taskService.addUpdateTaskToProject(project1, task2);
		taskService.addUpdateTaskToProject(project1, task3);
		taskService.addUpdateTaskToProject(project1, task4);
		taskService.addUpdateTaskToProject(project1, task5);
		taskService.addUpdateTaskToProject(project1, task7);
		taskService.addUpdateTaskToProject(project1, task10);
		
		
		
		//set dates to all tasks inside the project
		project1.setStartDate(LocalDate.now());
		
		try {
			project1 = taskService.calculateStartEndDates(project1);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		task5.setDescription("updated description");
		
		try {
			System.out.println(taskService.provideTasksInChronologicalOrder(project1));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//what if we change the start date
		project1.setStartDate(LocalDate.now().plusDays(2));
		try {
			project1 = taskService.calculateStartEndDates(project1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		task6.setDescription("updated description");
		
		try {
			System.out.println(taskService.provideTasksInChronologicalOrder(project1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
