import { Component, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import { Popup } from  'ng2-opd-popup';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  loggedIn = true;
  message = '';
  userList: User[];
  @ViewChild('popup') popup: Popup;
  columnsToDisplay = ['userName', 'firstName', 'lastName', 'email'];
  pressedEdit: boolean = false;

  constructor(private userService: UserService, private router: Router) {

    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    });
  }

  ngOnInit() {

  }



  someClickHandler(info: any): void {
    this.message = 'You have clicked on the row containing: \n' + 'Username: ' + info.username + ', First name: ' + info.firstName
    + ', Last name: ' + info.lastName + ', E-mail: ' + info.email;
    this.popup.options = {
      header: "User info",
      color: "darkred", // red, blue....
      widthProsentage: 30, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      animation: "fadeInDown" // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };

    this.popup.show(this.popup.options);
  }

  showEditPopup() {
    this.pressedEdit = true;
  }

  disableUser() {

  }

  submitEditForm() {

  }

  hidePopup() {
    this.popup.hide();
    this.pressedEdit = false;
  }
}
