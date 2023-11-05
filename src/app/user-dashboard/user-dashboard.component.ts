import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent {

  isSidebarExpanded = false;

  constructor(private router: Router, private userService: UserService) {}

  toggleSidebar(): void {
    this.isSidebarExpanded = !this.isSidebarExpanded;
  }

  logout(): void {
    this.userService.logout().subscribe(
      () => {
        localStorage.removeItem('authToken');
        localStorage.removeItem('userEmail');
        
        this.router.navigate(['']);
      },
      error => {
        console.error('Error logging out:', error);
      }
    );
  }
}
