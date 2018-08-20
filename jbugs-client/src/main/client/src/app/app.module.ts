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
import { RoleContentComponent } from './role-management/role-content/role-content.component';

const appRoutes: Routes = [
  {
    path: 'sign-up', component: SignUpComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'content', component: ContentComponent
  },
  {
    path: 'roles', component: RoleContentComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LoginComponent,
    ContentComponent,
    RoleContentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
