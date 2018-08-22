import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import { Popup } from  'ng2-opd-popup';
import {Role} from "../../role-management/entities/role";

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
  pressedEdit: boolean = false;
  @Input('show-modal') showModal: boolean;
  @ViewChild('addUserPopup') addUserPopup: Popup;
  pressedAdd: boolean = false;
  userModel: User;
  activeUser: boolean;
  errorMessage: string;
  roles: Role[];
  @ViewChild('popup') errorPopup: Popup;


  constructor(private userService: UserService, private router: Router) {
    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    });
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
  }

  ngOnInit() {
    this.errorPopup.options = {
      header: "Error",
      color: "darkred", // red, blue....
      widthProsentage: 30, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      animation: "fadeInDown" // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };
  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      this.router.navigate(['./login']);
      localStorage.removeItem(LSKEY);
      localStorage.removeItem(TOKENKEY);
      this.loggedIn = false;
    }
  }

  someClickHandler(info: any,  id: number, activeState: boolean): void {
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
    this.userModel.id = id;
    this.activeUser = activeState;
    this.popup.show(this.popup.options);
  }

  showEditPopup() {
    this.pressedEdit = true;
  }

  disableUser() {
    this.userService.deactivateUser(this.userModel.id).subscribe(
      (response) => {
        console.log('response ' + JSON.stringify(response));
      },
      (error) => {
        this.errorMessage = error['error'];
        this.popup.show(this.popup.options);
      }
    );
    this.userList[this.userModel.id - 1].isActive = false;
    this.activeUser = false;

  }

  enableUser() {
    this.userService.activateUser(this.userModel.id).subscribe(
      (response) => {
        console.log('response ' + JSON.stringify(response));
      },
      (error) => {
        this.errorMessage = error['error'];
        this.popup.show(this.popup.options);
      }
    );
    this.userList[this.userModel.id - 1].isActive = true;
    this.activeUser = true;

  }

  submitEditForm() {
    this.userService.updateUser(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber)
      .subscribe(
        (response) => {
          this.userService.getAllUsers().subscribe((user)=>this.userList=user);
          console.log('response ' + JSON.stringify(response));
        },
        (error) => {
          this.errorMessage = error['error'];
          this.popup.show(this.popup.options);
        }
      );
  }

  hidePopup() {
    this.popup.hide();
    this.pressedEdit = false;
  }

  passDataToModal(user: User) {
    this.message = 'You have clicked on the row containing: \n' + 'Username: ' + user.username + ', First name: ' + user.firstName
      + ', Last name: ' + user.lastName + 'Phone number: ' + user.phoneNumber +', E-mail: ' + user.email;
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
    this.userService.getAllRoles().subscribe(
      (roles) => {this.roles = roles;}
    );
  }

  submitAddForm(){
    this.roles.forEach(role =>
    {
      if (role.selected){
        role.selected = false;
        this.userModel.roles.push(role);
      }
    });
    this.userService.addUser(this.userModel.firstName,this.userModel.lastName,this.userModel.email,this.userModel.phoneNumber,this.userModel.username, this.userModel.password, this.userModel.roles)
      .subscribe(
        (response) => {
          console.log('response ' + JSON.stringify(response));
        },
        (error) => {
          this.errorMessage = error['error'];
          this.errorPopup.show(this.errorPopup.options);
        }
      );
    this.hideAddPopup();
  }

  hideAddPopup(){
    this.addUserPopup.hide();
  }

  hideErrorPopup(){
    this.errorPopup.hide();
  }
}
