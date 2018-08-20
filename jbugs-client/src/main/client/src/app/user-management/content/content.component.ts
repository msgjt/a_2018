import { Component, OnInit } from '@angular/core';
import {User, UserService} from "../services/user.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  userList: User[];
  userModel: User;
  constructor(private userService: UserService) {
    userService.getAllUsers().subscribe((users) => {
      console.log(users);
      this.userList = users;
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

  updateUser(id:number, firstname:string,lastname:string,email:string,mobile:string,username:string,password:string) {
    this.userService.updateUser(id, firstname,lastname,email,mobile,username,password)
      .subscribe(
        (response) => {
          console.log('response ' + JSON.stringify(response));
        }
      );
  }

  ngOnInit() {
  }

}
