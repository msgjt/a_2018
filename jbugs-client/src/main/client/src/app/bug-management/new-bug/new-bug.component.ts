import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {BugService} from "../services/bug.service";
import {User, UserService} from "../../user-management/services/user.service";
import {Form, FormControl, FormGroup} from "@angular/forms";
import {Warning} from "../../communication/communication.component";

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
  validExtensions: string[] = ['jpg','pdf','doc','odf','xlsx','xls'];
  fileNotValid: boolean;
  warningMessage: Warning;

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
    this.warningMessage = {
      message: "Your file is not supported, so it wasn't added",
      recommendation: "Choose another file or edit the bug later if you want to change it",
      display: false
    };

    this.userService.getAllUsers().subscribe(
      (users) => {this.userList = users;}
    );

  }



  validateAllFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        this.validateAllFields(control);
      }
    });
  }

  validate(){
    this.submitAddPerformed = true;
    console.log("aaaaa");
  }


  submitAddData(ngModel){
    this.submitAddPerformed = true;
    this.errorOccurred = false;
    this.errorMessage = "";
    this.fileSendAttempted = false;
    let internalFileSendAttempted = false;

    let currentUsername = localStorage.getItem("currentUser");
    let currentUser = this.userList.find(user => user.username == currentUsername);
    if (currentUser === undefined){
      this.errorMessage = {id: "5001",type: "Current user could not be retrieved"};
      this.positiveResponse = false;
      this.errorOccurred = true;
      this.submitAddPerformed = true;
      return;
    }
    this.bugModel.createdBy = currentUser;
    let assignedUsername = this.bugModel.assignedTo.username;
    let assignedUser = this.userList.find(user => user.username == assignedUsername);
    if (assignedUser === undefined){
      this.errorMessage = { id:"5002",type: "Can not find the assigned user.",details:[]};
      this.positiveResponse = false;
      this.errorOccurred = true;
      this.submitAddPerformed = true;
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
                  this.warningMessage.display = false;
                  internalFileSendAttempted = true;
                  this.resetBugModel();
                  this.submitAdd();
                },
                (error) => {
                  this.positiveResponse = false;
                  this.errorMessage = error['error'];
                  this.errorOccurred = true;
                  this.fileSendAttempted = true;
                  internalFileSendAttempted = true;
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
          if( internalFileSendAttempted == false)
            this.fileSendAttempted = true;
        },
        (error) => {
          this.positiveResponse = false;
          this.errorMessage = error['error'];
          this.errorOccurred = true;
          this.submitAddPerformed = true;
          if( internalFileSendAttempted == false)
            this.fileSendAttempted = true;
        }
      );
  }

  fileChange(event){
    this.warningMessage.display = false;
    let files = event.target.files;
    if (files.length > 0) {
      this.formData = new FormData();
      let filename = files[0].name;
      let splitted = filename.split(".");
      let extension = splitted[splitted.length-1];
      let isValidExtension = this.validExtensions.find(ext => ext == extension);
      if (isValidExtension !== undefined) {
        this.formData.append('file', files[0]);
        this.bugModel.attachment = files[0].name;
      }
      else {
        this.warningMessage.display = true;
        this.fileSendAttempted = true;
      }
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
    if(this.errorOccurred == false) {
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
