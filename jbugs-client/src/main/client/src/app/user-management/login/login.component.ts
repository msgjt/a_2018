import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {HttpClient} from "../../../../node_modules/@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userModel: User;
  loggedIn = false;
  baseURL = 'http://localhost:8080/jbugs/rest';
  recaptchaResponse: any;
  RecaptchaOptions = { theme : 'clean' };
  errorOccurred: boolean;
  errorMessage: string;
  usernameError: boolean;
  notificationsList: Notification[];
  oldNotificationsList: Notification[];

  @ViewChild('container-login-username') containerUsername: ElementRef;



  constructor(private userService: UserService, private router: Router, private http: HttpClient,
              private toastrService: ToastrService) {
    this.userModel = {
      id: 0,
      firstName: '',
      lastName: '',
      isActive: false,
      phoneNumber: '',
      email: '',
      roles: [],
      username: '',
      password: ''
    };
    this.loggedIn = userService.isLoggedIn();
    this.errorOccurred = false;
  }

  ngOnInit() {
    this.usernameError = false;
  }

  submitForm() {
   /* this.http.post(this.baseURL + '/captcha', this.recaptchaResponse).subscribe((response) => {
      console.log(response);
      if(response['success'] == true) {
        console.log('Form was submitted with the following data:' +
          JSON.stringify(this.userModel));*/
        this.userService.validateUserCredentials(this.userModel.username,
          this.userModel.password).subscribe(
          (response) => {
              this.login(response.token);
              localStorage.setItem("id",response.id);
              this.getUsersPermissions(this.userModel.username);
              this.loggedIn = true;
              this.getOldUsersNotifications();
              this.getUsersNotifications();
              this.router.navigate(['./profile']);
          },
          (error) => {
            console.log('ERROR: ' + JSON.stringify(error['error']));
            if(error['error'] == "{id=1000206, type=USER_VALIDATION_EXCEPTION, details={USER_LOGIN_FAILED_FIVE_TIMES}}") {
              this.errorMessage = 'Login failed 5 times. Your account has been disabled.';
            }
            else {
              if (error['error'] == "{id=1000207, type=USER_VALIDATION_EXCEPTION, details={USER_DISABLED}}") {
                this.errorMessage = 'User disabled';
              }
              else {
                if( ! this.userModel.username ) {
                  this.usernameError = true;
                  this.errorMessage = 'Username not valid';
                }
                if( ! this.userModel.password) {
                  this.errorMessage = 'Password not valid';
                }
              }
            }
            this.errorOccurred = true;
            this.loggedIn = false;
            console.log(JSON.stringify(error));

          });
      }




  login(token: string) {
    localStorage.setItem(LSKEY, this.userModel.username);
    console.log('saving token: ' + token);
    localStorage.setItem(TOKENKEY, token);
    this.loggedIn = true;
  }

  resolved(captchaResponse: string) {
    this.recaptchaResponse = captchaResponse;
  }

  getUsersPermissions(username: string){
    this.userService.getUsersPermissions(username).subscribe(
      (list)=>{
        console.log(list);
        for(let i=0; i<list.length;i++){
          localStorage.setItem(list[i].type,"1");
        }
      }
    )
  }

  getUsersNotifications(){
    /*
    this.notificationService.getNewNotificationForUser(Number(localStorage.getItem('id')))
      .subscribe(notifications=>{this.notificationsList=notifications,
        console.log(this.notificationsList),
        this.getUsersNotifications()
        },
        ()=>{this.getOldUsersNotifications()});
        */  }

  getOldUsersNotifications(){
    /*
    this.notificationService.getOldNotificationForUser(Number(localStorage.getItem('id')))
      .subscribe(notifications=>{this.oldNotificationsList=notifications,
        this.toastrService.info(this.oldNotificationsList[0]["type"],this.oldNotificationsList[0]["type"])
          .onShown.subscribe(()=>{
           // let sound =new Audio('../../assets/notificationsound.mp3');
           // sound.play();
          console.log(this.oldNotificationsList[0])
        })
        });
        */
  }
}
