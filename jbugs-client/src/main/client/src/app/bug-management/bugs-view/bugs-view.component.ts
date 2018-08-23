import {Component, OnInit, ViewChild} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {Popup} from "ng2-opd-popup";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-bugs-view',
  templateUrl: './bugs-view.component.html',
  styleUrls: ['./bugs-view.component.css']
})
export class BugsViewComponent implements OnInit {

  bugList: Bug[];
  bugListAll: Bug[];
  pagesFormControl : FormControl;
  bugsAmount: number = 3;
  currentPage: number = 1;
  maxPage: number;

  @ViewChild('popup') popup: Popup;
  constructor(private bugService: BugService) {
    this.bugList = [];
    this.bugService.getAllBugs().subscribe((bug) => {
      this.bugListAll = bug;
      if(this.bugListAll.length % this.bugsAmount == 0) {
        this.maxPage = this.bugListAll.length / this.bugsAmount;
      } else {
        this.maxPage = Math.floor(this.bugListAll.length / this.bugsAmount) + 1;
      }
      this.updateTable();
    });
  }

  ngOnInit() {
    this.pagesFormControl = new FormControl(0);
  }

  previousPage() {
    if(this.currentPage > 1) {
      this.currentPage = this.currentPage - 1;
    }
    this.updateTable();
  }

  nextPage() {
    if(this.currentPage < this.maxPage) {
      this.currentPage = this.currentPage + 1;
    }
    this.updateTable();
  }

  updateTable() {
    if(this.currentPage == this.maxPage) {
      this.bugList = this.bugListAll.slice(this.bugsAmount * (this.currentPage - 1));
    } else {
      this.bugList = this.bugListAll.slice(this.bugsAmount * (this.currentPage - 1), this.bugsAmount);
    }
  }
}
