import {Component, Input, OnInit, ViewChild} from '@angular/core';
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
  pressedEdit: boolean = false;
  @Input('show-modal') showModal: boolean;

  constructor(private userService: UserService, private router: Router) {
    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    });
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

  showEditPopup() {
    this.pressedEdit = true;
  }

  disableUser() {

  }

  submitEditForm() {

  }

  passDataToModal(user: User) {
    this.message = 'You have clicked on the row containing: \n' + 'Username: ' + user.username + ', First name: ' + user.firstName
      + ', Last name: ' + user.lastName + 'Phone number: ' + user.phoneNumber +', E-mail: ' + user.email;
  }
}
