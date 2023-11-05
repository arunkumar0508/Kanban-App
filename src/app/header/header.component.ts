import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  isLoginPage: boolean = false;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    // this.isLoginPage = true;
    // const authToken = localStorage.getItem('authToken');
    // const userEmail = localStorage.getItem('userEmail');
    // this.isLoggedIn = !!authToken && !!userEmail;
    this.updateAuthenticationStatus();
    
  }
  private updateAuthenticationStatus(): void {
    this.isLoggedIn = this.userService.isAuthenticated();
  }

  logout(): void {
    this.userService.logout().subscribe(
      () => {
        this.isLoggedIn = false;

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
