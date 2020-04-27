package com.iscalesolutions.vlocity.projectscheduler.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iscalesolutions.vlocity.projectscheduler.builders.TaskBuilder;
import com.iscalesolutions.vlocity.projectscheduler.builders.TaskViewBuilder;
import com.iscalesolutions.vlocity.projectscheduler.entities.Task;
import com.iscalesolutions.vlocity.projectscheduler.entities.TaskDependencies;
import com.iscalesolutions.vlocity.projectscheduler.repository.ProjectTasksRepository;
import com.iscalesolutions.vlocity.projectscheduler.repository.TaskDependenciesRepository;
import com.iscalesolutions.vlocity.projectscheduler.repository.TaskRepository;
import com.iscalesolutions.vlocity.projectscheduler.view.ProjectView;
import com.iscalesolutions.vlocity.projectscheduler.view.TaskView;

@Component("TaskService")
public class TaskServiceImpl implements TaskService {

	private TaskRepository taskRepository;

	private TaskDependenciesRepository taskDependenciesRepository;

	@Autowired
	public void setTaskRepository(TaskRepository repository) {
		this.taskRepository = repository;
	}

	@Autowired
	public void setTaskDependenciesRepository(TaskDependenciesRepository repository) {
		this.taskDependenciesRepository = repository;
	}

	@Override
	public TaskView saveTask(final TaskView task) throws Exception {

		Task saveTask = new TaskBuilder(task.getTaskName(), task.getDuration()).setId(task.getTaskId())
				.setDescription(task.getDescription()).build();
		saveTask = taskRepository.save(saveTask);

		AtomicReference<Integer> taskId = new AtomicReference<Integer>();
		taskId.set(saveTask.getTaskId());

		if (Optional.ofNullable(task.getDependencies()).isPresent()) {
			List<TaskView> d = task.getDependencies();

			List<TaskDependencies> dependenciesToRemove = taskDependenciesRepository.findByMainTaskId(taskId.get());
			if (Optional.ofNullable(dependenciesToRemove).isPresent()) {
				taskDependenciesRepository.deleteAll(dependenciesToRemove);
			}

			List<TaskDependencies> dependencies = new ArrayList<TaskDependencies>();
			d.parallelStream().forEach(t -> {
				TaskDependencies dp = new TaskDependencies();
				dp.setMainTaskId(taskId.get());
				dp.setDependencyId(t.getTaskId());
				dependencies.add(dp);
			});

			taskDependenciesRepository.saveAll(dependencies);
		} else {

			List<TaskDependencies> dependenciesToRemove = taskDependenciesRepository.findByMainTaskId(taskId.get());
			if (Optional.ofNullable(dependenciesToRemove).isPresent()) {
				taskDependenciesRepository.deleteAll(dependenciesToRemove);
			}

		}

		return task;
	}

