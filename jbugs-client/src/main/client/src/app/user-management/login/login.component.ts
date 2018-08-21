import {Component, OnInit} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userModel: User;
  wrongCredentials = false;
  loggedIn = false;

  constructor(private userService: UserService, private router: Router) {
    this.userModel = {
      firstName: '',
      lastName: '',
      isActive: 0,
      mobileNumber: '',
      email: '',
      roles: '',
      username: '',
      password: ''
    };
    this.loggedIn = userService.isLoggedIn();
  }

  ngOnInit() {
  }

  submitForm() {
    console.log('Form was submitted with the following data:' +
      JSON.stringify(this.userModel));
    this.userService.validateUserCredentials(this.userModel.username,
      this.userModel.password).subscribe((response) => {
      console.log('credentials are valid is : ' + response);
      if (response) {
        this.loggedIn = true;
        this.wrongCredentials = false;
        this.login(response.token);
        this.router.navigate(['./profile']);
      } else {
        this.wrongCredentials = true;
        this.loggedIn = false;
      }
    });
  }

  login(token: string) {
    localStorage.setItem(LSKEY, this.userModel.username);
    console.log('saving token: ' + token);
    localStorage.setItem(TOKENKEY, token);
    this.loggedIn = true;
  }
}
