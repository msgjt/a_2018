import {Component, OnInit, ViewChild} from '@angular/core';
import {User, UserService} from "../user-management/services/user.service";

import {Role} from "../role-management/entities/role";
import {Router} from "@angular/router";
import {NotificationService} from "../notification.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  pressedEdit: boolean=false;
  userModel: User;
  userList: User[];
  errorMessage: string;
  notificationsList : Notification[];

  constructor(private userService: UserService, private router:Router,
              private notificationService: NotificationService) {
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
    this.userModel.id = Number(localStorage.getItem('id'));
  }

  ngOnInit() {
    this.isLoggedInOnServer();
    this.getUsersNotifications()

  }


  submitEditForm() {
    console.log(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber,
      this.userModel.roles);
    this.userService.updateUser(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber, this.userModel.roles)
      .subscribe(
        (response) => {
          console.log('response ' + JSON.stringify(response));
        },
        (error) => {
          this.errorMessage = error['error'];
        }
      );
  }

  isLoggedInOnServer(){
    let returnedValue : boolean;
    let test = this.userService.isLoggedInOnServer().subscribe(response=>{
    if(response==true){
    }else{
      this.router.navigate(['/norights']);
    }})
  }

  getUsersNotifications(){
    this.notificationService.getNewNotificationForUser(Number(localStorage.getItem('id')))
      .subscribe(notifications=>{this.notificationsList=notifications,
        console.log(this.notificationsList),
        this.getUsersNotifications()});
  }


}
