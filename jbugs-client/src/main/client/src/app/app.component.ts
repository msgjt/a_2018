import {Component, Injectable, Input} from '@angular/core';
import {LSKEY, TOKENKEY} from "./user-management/services/user.service";
import {ProfileComponent} from "./user-management/profile/profile.component";
import {Router} from "@angular/router";
import {TranslateService} from '@ngx-translate/core';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'JBugs';
  loggedIn = true;

  constructor(private router: Router,private translate: TranslateService){

    translate.setDefaultLang('en');
  }



  isUserLoggedIn(): boolean {
    return localStorage.getItem('currentUser') != null;
  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      this.router.navigate(['./login']);
      localStorage.removeItem(LSKEY);
      localStorage.removeItem(TOKENKEY);
      this.loggedIn = false;
    }
  }
  useLanguage(language: string) {
    this.translate.use(language);
  }
}
