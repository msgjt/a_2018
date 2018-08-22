import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { SignUpComponent } from './user-management/sign-up/sign-up.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './user-management/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticatedGuard} from "./user-management/guards/authenticated.guard";
import { ErrorComponent } from './error/error.component';
import {PopupModule} from "ng2-opd-popup";
import { ProfileComponent } from './user-management/profile/profile.component';
import {MatTableModule} from "@angular/material";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RECAPTCHA_LANGUAGE, RecaptchaModule} from "ng-recaptcha";
import { RecaptchaFormsModule } from "ng-recaptcha/forms";
import { HomeComponent } from './home/home.component';
import {RedirectGuard} from "./user-management/guards/redirect.guard";
import { RolesComponent } from './role-management/roles/roles.component';

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/home'
  },
  {
    path: 'register', component: SignUpComponent
  },
  {
    path: 'login', component: LoginComponent, canActivate: [RedirectGuard]
  },
  {
    path: 'home', component: HomeComponent,  canActivate: [AuthenticatedGuard]
  },
  {
    path: 'roles', component: RolesComponent
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
    ErrorComponent,
    ProfileComponent,
    HomeComponent,
    RolesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    PopupModule.forRoot(),
    MatTableModule,
    BrowserAnimationsModule,
    RecaptchaModule.forRoot(),
    RecaptchaFormsModule
  ],
  providers: [
    {
      provide: RECAPTCHA_LANGUAGE,
      useValue: 'en'
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
