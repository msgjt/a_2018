import {Component, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Popup} from "ng2-opd-popup";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userModel: User;
  wrongCredentials = false;
  loggedIn = false;
  @ViewChild('popup') popup: Popup;
  errorMessage: string;

  constructor(private userService: UserService) {
    this.userModel = {
      id: 0,
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
    this.popup = new Popup();
  }

  ngOnInit() {
    //configure the pop-up layout
    this.popup.options = {
      header: 'Error occurred',
      color: 'red', // red, blue....
      widthProsentage: 40, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      confirmBtnContent: 'OK', // The text on your confirm button
      cancleBtnContent: 'Cancel', // the text on your cancel button
      confirmBtnClass: 'btn btn-danger', // your class for styling the confirm button
      cancleBtnClass: 'btn btn-danger', // you class for styling the cancel button
      animation: 'fadeInDown' // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };
  }


  submitForm() {
    console.log('Form was submitted with the following data:' +
      JSON.stringify(this.userModel));
    this.userService.validateUserCredentials(this.userModel.username,
      this.userModel.password).subscribe(
        (response) => {
      console.log('credentials are valid is : ' + response);
      if (response) {
        this.loggedIn = true;
        this.wrongCredentials = false;
        this.login(response.token);
      } else {
        this.wrongCredentials = true;
        this.loggedIn = false;
      }
    },
      (error) => {
        this.errorMessage = error['error'];
        this.popup.show(this.popup.options);
      });
  }

  login(token: string) {
    localStorage.setItem(LSKEY, this.userModel.username);
    console.log('saving token: ' + token);
    localStorage.setItem(TOKENKEY, token);
    this.loggedIn = true;
  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      localStorage.removeItem(LSKEY);
      localStorage.removeItem(TOKENKEY);
      this.loggedIn = false;
    }
  }


}
