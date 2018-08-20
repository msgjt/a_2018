import {Component, OnInit, ViewChild} from '@angular/core';
import {User, UserService} from "../services/user.service";
import {Popup} from "ng2-opd-popup";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  userModel: User;
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


  signUpForm() {
    this.userService.addUser(this.userModel.firstName,this.userModel.lastName,this.userModel.email,this.userModel.mobileNumber,this.userModel.username, this.userModel.password)
      .subscribe(
        (response) => {
          console.log('response ' + JSON.stringify(response));
        },
        (error) => {
          this.errorMessage = error['error'];
          this.popup.show(this.popup.options);
        }
      );
  }
}
