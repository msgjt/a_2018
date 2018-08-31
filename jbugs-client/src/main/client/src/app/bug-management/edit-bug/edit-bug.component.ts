import {Bug, BugService} from "../services/bug.service";
import {Router} from "@angular/router";
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {UserService} from "../../user-management/services/user.service";
import {Form, FormControl} from "@angular/forms";


@Component({
  selector: 'app-edit-bug',
  templateUrl: './edit-bug.component.html',
  styleUrls: ['./edit-bug.component.css']
})
export class EditBugComponent implements OnInit {

  @Input() bug;
  @Input() severityFormControl: FormControl;
  @Input() statusFormControl: FormControl;
  errorOccurred: boolean;
  positiveResponse: boolean;
  errorMessage: string;
  possibleSeverities: string[] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  oldStatus: string = null;
  formData: FormData;


  constructor(private bugService: BugService, private userService: UserService, private router: Router) {
    this.formData = new FormData();

  }


  ngOnInit() {


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

  submitEditForm() {
    this.userService.getAllUsers().subscribe(users => {
      this.bug.assignedTo = users.find(user => user.username === this.bug.assignedTo.username)
    });
    if (this.bug.assignedTo == null) {
      // error
    }
    this.bugService.updateBug(this.bug).subscribe(() => {
      this.severityFormControl = new FormControl(this.bug.severity);
      this.statusFormControl = new FormControl(this.bug.status);
      this.oldStatus = null;
      if (this.formData.has('file')) {
        this.formData.append('bugId', this.bug.id.toString());
        this.bugService.sendFile(this.formData)
          .subscribe(
            () => {
              console.log('success');
            },
            (error) => {
              console.log(error);
            }
          );
      }
    },(error)=>{
      if(error.status == 403){
        localStorage.clear();
        this.router.navigate(['/login']);
      }
      if(error.status == 401){
        this.router.navigate(['/norights']);
      }
    });
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
    let status;
    if (this.oldStatus != null) {
      status = this.oldStatus;
    } else {
      status = bug.status;
    }

    return allStates.find(s => s.key == status).values;
  }

  deleteAttachment(id) {
    this.bugService.deleteAttachment(id)
      .subscribe(
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
  }

  fileChange(event) {
    let files = event.target.files;
    if (files.length > 0) {
      this.formData = new FormData();
      this.formData.append('file', files[0]);
      this.bug.attachment = files[0].name;
    }
  }


}
