import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/services/user.service';
import { Router } from '@angular/router';
import { User } from '../models/user';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {
  showSignup: boolean = false;
  loginForm: FormGroup;
  signupForm: FormGroup;
  showHelloMessage: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required,this.validatePassword]]
    });

    this.signupForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      phoneNumber: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  onLoginSubmit(): void {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.email;
      const password = this.loginForm.value.password;
      
      this.userService.login({ email, password }).subscribe(
        (response: any) => {
          const authToken = response.token;
          
          localStorage.setItem('authToken', authToken);

          localStorage.setItem('userEmail', email);

          this.router.navigate(['/dashboard']);
        },
        error => {
          console.error('Error logging in:', error);
        }
      );
    }
  }
  validatePassword(control: any) {
    const password = control.value;
    if (password && password.length < 8) {
      return { invalidPassword: true };
    }
    
    return null;
  }

  showSignupCard(): void {
    this.showSignup = true;
    this.showHelloMessage = false;
  }

  showHelloMessageCard(): void {
    this.showHelloMessage = true;
    this.showSignup = false;
  }
  reloadPage(){
    location.reload();
  }

  onSignupSubmit(): void {
    if (this.signupForm.valid) {
      const user: User = this.signupForm.value;
      this.userService.signUp(user).subscribe({
        next: response => {
          console.log("User created", response);
          // this.router.navigate(['/'])
          location.reload();
          this.signupForm.reset();
          // this.router.navigate([''])
        },
        error: error => {
          console.error('Error submitting User:', error);
        }
      });
    }
  }
  // logout(): void {
  //   localStorage.removeItem('authToken');
  //   localStorage.removeItem('userEmail'); 

  //   this.router.navigate(['']);
  // }
}
