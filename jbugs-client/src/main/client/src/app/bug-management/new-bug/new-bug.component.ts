import {Component, Input, OnInit} from '@angular/core';
import {BugService} from "../services/bug.service";
import {User, UserService} from "../../user-management/services/user.service";

@Component({
  selector: 'app-new-bug',
  templateUrl: './new-bug.component.html',
  styleUrls: ['./new-bug.component.css']
})
export class NewBugComponent implements OnInit {

  bugModel;
  errorMessage: string;
  errorOccurred: boolean = false;
  userList: User[];
  formData: FormData;
  positiveResponse: boolean = false;
  showInfoDiv: boolean = false;

  constructor(private bugService: BugService, private userService: UserService) { }

  ngOnInit() {
    this.bugModel = {
      id: 0,
      title: '',
      description: '',
      status: '',
      severity: '',
      fixedVersion: '',
      targetDate: '',
      version: '',
      attachment: '',
      assignedTo: {
        id: 0,
        firstName: '',
        lastName: '',
        isActive: false,
        phoneNumber: '',
        email: '',
        roles: [],
        username: '',
        password: ''
      },
      createdBy: {
        id: 0,
        firstName: '',
        lastName: '',
        isActive: false,
        phoneNumber: '',
        email: '',
        roles: [],
        username: '',
        password: ''
      }
    }

    this.userService.getAllUsers().subscribe(
      (users) => {this.userList = users;}
    );
  }

  submitAddData(){
    let currentUsername = localStorage.getItem("currentUser");
    let currentUser = this.userList.find(user => user.username == currentUsername);
    if (currentUser === undefined){
      this.errorMessage = 'Current user could not be retrieved';
      this.errorOccurred = true;
      return;
    }
    this.bugModel.createdBy = currentUser;
    let assignedUsername = this.bugModel.assignedTo.username;
    let assignedUser = this.userList.find(user => user.username == assignedUsername);
    if (assignedUser === undefined){
      this.errorMessage = 'Cannot find the assigned user';
      this.errorOccurred = true;
      return;
    }
    this.bugModel.assignedTo = assignedUser;

    this.bugService.createBug(this.bugModel)
      .subscribe(
        (response) => {
          if(this.formData.has('file')) {
            this.formData.append('bugId', response.id.toString());
            this.bugService.sendFile(this.formData)
              .subscribe(
                () => {
                  this.errorOccurred = false;
                  this.positiveResponse = true;
                },
                (error) => {
                  this.positiveResponse = false;
                  this.errorMessage = error['error'];
                  this.errorOccurred = true;
                }
              );
          }
          else {
            this.errorOccurred = false;
            this.positiveResponse = true;
          }
        },
        (error) => {
          this.positiveResponse = false;
          this.errorMessage = error['error'];
          this.errorOccurred = true;
        }
      );
  }

  fileChange(event){
    let files = event.target.files;
    if (files.length > 0) {
      this.formData = new FormData();
      this.formData.append('file', files[0]);
      this.bugModel.attachment = files[0].name;
    }
  }

  showInfo() {
    this.showInfoDiv = true;
  }

  hideInfo() {
    this.showInfoDiv = false;
  }
}
