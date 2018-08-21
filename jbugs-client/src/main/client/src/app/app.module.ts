import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { SignUpComponent } from './user-management/sign-up/sign-up.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './user-management/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import { ContentComponent } from './user-management/content/content.component';
import {AuthenticatedGuard} from "./user-management/authenticated.guard";
import { ErrorComponent } from './error/error.component';
import {PopupModule} from "ng2-opd-popup";
import {RoleContentComponent} from "./role-management/role-content/role-content.component";
import { ProfileComponent } from './user-management/profile/profile.component';
import {MatTableModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/login'
  },
  {
    path: 'register', component: SignUpComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'content', component: ContentComponent,  canActivate: [AuthenticatedGuard]
  },
  {
    path: 'roles', component: RoleContentComponent
  },
  {
    path: 'error', component: ErrorComponent
  },
  {
    path: 'profile', component: ProfileComponent, canActivate: [AuthenticatedGuard]
  },
  {
    path: '**', component: ErrorComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LoginComponent,
    ContentComponent,
    RoleContentComponent,
    ErrorComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    PopupModule.forRoot(),
    MatTableModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
