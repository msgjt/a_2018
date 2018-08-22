import {Component, OnInit, ViewChild} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {Popup} from "ng2-opd-popup";

@Component({
  selector: 'app-bugs-view',
  templateUrl: './bugs-view.component.html',
  styleUrls: ['./bugs-view.component.css']
})
export class BugsViewComponent implements OnInit {

  columnsToDisplay = ['title', 'description','version','fixedVersion','status', 'severity','targetDate', 'assignedTo', 'createdBy'];
  bugList: Bug[];
  message:string;
  @ViewChild('popup') popup: Popup;
  constructor(private bugService: BugService) {
    this.bugService.getAllBugs().subscribe((bug) => {
      this.bugList = bug;
    });
  }

  ngOnInit() {
  }

  viewDescription(description: string){
    this.message = description;

    this.popup.options = {
      header: "Bug description",
      color: "darkred", // red, blue....
      widthProsentage: 40, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      animation: "fadeInDown", // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };

    this.popup.show(this.popup.options);
  }


}
