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

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/login'
  },
  {
    path: 'sign-up', component: SignUpComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'content', component: ContentComponent,  canActivate: [AuthenticatedGuard]
  },
  {path: 'error', component: ErrorComponent},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LoginComponent,
    ContentComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    PopupModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
