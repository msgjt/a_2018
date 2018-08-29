import {Component, OnInit} from '@angular/core';
import {User, UserService} from "../user-management/services/user.service";
import {Router} from "@angular/router";

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
  showUpdate:boolean;
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
    this.showUpdate= false;
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


  submitEditForm() {
    console.log(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber,
      this.userModel.roles);
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

  isLoggedInOnServer() {
    let returnedValue: boolean;
    let test = this.userService.isLoggedInOnServer().subscribe(response => {
      if (response == true) {
      } else {
        this.router.navigate(['/norights']);
      }
    })
  }


}