	@Override
	public TaskView getTaskById(Integer taskId) throws Exception {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new Exception("No Task with id: " + taskId));
		TaskViewBuilder builder = new TaskViewBuilder(task.getTaskName(), task.getDuration()).setId(task.getTaskId())
				.setDescription(task.getDescription());
		return getTask(builder);
	}

	@Override
	public TaskView getTask(TaskViewBuilder builder) {

		TaskView mainTask = builder.build();

		// dependencies
		List<TaskDependencies> dependencies = taskDependenciesRepository.findByMainTaskId(mainTask.getTaskId());

		for (TaskDependencies t : dependencies) {
			Task ts = taskRepository.findById(t.getDependencyId()).get();
			TaskViewBuilder tvBuilder = new TaskViewBuilder(ts.getTaskName(), ts.getDuration()).setId(ts.getTaskId())
					.setDescription(ts.getDescription());
			builder.addDependencyTask(getTask(tvBuilder));
		}
		return builder.build();
	}

	@Override
	public List<TaskView> getAllTaskIdTaskName() throws Exception {
		List<Task> all = taskRepository.findAll();
		List<TaskView> list = new ArrayList<TaskView>();
		for (Task task : all) {
			TaskView view = new TaskViewBuilder(task.getTaskName(), task.getDuration()).setId(task.getTaskId())
					.setDescription(task.getDescription()).build();
			list.add(view);
		}
		return list;
	}

	@Override
	public List<Integer> getAllDependentTaskId(Integer taskId) throws Exception {
		List<Integer> dependentTasksId = new ArrayList<Integer>();
		for (TaskDependencies t : taskDependenciesRepository.findByMainTaskId(taskId)) {
			dependentTasksId.add(t.getDependencyId());
		}
		return dependentTasksId;
	}
	
	@Override
	public ProjectView calculateStartEndDates(ProjectView project) throws Exception {
		if (!Optional.of(project.getStartDate()).isPresent())
			throw new Exception("start date for the project must be set");
		Map<Integer, TaskView> taskMap = new HashMap<Integer, TaskView>();
		List<TaskView> list = Optional.ofNullable(project.getTaskViews())
				.orElseThrow(() -> new Exception("no tasks set to project " + project.getProjectName()));
		AtomicReference<LocalDate> startDate = new AtomicReference<>();
		startDate.set(project.getStartDate());
		ListIterator<TaskView> iterator = list.listIterator();
		while (iterator.hasNext()) {
			TaskView task = iterator.next();
			if (!taskMap.containsKey(task.getTaskId())) {
				task = traverseTasks(task, startDate.get(), taskMap);
				startDate.set(task.getEndDate().plusDays(1));
			} else {
				task = taskMap.get(task.getTaskId());
			}
			iterator.set(task);
		}
		project.setTaskViews(list);
		return project;
	}

	private TaskView traverseTasks(TaskView task, LocalDate startDate, Map<Integer, TaskView> taskMap) {

		if (!Optional.ofNullable(task.getDependencies()).isPresent()) {
			if (!taskMap.containsKey(task.getTaskId())) {
				task.setStartDate(startDate);
				LocalDate endDate = startDate.plusDays(task.getDuration() - 1);
				task.setEndDate(endDate);
				taskMap.put(task.getTaskId(), task);
				return task;
			} else {
				return taskMap.get(task.getTaskId());
			}
		} else {
			List<TaskView> dependencies = task.getDependencies();
			dependencies.sort(Comparator.comparingInt(TaskView::getTaskId));
			AtomicReference<LocalDate> maxDate = new AtomicReference<>();
			AtomicReference<LocalDate> endDate = new AtomicReference<>();
			maxDate.set(startDate);

			ListIterator<TaskView> iterator = dependencies.listIterator();
			while (iterator.hasNext()) {
				TaskView t = iterator.next();
				if (!taskMap.containsKey(t.getTaskId())) {
					t = traverseTasks(t, maxDate.get(), taskMap);
				} else {
					t = taskMap.get(t.getTaskId());
				}
				endDate.set(t.getEndDate());
				if (endDate.get().isAfter(maxDate.get()) || endDate.get().isEqual(maxDate.get()) ) {
					maxDate.set(endDate.get().plusDays(1));
				}
				iterator.set(t);
			}

			if (!taskMap.containsKey(task.getTaskId())) {
				task.setStartDate(maxDate.get());
				endDate.set(maxDate.get().plusDays(task.getDuration() - 1));
				task.setEndDate(endDate.get());
			}

			task.setDependencies(dependencies);
			taskMap.put(task.getTaskId(), task);
			return task;
		}

	}

	@Override
	public List<TaskView> provideTasksInChronologicalOrder(ProjectView project) throws Exception {
		if (!Optional.of(project.getStartDate()).isPresent())
			throw new Exception("start date for the project must be set");
		Set<TaskView> list = new HashSet<TaskView>();
		List<TaskView> taskList = project.getTaskViews();
		ListIterator<TaskView> iterator = taskList.listIterator();
		while (iterator.hasNext()) {
			TaskView task = iterator.next();
			task.setTaskType("Main Task");
			getDependencyChildren(list, task);
			iterator.set(task);
		}
		List<TaskView> sortList = new ArrayList<TaskView>(list);
		sortList.sort((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate()));
		return sortList;
	}

	private TaskView getDependencyChildren(Set<TaskView> list, TaskView task) {

		list.add(task);
		if (!Optional.ofNullable(task.getDependencies()).isPresent()) {
			return task;
		} else {
			List<TaskView> dependencies = task.getDependencies();
			dependencies.sort((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate()));
			ListIterator<TaskView> iterator = dependencies.listIterator();
			while (iterator.hasNext()) {
				TaskView t = iterator.next();
				if (!Optional.ofNullable(t.getTaskType()).isPresent())
					t.setTaskType("Sub-Task");
				t = getDependencyChildren(list, t);
				list.add(t);
				iterator.set(t);
			}
			return task;
		}
	}

}
