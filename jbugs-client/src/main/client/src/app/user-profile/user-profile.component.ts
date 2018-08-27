import {Component, OnInit, ViewChild} from '@angular/core';
import {User, UserService} from "../user-management/services/user.service";

import {Role} from "../role-management/entities/role";
import {Router} from "@angular/router";

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

  constructor(private userService: UserService, private router:Router) {
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

  ngOnInit() {
    this.isLoggedInOnServer()
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


}
