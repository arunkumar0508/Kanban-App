import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { project } from '../models/project';
import { ProjectService } from 'src/services/project.service'; 

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  projects: project[] = [];
  filteredProjects: project[] = [];
  searchTerm: string = ''; 

  newProjectName: string = '';
  newProjectDescription: string = '';

  constructor(
    private router: Router,
    private userService: UserService,
    private projectService: ProjectService 
  ) {}

  ngOnInit(): void {
    if (this.userService.isAuthenticated()) {
      const email = localStorage.getItem('userEmail');
      
      if (email) {
        this.projectService.getProjects().subscribe((userProjects) => {
          console.log(userProjects);
          if (userProjects != null) {
            this.projects = userProjects.map((project)=>({
               id: project.projectId,
               name: project.name,
               description: project.description,
            })
            );
            this.filteredProjects = this.projects;
            console.log(this.projects);
          } else {
            this.projects = [];
          }
        });
      } else {
        console.error('User email is missing.');
      }
    } else {
      this.router.navigate(['']);
    }
  }

  onSearch(searchTerm: string) {
    if (searchTerm) {

      this.filteredProjects=this.projects.filter((project)=>{
        console.log(project);
        const projectName =project.name?.toLowerCase();
        const search = searchTerm.toLowerCase();
        return projectName?.includes(search) ;
      });
      console.log(this.filteredProjects);


    } else{
      this.filteredProjects=this.projects;
    }
}
  
}
