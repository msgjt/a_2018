import {Component, OnInit, ViewChild} from '@angular/core';
import {User, UserService} from "../user-management/services/user.service";
import {Router} from "@angular/router";
import {Error, Success} from "../communication/communication.component";
import {NgForm} from "@angular/forms";
import {UtilService} from "../shared/util.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  pressedEdit: boolean = false;
  userModel: User;
  userList: User[];
  errorMessage: Error;
  errorOccurred: boolean;
  positiveResponse: boolean;
  showInfoDiv: boolean;
  showUpdate: boolean;
  showPassword: boolean;
  newPassword: string;
  newPasswordConfirmed: string;
  submitEditPerformed: boolean = false;
  submitEditPassPerformed: boolean = false;
  successMessage: Success;
  @ViewChild('formControl') formControl: NgForm;


  constructor(private userService: UserService, private router: Router, private utilService: UtilService) {
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
    this.userService.getAllUsersForUserProfile().subscribe((user) => {
      this.userList = user;
    });
    this.userModel.username = localStorage.getItem('currentUser');
    this.userModel.id = Number(localStorage.getItem('id'));
    this.isLoggedInOnServer();
    this.showInfoDiv = true;
    this.successMessage = {
      message: "Action completed successfully",
      display: false
    };
    this.successMessage.display = false;
    this.formControl.valueChanges.subscribe(() => this.successMessage.display = false);
  }

  refresh(){
    this.userList = [];
    this.userService.getAllUsersForUserProfile().subscribe((user) => {
      this.userList = user;
    },(error)=>{
      if(error.status == 403){
        localStorage.clear();
        this.router.navigate(['/login']);
      }
      if(error.status == 401){
        this.router.navigate(['/norights']);
      }
    });}

  submitEditForm() {
    this.submitEditPerformed = true;
    this.userModel.firstName = this.changeToEmptyString(this.userModel.firstName);
    this.userModel.lastName = this.changeToEmptyString(this.userModel.lastName);
    this.userModel.email = this.changeToEmptyString(this.userModel.email);
    this.userModel.phoneNumber = this.changeToEmptyString(this.userModel.phoneNumber);

    this.userService.updateUserForUserProfile(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber, this.userModel.roles)
      .subscribe(
        (response) => {
          this.errorOccurred = false;
          this.successMessage.display = true;
        },
        (error) => {
          this.errorMessage = error['error'];
          this.errorOccurred = true;
          this.successMessage.display = false;
        }
      );
  }


  submitEditPasswordForm() {
    this.submitEditPassPerformed = true;
    if (this.newPassword == this.newPasswordConfirmed) {
      this.userService.updateUserPasswordForUserProfile(this.userModel.id, this.newPassword)
        .subscribe(
          (response) => {
            this.errorOccurred = false;
            this.successMessage.display = true;
          },
          (error) => {
            this.errorMessage = error['error'];
            this.errorOccurred = true;
            this.successMessage.display = false;
          }
        );
    }
    else {
      this.errorOccurred = true;
      this.successMessage.display = false;
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

  isLoggedIn() {
    let result = localStorage.getItem("currentUser") != null;
    if(result == false)
      this.router.navigate(['/login']);
    return result;
  }
}
