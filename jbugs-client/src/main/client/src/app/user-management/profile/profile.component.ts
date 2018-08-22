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
  @ViewChild('addUserPopup') addUserPopup: Popup;
  pressedAdd: boolean = false;
  userModel: User;
  errorMessage: string;


  constructor(private userService: UserService, private router: Router) {

    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    });
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
  }

  ngOnInit() {

  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      this.router.navigate(['./login']);
      localStorage.removeItem(LSKEY);
      localStorage.removeItem(TOKENKEY);
      this.loggedIn = false;
    }
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

  showAddPopup(){
    this.pressedAdd = true;
    this.addUserPopup.options = {
      header: "Add user",
      color: "darkred", // red, blue....
      widthProsentage: 30, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      animation: "fadeInDown" // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };
    this.addUserPopup.show(this.addUserPopup.options);

  }

  submitAddForm(){
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
