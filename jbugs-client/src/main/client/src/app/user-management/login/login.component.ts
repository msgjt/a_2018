import {Component, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {HttpClient} from "../../../../node_modules/@angular/common/http";
import {FormControl, FormGroup} from "@angular/forms";

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
  errorOccurred: boolean;
  errorMessage: string;

  constructor(private userService: UserService, private router: Router, private http: HttpClient) {
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
              this.getUsersPermissions(this.userModel.username);
              this.loggedIn = true;
              this.router.navigate(['./profile']);
          },
          (error) => {
            console.log('ERROR: ' + JSON.stringify(error['error']));
            if(error['error'] == 'aaa') {
              this.errorMessage = 'Login failed 5 times. Your account has been disabled.';
            }
            else {
              if (error['error'] == 'bbb') {
                this.errorMessage = 'User disabled';
              }
              else {
                this.errorMessage = 'Username or password not valid';
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
        for(let i=0; i<list.length;i++){
          localStorage.setItem(list[i].type,"1");
        }
      }
    )
  }
}
