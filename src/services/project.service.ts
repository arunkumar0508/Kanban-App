import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private apiUrl = 'http://localhost:9999/apii/users';

  constructor(private http: HttpClient) {}

  private getAuthToken(): string | null {
    return localStorage.getItem('authToken');
  }

  private createHeaders(): HttpHeaders {
    const authToken = this.getAuthToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${authToken}` 
    });
  }

  getProjects(): Observable<any[]> {
    const headers = this.createHeaders();
    const apiUrlWithDynamicEmail = `${this.apiUrl}/projects`; 
    return this.http.get<any[]>(apiUrlWithDynamicEmail, { headers });
  }
  addProject(newProject: any): Observable<any> {
    const headers = this.createHeaders();
    const apiUrlWithDynamicEmail = `${this.apiUrl}/projects`; 
    return this.http.post<any>(apiUrlWithDynamicEmail, newProject, {
      headers
    });
  }
  getProjectDetails(email:string,projectId: any): Observable<any> {
    const headers = this.createHeaders();
    const apiUrlWithProjectId = `${this.apiUrl}/projects/${email}/${projectId}`; 
    return this.http.get<any>(apiUrlWithProjectId, { headers });
  }
  createTaskInFirstColumn(projectId: string, task: any): Observable<any> {
    const headers = this.createHeaders();
    const apiUrlWithProjectId = `${this.apiUrl}/${projectId}/tasks`; 
    return this.http.put<any>(apiUrlWithProjectId, task, { headers });
}

deleteTask( projectId: string, taskToDelete: any): Observable<any> {
  const headers = this.createHeaders();
  const apiUrlWithDynamicEmailAndProjectId = `${this.apiUrl}/${projectId}/task`;

  return this.http.delete(apiUrlWithDynamicEmailAndProjectId, {
    headers,
    body: taskToDelete 
  });
}

updateTask(projectId: string, task: any, targetColumn: string): Observable<any> {
  const headers = this.createHeaders();
  const apiUrlWithProjectId = `${this.apiUrl}/${projectId}/tasks/move`; 
  const requestBody = {
    task,
    targetColumn
  };

  return this.http.put<any>(apiUrlWithProjectId, requestBody, { headers });
}


  
}
