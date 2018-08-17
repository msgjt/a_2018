import {Component, OnInit} from '@angular/core';
import {User, UserService} from "../services/user.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  userModel: User;

  constructor(private userService: UserService) {
    this.userModel = {
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

  ngOnInit() {
  }


  signUpForm() {
    this.userService.addUser(this.userModel.firstName,this.userModel.lastName,this.userModel.email,this.userModel.mobileNumber,this.userModel.username, this.userModel.password)
      .subscribe(
        (response) => {
          console.log('response ' + JSON.stringify(response));
        }
      );
  }

  getAll() {
    this.userService.getAllUsers().subscribe((user) => {
      console.log(user);
    });
  }
}
