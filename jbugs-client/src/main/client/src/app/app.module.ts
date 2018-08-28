import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './user-management/login/login.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticatedGuard} from "./user-management/guards/authenticated.guard";
import {ErrorComponent} from './error/error.component';
import {ProfileComponent} from './user-management/profile/profile.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BugsViewComponent} from './bug-management/bugs-view/bugs-view.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {RECAPTCHA_LANGUAGE, RecaptchaModule} from "ng-recaptcha";
import {RecaptchaFormsModule} from "ng-recaptcha/forms";
import {HomeComponent} from './home/home.component';
import {RedirectGuard} from "./user-management/guards/redirect.guard";
import {RolesComponent} from './role-management/roles/roles.component';
import {UserProfileComponent} from './user-profile/user-profile.component';
import {NgxPaginationModule} from 'ngx-pagination';

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
import {NorightsComponent} from './norights/norights.component';
import {FilterPipe} from './filter.pipe';
import {EditBugComponent} from './bug-management/edit-bug/edit-bug.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {ToastrModule} from "ngx-toastr";

const appRoutes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo: '/home'
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
    path: 'norights', component: NorightsComponent
  },
  {
    path: 'userProfile', component: UserProfileComponent,canActivate: [AuthenticatedGuard]
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
  ],
  declarations: [NorightsComponent]
})
export class MaterialModule {}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorComponent,
    ProfileComponent,
    BugsViewComponent,
    EditBugComponent,
    HomeComponent,
    UserProfileComponent,
    RolesComponent,
    FilterPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
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
    }),
    NgxPaginationModule,
    NgbModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-right',
      enableHtml: true,
      progressBar: true,
      progressAnimation: "decreasing",

    })
  ],
  providers: [
    {
      provide: RECAPTCHA_LANGUAGE,
      useValue: 'en'
    }
  ],
  exports: [
    FilterPipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
