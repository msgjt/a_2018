import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {BugService} from "../services/bug.service";
import {User, UserService} from "../../user-management/services/user.service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-new-bug',
  templateUrl: './new-bug.component.html',
  styleUrls: ['./new-bug.component.css']
})
export class NewBugComponent implements OnInit {

  severityFormControl: FormControl;
  bugModel;
  errorMessage: any;
  fileSendAttempted: boolean = false;
  errorOccurred: boolean = false;
  userList: User[];
  formData: FormData;
  positiveResponse: boolean = false;
  possibleSeverities: string[] = ['LOW','MEDIUM','HIGH','CRITICAL'];
  showInfoDiv: boolean = false;
  submitAddPerformed: boolean = false;
  @ViewChild('closeBtn') closeBtn: ElementRef;

  constructor(private bugService: BugService, private userService: UserService) { }


  resetBugModel(){
    this.bugModel = {
      id: 0,
      title: '',
      description: '',
      status: '',
      severity: 'MEDIUM',
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
    };

    this.severityFormControl = new FormControl(this.possibleSeverities.find(s => s === 'MEDIUM'));
    this.formData = new FormData();

  }

  ngOnInit() {

    this.resetBugModel();


    this.userService.getAllUsers().subscribe(
      (users) => {this.userList = users;}
    );

  }

  submitAddData(){
    this.submitAddPerformed = true;
    this.errorOccurred = false;
    this.errorMessage = "";
    this.fileSendAttempted = false;


    let currentUsername = localStorage.getItem("currentUser");
    let currentUser = this.userList.find(user => user.username == currentUsername);
    if (currentUser === undefined){
      this.errorMessage = {id: "5001",type: "Current user could not be retrieved"};
      this.errorOccurred = true;
      this.submitAddPerformed = true;
      this.resetBugModel();
      return;
    }
    this.bugModel.createdBy = currentUser;
    let assignedUsername = this.bugModel.assignedTo.username;
    let assignedUser = this.userList.find(user => user.username == assignedUsername);
    if (assignedUser === undefined){
      this.errorMessage = { id:"5002",type: "Can not find the assigned user.",details:[]};
      this.errorOccurred = true;
      this.submitAddPerformed = true;
      this.resetBugModel();
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
                  this.fileSendAttempted = true;
                  this.submitAddPerformed = true;
                  this.resetBugModel();
                  this.submitAdd();
                },
                (error) => {
                  this.positiveResponse = false;
                  this.errorMessage = error['error'];
                  this.errorOccurred = true;
                  this.fileSendAttempted = true;
                  this.resetBugModel();
                }
              );
          }
          else {
            this.errorOccurred = false;
            this.positiveResponse = true;
            this.submitAddPerformed = true;
            this.resetBugModel();
            this.submitAdd();
          }
        },
        (error) => {
          this.positiveResponse = false;
          this.errorMessage = error['error'];
          this.errorOccurred = true;
          this.submitAddPerformed = true;
          this.resetBugModel();
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

  addBugSeverity(severity: string){
    this.bugModel.severity = severity;
  }

  showInfo() {
    this.showInfoDiv = true;
  }

  hideInfo() {
    this.showInfoDiv = false;
  }

  submitAdd(){
    if(this.errorOccurred == false && this.fileSendAttempted == true) {
      this.closeBtn.nativeElement.click();
    }
    else {
    }
  }

  startedEditing(): boolean {
    return this.bugModel.title != '' || this.bugModel.fixedVersion != '' ||
      this.bugModel.version != '' ||  this.bugModel.targetDate != '' ||
      this.bugModel.description != '';
  }

}
