import {Component, OnInit} from '@angular/core';
import {User, UserService} from "../user-management/services/user.service";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  pressedEdit: boolean = false;
  userModel: User;
  userList: User[];
  errorMessage: string;
  errorOccurred: boolean;
  positiveResponse: boolean;
  showInfoDiv: boolean;
  showUpdate: boolean;
  showPassword: boolean;
  newPassword: string;
  newPasswordConfirmed: string;

  constructor(private userService: UserService, private router: Router) {
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
    this.errorOccurred = false;
    this.positiveResponse = false;
    this.showInfoDiv = false;
    this.showUpdate = false;
    this.showPassword = false;
    this.userModel.username = localStorage.getItem('currentUser');
    this.userModel.id = Number(localStorage.getItem('id'));
  }

  ngOnInit() {
    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    });
    this.userModel.username = localStorage.getItem('currentUser');
    this.userModel.id = Number(localStorage.getItem('id'));
    this.isLoggedInOnServer()
  }
  refresh(){
    this.userList = [];
    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    },(error)=>{
      if(error.status == 403){
        this.router.navigate(['/error']);
      }
      if(error.status == 401){
        this.router.navigate(['/norights']);
      }
    });}

  submitEditForm() {
    this.userModel.firstName = this.changeToEmptyString(this.userModel.firstName);
    this.userModel.lastName = this.changeToEmptyString(this.userModel.lastName);
    this.userModel.email = this.changeToEmptyString(this.userModel.email);
    this.userModel.phoneNumber = this.changeToEmptyString(this.userModel.phoneNumber);

    this.userService.updateUser(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber, this.userModel.roles)
      .subscribe(
        (response) => {
          console.log('response ' + JSON.stringify(response));
          this.errorOccurred = false;
          this.positiveResponse = true;
        },
        (error) => {
          this.errorMessage = error['error'];
          this.errorOccurred = true;
          this.positiveResponse = false;
        }
      );
  }


  submitEditPasswordForm() {
    if (this.newPassword == this.newPasswordConfirmed) {
      this.userService.updateUserPassword(this.userModel.id, this.newPassword)
        .subscribe(
          (response) => {
            console.log('response ' + JSON.stringify(response));
            this.errorOccurred = false;
            this.positiveResponse = true;
          },
          (error) => {
            this.errorMessage = error['error'];
            this.errorOccurred = true;
            this.positiveResponse = false;
          }
        );
    }
    else {
      this.errorOccurred = true;
      this.positiveResponse = false;
    }
  }

  isLoggedInOnServer() {
    let returnedValue: boolean;
    let test = this.userService.isLoggedInOnServer().subscribe(response => {
      if (response == true) {
      } else {
        this.router.navigate(['/norights']);
      }
    })
  }

  changeToEmptyString(value: string): string {
    if (value == "null" || value == " " || value == null)
      value = "";
    return value;
  }

}
