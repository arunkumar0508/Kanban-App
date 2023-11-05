import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectService } from 'src/services/project.service'; 

@Component({
  selector: 'app-addproject',
  templateUrl: './addproject.component.html',
  styleUrls: ['./addproject.component.css']
})
export class AddprojectComponent {
  newProject: any = {
    name: '',
    description: '',
    board: {
      name: '', 
      columns: [] 
    }
  };

  constructor(private router: Router, private projectService: ProjectService) {}

  saveProject() {
    const email = localStorage.getItem('userEmail');

    if (!email) {
      console.error('User email is missing.');
      return;
    }
    console.log(email);
    this.projectService.addProject(this.newProject).subscribe((response) => {
      console.log('Response:', response);
      this.router.navigate(['/project']); 
    });
    
    
    
  }

  addColumn() {
    this.newProject.board.columns.push({ name: '', tasks: [] });
  }

  addTask(columnIndex: number) {
    this.newProject.board.columns[columnIndex].tasks.push({ title: '', description: '', assigningname: '' });
  }
}
