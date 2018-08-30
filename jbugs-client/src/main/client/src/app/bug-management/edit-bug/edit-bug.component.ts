import {BugService} from "../services/bug.service";
import {Router} from "@angular/router";
import {Component, Input, OnInit} from '@angular/core';
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
  possibleSeverities: string[] = ['LOW','MEDIUM','HIGH'];

  constructor(private bugService: BugService,private userService: UserService, private router: Router) {

  }


  ngOnInit() {


  }

  updateBugSeverity(severity: string) {
    this.bug.severity = severity;
  }

  submitEditForm() {

    this.userService.getAllUsers().subscribe( users => {
      this.bug.assignedTo = users.find(user => user.username === this.bug.assignedTo.username )
    });
    if( this.bug.assignedTo == null){
      // error
    }
    this.bugService.updateBug(this.bug).subscribe( () => {});
  }


}
