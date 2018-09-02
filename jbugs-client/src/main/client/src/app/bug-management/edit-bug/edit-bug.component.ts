import {Bug, BugService} from "../services/bug.service";
import {Router} from "@angular/router";
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User, UserService} from "../../user-management/services/user.service";
import {Form, FormControl} from "@angular/forms";
import {Error, Success, Warning} from "../../communication/communication.component";


@Component({
  selector: 'app-edit-bug',
  templateUrl: './edit-bug.component.html',
  styleUrls: ['./edit-bug.component.css']
})
export class EditBugComponent implements OnInit {

  @Input() bug;
  @Input() severityFormControl: FormControl;
  @Input() statusFormControl: FormControl;
  errorMessage: Error;
  possibleSeverities: string[] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  oldStatus: string = null;
  formData: FormData;
  userList: User[];
  haveToDelete: boolean;
  toValidate: boolean = false;
  validExtensions: string[] = ['jpg','png','jpeg','pdf','doc','odf','xlsx','xls'];
  warningMessage: any;
  successMessage: Success;


  constructor(private bugService: BugService, private userService: UserService, private router: Router) {
    this.formData = new FormData();
    this.userService.getAllUsers().subscribe(users => {
      this.userList = users;
    });
  }


  ngOnInit() {
    this.isBUG_CLOSE_ON_SERVER();
    this.warningMessage = {
      message: "Your file is not supported, so it wasn't added",
      recommendation: "Choose another file or edit the bug later if you want to change it",
      display: false
    };
    this.successMessage = {
      message: "Bug updated successfully",
      display: false
    }
  }

  updateBugSeverity(severity: string) {
    this.bug.severity = severity;
  }

  updateBugStatus(status: string) {
    if (this.oldStatus == null) {
      this.oldStatus = this.bug.status;
    }
    this.bug.status = status;
  }

  validate(){
    this.toValidate = true;
  }

  submitEditForm() {
    this.toValidate = true;
    this.successMessage.display = false;
    console.log(this.bug.assignedTo.username);
    let userList: User[];


    let assignedUser: User = this.userList.find(user => user.username == this.bug.assignedTo.username);
    console.log("found")

    if (assignedUser == null) {
      this.errorMessage = {
        id: 5000,
        type: "BUG_VALIDATION_EXCEPTION",
        details: [{detail: "BUG_ASSIGNED_TO_NOT_FOUND", message: ""}]
      };
      return;
    }
    else {
      this.errorMessage = null;
      this.bug.assignedTo = assignedUser;
      this.bugService.updateBug(this.bug).subscribe(() => {
          this.severityFormControl = new FormControl(this.bug.severity);
          this.statusFormControl = new FormControl(this.bug.status);
          this.oldStatus = null;
          if (this.formData.has('file')) {
            this.formData.append('bugId', this.bug.id.toString());
            this.bugService.sendFile(this.formData)
              .subscribe(
                () => {},
                (error) => {
                  this.errorMessage = error['error'];
                }
              );
          }
          if (this.haveToDelete == true) {
            this.deleteAttachment(this.bug.id);
          }
        },
        (error) => {
          if (error.status == 403) {
            localStorage.clear();
            this.router.navigate(['/login']);
          }
          if (error.status == 401) {
            this.router.navigate(['/norights']);
          }
          this.errorMessage = error['error'];
        });
    }
  }

  getPossibleStates(bug: Bug) {
    let allStates = [
      {key: 'Open', values: ['Open', 'InProgress', 'Rejected']},
      {key: 'InProgress', values: ['InProgress', 'Rejected', 'Fixed', 'InfoNeeded']},
      {key: 'Rejected', values: ['Rejected', 'Closed']},
      {key: 'Fixed', values: ['Fixed', 'Closed']},
      {key: 'InfoNeeded', values: ['InfoNeeded', 'InProgress']},
      {key: 'Closed', values: ['Closed']}
    ];

    let allStatesWithoutBugCloseRights = [
      {key: 'Open', values: ['Open', 'InProgress', 'Rejected']},
      {key: 'InProgress', values: ['InProgress', 'Rejected', 'Fixed', 'InfoNeeded']},
      {key: 'Rejected', values: ['Rejected']},
      {key: 'Fixed', values: ['Fixed']},
      {key: 'InfoNeeded', values: ['InfoNeeded', 'InProgress']},
      {key: 'Closed', values: ['Closed']}
    ];

    let status;
    if (this.oldStatus != null) {
      status = this.oldStatus;
    } else {
      status = bug.status;
    }

    if(localStorage.getItem("BUG_CLOSE")=="1"){
      return allStates.find(s => s.key == status).values;
    }else {
      return allStatesWithoutBugCloseRights.find(s => s.key == status).values;
    }
  }

  deleteTriggered(){
    this.bug.attachment = null;
    this.haveToDelete = true;
    this.formData = new FormData();
  }

  deleteAttachment(id) {
    this.bugService.deleteAttachment(id)
      .subscribe(
        () => {
          this.bug.attachment = null;
        },
        (error) => {
          this.errorMessage = error['error'];
        }
      );
  }

  fileChange(event) {
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
        this.bug.attachment = files[0].name;
        this.haveToDelete = false;
      }
      else{
        this.warningMessage.display = true;
      }
    }
  }

  isBUG_CLOSE_ON_SERVER(){
    this.bugService.is_BUG_CLOSE_ON_SERVER().subscribe(response => {
      if (response == true) {
        if(localStorage.getItem("BUG_CLOSE")=="1"){
        }else {
          localStorage.setItem("BUG_CLOSE","1");
        }
      } else {
        localStorage.removeItem("BUG_CLOSE");
      }
    })
  }
}
