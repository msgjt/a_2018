import {Bug, BugService} from "../services/bug.service";
import {Router} from "@angular/router";
import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {Role} from "../../role-management/entities/role";
import {HttpHeaders} from "@angular/common/http";
import {User, UserService} from "../../user-management/services/user.service";


@Component({
  selector: 'app-edit-bug',
  templateUrl: './edit-bug.component.html',
  styleUrls: ['./edit-bug.component.css']
})
export class EditBugComponent implements OnInit {

  @Input() bug;
  errorOccurred: boolean;
  positiveResponse: boolean;
  errorMessage: string;

  constructor(private bugService: BugService,private userService: UserService, private router: Router) {

  }

  ngOnInit() {
    this.bug = {
      id: -1,
    title: '',
    description: '',
    status: '',
    severity: '',
    fixedVersion: '',
    targetDate: '',
    version: '',
    assignedTo: '',
    createdBy: ''
    };
  }




  submitEditForm() {

    this.userService.getAllUsers().subscribe( users => {
      this.bug.assignedTo = users.find(user => user.username === this.bug.assignedTo.username )
    });
    if( this.bug.assignedTo == null){
      // error
    }
    console.log(this.bug);
    this.bugService.updateBug(this.bug).subscribe( () => {});
  }


}
