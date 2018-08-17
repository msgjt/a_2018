import { Component, OnInit } from '@angular/core';
import {User, UserService} from "../services/user.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent implements OnInit {

  userList: User[];

  constructor(userService: UserService) {
    userService.getAllUsers().subscribe((user) => {
      console.log(user);
      this.userList = user;
    });
  }

  ngOnInit() {
  }

}
