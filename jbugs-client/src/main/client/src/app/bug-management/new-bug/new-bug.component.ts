import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {User, UserService} from "../../user-management/services/user.service";
import {Form, FormControl, FormGroup, NgForm, NgModel} from "@angular/forms";
import {Success, Warning} from "../../communication/communication.component";

@Component({
  selector: 'app-new-bug',
  templateUrl: './new-bug.component.html',
  styleUrls: ['./new-bug.component.css']
})
export class NewBugComponent implements OnInit {

  @Input() bugList: Bug[];
  severityFormControl: FormControl;
  bugModel;
  errorMessage: any;
  userList: User[];
  formData: FormData;
  possibleSeverities: string[] = ['LOW','MEDIUM','HIGH','CRITICAL'];
  showInfoDiv: boolean = false;
  validateInput: boolean = false;
  @ViewChild('closeBtn') closeBtn: ElementRef;
  wrongFileWarning: Warning;
  noFileWarning: Warning;
  success: Success;
  @ViewChild('formControl') formControl: NgForm;
  validExtensions: string[] = ['jpg','png','jpeg','pdf','doc','odf','xlsx','xls'];

  constructor(private bugService: BugService, private userService: UserService) { }


  resetBugModel(){

    this.bugModel = this.getDefaultBugModel();
    this.severityFormControl = new FormControl(this.possibleSeverities.find(s => s === 'MEDIUM'));
    this.formData = new FormData();

  }

  getDefaultBugModel(){
    const defaultBugModel  = {
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
    return defaultBugModel;
  }

  ngOnInit() {

    this.resetBugModel();
    this.wrongFileWarning = {
      message: "Your file is not supported, so it wasn't added",
      recommendation: "Choose another file or edit the bug later if you want to change it",
      display: false
    };
    this.noFileWarning = {
      message: "You did not attach any file to the bug",
      recommendation: null,
      display: false
    };
    this.success = {
      message: "Bug successfully added",
      display: false
    };

    this.userService.getAllUsers().subscribe(
      (users) => {this.userList = users;}
    );

    this.formControl.valueChanges.subscribe(() => this.resetErrors());
  }

  resetErrors(){
    if( (JSON.stringify(this.bugModel)) != JSON.stringify(this.getDefaultBugModel()) ) {
      this.noFileWarning.display = false;
      this.success.display = false;
    }
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
    if(! this.success.display )
      this.validateInput = true;
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }


  submitAddData(){

    this.errorMessage = "";
    this.validateInput = false;


    let currentUsername = localStorage.getItem("currentUser");
    let currentUser = this.userList.find(user => user.username == currentUsername);
    if( currentUser == null){
      this.errorMessage = {id: "5001",type: "Current user could not be retrieved", details:[{detail:"Current user could not be retrieved",message:""}]};
      return;
    }
    this.bugModel.createdBy = currentUser;


    let assignedUser = this.userList.find(user => user.username == this.bugModel.assignedTo.username);
    if (assignedUser == null) {
      this.errorMessage = { id:"5002",type: "Can not find the assigned user.",details:[{detail:"Can not find the assigned user.",message:""}]};
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
                  this.wrongFileWarning.display = false;
                  this.resetBugModel(); // IMPORTANT !! DISPLAY INFOS AFTER RESET FORM
                  this.formControl.resetForm();
                  this.success.display = true;
                  this.bugList.push(response);

                },
                (error) => {
                  this.errorMessage = error['error'];
                }
              );
          }
          else {
            this.wrongFileWarning.display = false;
            this.resetBugModel();
            this.formControl.resetForm(); // IMPORTANT !! DISPLAY INFOS AFTER RESET FORM
            this.success.display = true;
            this.noFileWarning.display = true;
            this.bugList.push(response);
          }
        },
        (error) => {
          this.wrongFileWarning.display = false;
          this.errorMessage = error['error'];
        }
      );

  }




  fileChange(event){
    this.wrongFileWarning.display = false;
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
        this.wrongFileWarning.display = true;
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
}
