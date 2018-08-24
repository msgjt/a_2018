import {Component, OnInit, ViewChild} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {Popup} from "ng2-opd-popup";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";

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

  //Pagination

  public filter: string = '';
  public maxSize: number = 7;
  public directionLinks: boolean = true;
  public autoHide: boolean = false;
  public responsive: boolean = true;
  public config: PaginationInstance = {
    id: 'custom',
    itemsPerPage: 3,
    currentPage: 1
  };
  public labels: any = {
    previousLabel: 'Previous',
    nextLabel: 'Next',
    screenReaderPaginationLabel: 'Pagination',
    screenReaderPageLabel: 'page',
    screenReaderCurrentLabel: `You're on page`
  };

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

  updateTable() {
    if(this.currentPage == this.maxPage) {
      this.bugList = this.bugListAll.slice(this.bugsAmount * (this.currentPage - 1));
    } else {
      this.bugList = this.bugListAll.slice(this.bugsAmount * (this.currentPage - 1), this.bugsAmount);
    }
  }

}
