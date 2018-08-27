import {Component, OnInit, ChangeDetectionStrategy} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";
import {Router} from "@angular/router";
import {ExcelService} from "../services/excel.service";

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

  constructor(private bugService: BugService, private router: Router,private excelService: ExcelService) {
    this.bugList = [];
    this.bugService.getAllBugs().subscribe((bug) => {
      this.bugList = bug;
    },
      (error)=>{
        if(error.status == 403){
          router.navigate(['/error']);
        }
        if(error.status == 401){
          router.navigate(['/norights']);
        }
      });
  }

  ngOnInit() {
    this.pagesFormControl = new FormControl(0);
  }

  exportToExcel() {
    this.excelService.exportAsExcelFile(this.bugList);
  }

}
