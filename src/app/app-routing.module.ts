import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationComponent } from './authentication/authentication.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { ProjectComponent } from './project/project.component';
import { AddprojectComponent } from './addproject/addproject.component';
import { BoardComponent } from './board/board.component';
import { AuthGuard } from 'src/services/auth.guard';


const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'dashboard', component: UserDashboardComponent, canActivate: [AuthGuard] },
  { path: 'project', component: ProjectComponent, canActivate: [AuthGuard]}, 
  { path: 'project-details/:id', component: BoardComponent ,canActivate: [AuthGuard]}, 
  { path: 'addproject', component: AddprojectComponent, canActivate: [AuthGuard] },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
