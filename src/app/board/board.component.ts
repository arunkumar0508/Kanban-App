import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { UserService } from 'src/services/user.service';
import { board } from '../models/board';
import { column } from '../models/column';
import { task } from '../models/task';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  projectId: string | undefined;
  board: board | undefined;
  newTaskTitle: string = '';
  newTaskDescription: string = '';
  newTaskAssigningName: string = '';
  isFormOpen: boolean = false;
  openTaskDetailsModal: boolean = false;
  selectedTask: any;
  selectedColumn: any;
  newTaskStatus: string = '';
  targetColumn: string = '';
  tasks: task[] = [];
  columns: column[] = [];
  userNames: string[] = [];
  tasksAssignedToUser: number = 0;
  allAssigningNames: string[] = [];

  // Maintain a map to count tasks assigned to each user
  userTaskCounts: Record<string, number> = {};

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private userService: UserService
  ) {
    this.board = {
      boardId: null,
      name: '',
      columns: []
    };
  }

  selectColumn(columnIndex: number): void {
    this.selectedColumn = columnIndex;
  }

  ngOnInit(): void {
    this.userService.getAllUserEmails().subscribe((emails) => {
      this.userNames = emails;
    });
    this.route.params.subscribe((params) => {
      this.projectId = String(params['id']) ?? undefined;

      if (!this.projectId) {
        console.error('projectId is undefined or null.');
      } else {
        this.loadBoardDetails();
      }
    });
  }

  loadBoardDetails() {
    if (this.projectId) {
      const email = localStorage.getItem('userEmail');

      if (email) {
        this.projectService.getProjectDetails(email, this.projectId).subscribe(
          (response) => {
            this.board = response.board;

            if (this.board && this.board.columns) {
              this.columns = this.board.columns;

              // Populate this.tasks by flattening the columns
              this.tasks = this.flattenTasks(this.columns);

              // Now you can call getAllAssigningNames
              this.getAllAssigningNames();
            }
          },
          (error) => {
            console.error('Error loading board details:', error);
          }
        );
      } else {
        console.error('User email is missing.');
      }
    }
  }

  flattenTasks(columns: column[]): task[] {
    return columns.reduce((tasks: task[], column: column) => {
      if (column.tasks) {
        tasks.push(...column.tasks);
      }
      return tasks;
    }, []);
  }

  openAddTaskForm() {
    this.isFormOpen = true;
  }

  closeAddTaskForm() {
    this.isFormOpen = false;
    this.resetFormFields();
  }

  getAllAssigningNames(): string[] {
    console.log(this.tasks);
    const names: string[] = [];
    if (this.tasks) {
      this.tasks.forEach((task: task) => {
        names.push(task.assigningName);
      });
    }
    console.log('names', names); 
    return names;
  }
  
  
  addTask() {
    if (
      this.newTaskTitle !== null &&
      this.newTaskAssigningName !== null &&
      this.newTaskTitle.trim() !== '' &&
      this.newTaskAssigningName.trim() !== '' &&
      this.newTaskAssigningName.trim().length > 0
    ) {
      const maxTasksPerUser = 3;
  
      // Get the names from tasks
      const names = this.getAllAssigningNames();

      console.log(names);
  
      // Count occurrences of the new assigning name (case-insensitive and trim whitespace)
      const validNames = names.filter(name => {
        // Check if name and this.newTaskAssigningName are not null
        if (name !== null && this.newTaskAssigningName !== null) {
          // Convert both name and this.newTaskAssigningName to lowercase and trim
          const trimmedName = name.trim().toLowerCase();
          const trimmedNewTaskAssigningName = this.newTaskAssigningName.trim().toLowerCase();
      
          // Check if the trimmed names are equal
          return trimmedName === trimmedNewTaskAssigningName;
        }
        return false; // If either is null, consider it invalid
      });
      
      const occurrences = validNames.length;
      
  
      if (occurrences >= maxTasksPerUser) {
        alert('This user already has the maximum number of tasks (3).');
        return;
      }
  
      const newTask = {
        title: this.newTaskTitle,
        description: this.newTaskDescription,
        assigningName: this.newTaskAssigningName,
        status: ''
      };
  
      if (this.projectId) {
        this.projectService.createTaskInFirstColumn(this.projectId, newTask).subscribe(
          (response) => {
            if (this.board && this.board.columns[0]) {
              this.board.columns[0].tasks.push(response.task);
              this.closeAddTaskForm();
              this.resetFormFields();
  
              this.userTaskCounts[this.newTaskAssigningName] =
                (this.userTaskCounts[this.newTaskAssigningName] || 0) + 1;
  
              console.log(`Task added for ${this.newTaskAssigningName}. Total tasks now: ${this.userTaskCounts[this.newTaskAssigningName]}`);
            }
          },
          (error) => {
            console.error('Error creating a task:', error);
          }
        );
      }
    } else {
      alert('Please provide a task title and a valid assigning name.');
    }
  }
  

  resetFormFields() {
    this.newTaskTitle = '';
    this.newTaskDescription = '';
    this.newTaskAssigningName = '';
  }

  showTaskDetails(task: any) {
    this.selectedTask = task;
    this.openTaskDetailsModal = true;
  }

  hideTaskDetails() {
    this.selectedTask = null;
    this.openTaskDetailsModal = false;
  }

  deleteTask(columnIndex: any, taskToDelete: task): void {
    if (columnIndex >= 0 && columnIndex < this.columns.length) {
      const column = this.columns[columnIndex];

      if (this.projectId) {
        this.projectService.deleteTask(this.projectId, taskToDelete).subscribe(
          () => {
            console.log('Task deleted successfully from the database.');

            const taskIndex = column.tasks.findIndex((taskInColumn: task) =>
              taskInColumn.title === taskToDelete.title && taskInColumn.description === taskToDelete.description
            );

            if (taskIndex !== -1) {
              column.tasks.splice(taskIndex, 1);

              this.userTaskCounts[taskToDelete.assigningName] =
                (this.userTaskCounts[taskToDelete.assigningName] || 1) - 1;
              console.log(`Task deleted for ${taskToDelete.assigningName}. Total tasks now: ${this.userTaskCounts[taskToDelete.assigningName]}`);
            }

            this.hideTaskDetails();
          },
          (error) => {
            console.error('Error deleting task from the server:', error);
          }
        );
      }
    }
  }

  updateTaskStatusAndMove(task: any, newStatus: string, targetColumn: string) {
    if (task.status === newStatus) {
      return;
    }

    const targetColumnObj = this.board?.columns.find((column) => column.name === newStatus);

    if (targetColumnObj) {
      const currentColumn = this.board?.columns.find((column) => column.tasks.includes(task));

      if (currentColumn && currentColumn.tasks) {
        currentColumn.tasks = currentColumn.tasks.filter((t) => t !== task);
      }

      targetColumnObj.tasks.push(task);

      task.status = newStatus;

      if (this.projectId) {
        this.projectService.updateTask(this.projectId, task, targetColumn).subscribe(
          (response) => {
            console.log('Task successfully updated in the database:', response);
          },
          (error) => {
            console.error('Error updating task in the database:', error);
          }
        );
      }
    }
  }
}
