import {Component, OnInit, ViewChild} from '@angular/core';
import {User, UserService} from "../user-management/services/user.service";

import {Popup} from 'ng2-opd-popup';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  pressedEdit: boolean=false;
  userModel: User;
  errorMessage: string;
  @ViewChild('popup') popup: Popup;


  constructor(private userService: UserService) {
  }

  ngOnInit() {

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
    this.userModel.username = localStorage.getItem('currentUser');
    this.userModel.id = Number(localStorage.getItem('id'))+1;
  }


  submitEditForm() {
    console.log(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber);
    this.userService.updateUser(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber, this.userModel.roles)
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
