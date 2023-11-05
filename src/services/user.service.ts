import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user';
import { of } from 'rxjs';
import { project } from 'src/app/models/project';

@Injectable({
    providedIn: 'root'
  })
  export class UserService {
    private baseUrl = 'http://localhost:9999/apii/users';
    private baseUrl1 = 'http://localhost:9999/api/users';
  
    constructor(private http: HttpClient) { }
  
    signUp(user: any): Observable<any> {
      return this.http.post(`${this.baseUrl}/register`, user);
    }
  
    login(user: any): Observable<User> {
      return this.http.post<User>(`${this.baseUrl1}/login`, user);
    }

    getAllUserEmails(): Observable<string[]> {
      return this.http.get<string[]>(`${this.baseUrl}/emails`);
    }    
    
  logout(): Observable<string> {
    return this.http.post(`${this.baseUrl1}/logout`, null, {
      responseType: 'text'
    });
  }
      isAuthenticated(): boolean {
        const authToken = localStorage.getItem('authToken');
        const userEmail = localStorage.getItem('userEmail');
    
        return !!authToken && !!userEmail;
      }
  
    //   getCurrentUserProjects(): Observable<project[] | null> {
    //     const authToken = localStorage.getItem('authToken');
    //     const userEmail = localStorage.getItem('userEmail');
      
    //     if (authToken && userEmail) {
    //       return this.http.get<project[]>(`${this.baseUrl}/${userEmail}/projects`, {
    //         headers: { Authorization: `Bearer ${authToken}` }
    //       });
    //     } else {
    //       return of(null);
    //     }
      }
      
      
  