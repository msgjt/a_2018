import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { SignUpComponent } from './user-management/sign-up/sign-up.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './user-management/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticatedGuard} from "./user-management/guards/authenticated.guard";
import { ErrorComponent } from './error/error.component';
import {PopupModule} from "ng2-opd-popup";
import { ProfileComponent } from './user-management/profile/profile.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { BugsViewComponent } from './bug-management/bugs-view/bugs-view.component';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {RECAPTCHA_LANGUAGE, RecaptchaModule} from "ng-recaptcha";
import { RecaptchaFormsModule } from "ng-recaptcha/forms";
import { HomeComponent } from './home/home.component';
import {RedirectGuard} from "./user-management/guards/redirect.guard";
import { RolesComponent } from './role-management/roles/roles.component';
import {HttpClient} from '@angular/common/http';

import {
  MatAutocompleteModule,
  MatBadgeModule,
  MatBottomSheetModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatTreeModule,
} from '@angular/material';
import {CdkTableModule} from "@angular/cdk/table";
import {CdkTreeModule} from "@angular/cdk/tree";

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
    path: 'home', component: HomeComponent
  },
  {
    path: 'roles', component: RolesComponent
  },
  {
    path: 'bugs', component: BugsViewComponent, canActivate: [AuthenticatedGuard]
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
  exports: [
    CdkTableModule,
    CdkTreeModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
  ]
})
export class MaterialModule {}

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LoginComponent,
    ErrorComponent,
    ProfileComponent,
    BugsViewComponent,
    HomeComponent,
    RolesComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    PopupModule.forRoot(),
    BrowserAnimationsModule,
    RecaptchaModule.forRoot(),
    RecaptchaFormsModule,
    MaterialModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    {
      provide: RECAPTCHA_LANGUAGE,
      useValue: 'ro'
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
