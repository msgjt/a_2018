import {Component, OnInit, ChangeDetectionStrategy} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";

@Component({
  selector: 'app-bugs-view',
  templateUrl: './bugs-view.component.html',
  styleUrls: ['./bugs-view.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class BugsViewComponent implements OnInit {

  bugList: Bug[];
  pagesFormControl : FormControl;

  //Pagination
  public filter: string = '';
  public config: PaginationInstance = {
    id: 'custom',
    itemsPerPage: 3,
    currentPage: 1
  };

  constructor(private bugService: BugService) {
    this.bugList = [];
    this.bugService.getAllBugs().subscribe((bug) => {
      this.bugList = bug;
    });
  }

  ngOnInit() {
    this.pagesFormControl = new FormControl(0);
  }

}
