import {Component} from '@angular/core';
import {LSKEY, UserService} from "./user-management/services/user.service";
import {Router} from "@angular/router";
import {TranslateService} from '@ngx-translate/core';
import {NotificationService} from "./notifications/services/notification.service";
import {NotificationComponent} from "./notifications/notification/notification.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'JBugs';
  loggedIn = true;
  newNotificationList;

  constructor(private router: Router, private translate: TranslateService,
              private userService: UserService, private notificationService : NotificationService) {
    translate.setDefaultLang('en');
  }


  isUserLoggedIn(): boolean {
    return localStorage.getItem('currentUser') != null;
  }

  isADRESSED_USER(): boolean {
    return localStorage.getItem('ADRESSED_USER') != null;
  }

  isUSER_MANAGEMENT(): boolean {
    return localStorage.getItem('USER_MANAGEMENT') != null;
  }

  isBUG_EXPORT_PDF(): boolean {
    return localStorage.getItem('BUG_EXPORT_PDF') != null;
  }

  isBUG_CLOSE(): boolean {
    return localStorage.getItem('BUG_CLOSE') != null;
  }

  isPERMISSION_MANAGEMENT(): boolean {
    return localStorage.getItem('PERMISSION_MANAGEMENT') != null;
  }

  isBUG_MANAGEMENT(): boolean {
    return localStorage.getItem('BUG_MANAGEMENT') != null;
  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      this.userService.logout(localStorage.getItem(LSKEY)).subscribe(response => console.log());
      this.router.navigate(['./login']);
      localStorage.clear();
      this.loggedIn = false;
    }
  }

  useLanguage(language: string) {
    this.translate.use(language);
  }

  getNewNotifications(){
    this.notificationService.getNewNotifications().subscribe(notification=>{
      this.newNotificationList=notification;
    })
  }

  size(): number {
    return NotificationComponent.size();
  }
}
