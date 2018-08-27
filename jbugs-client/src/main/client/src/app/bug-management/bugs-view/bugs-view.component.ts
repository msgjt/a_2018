import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";
import {Router} from "@angular/router";
import {ExcelService} from "../services/excel.service";
import {FilterPipe} from "../../filter.pipe";
import * as jsPDF from 'jspdf'
import {Status} from "tslint/lib/runner";

//for commit
@Component({
  selector: 'app-bugs-view',
  templateUrl: './bugs-view.component.html',
  styleUrls: ['./bugs-view.component.css'],
  changeDetection: ChangeDetectionStrategy.Default
})
export class BugsViewComponent implements OnInit {

  bugList: Bug[];
  pagesFormControl : FormControl;
  filtersShow = [
    { show1: false },
    { show2: false },
    { show3: false },
    { show4: false },
    { show5: false },
    { show6: false },
    { show7: false }
  ];
  filter1 = '';
  filter2 = '';
  filter3 = '';
  filter4 = '';
  filter5 = '';
  filter6 = '';
  filter7 = '';
  bugListAux = [];
  detailedBug: Bug;
  ascendingSort: boolean = false;

  //Pagination
  public filter = { };
  public config: PaginationInstance = {
    id: 'custom',
    itemsPerPage: 25,
    currentPage: 1
  };

  constructor(private bugService: BugService, private router: Router,private excelService: ExcelService) {
    this.bugList = [];
    this.bugService.getAllBugs().subscribe((bug) => {
      this.bugList = bug;
      this.bugListAux = bug;
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

  addFilters(filterBy: string) {
    switch (filterBy) {
      case 'description' : {
        this.filter[filterBy] = this.filter1;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'fixedVersion' : {
        this.filter[filterBy] = this.filter2;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'severity' : {
        this.filter[filterBy] = this.filter3;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'status' : {
        this.filter[filterBy] = this.filter4;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'targetDate' : {
        this.filter[filterBy] = this.filter5;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'title' : {
        this.filter[filterBy] = this.filter6;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'version' : {
        this.filter[filterBy] = this.filter7;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
    }
    this.bugList = new FilterPipe().transform(this.bugListAux, this.filter);
  }

  passUserToDetailedModal(bug: Bug) {
    this.detailedBug = bug;
  }

  doSort(sortBy: string) {
    this.ascendingSort = !this.ascendingSort;
    // let toSortBugList = this.bugList.slice((this.config.currentPage-1)* 25 + 1, (this.bugList.length>(this.config.currentPage-1)* 25 + 26) ? (this.bugList.length-))
    // switch (sortBy) {
    //   case 'description' : {
    //     this.bugList.sort((one, two) => (one > two ? -1 : 1));
    //     break;
    //   }
    //   case 'fixedVersion' : {
    //     this.bugList.sort((one, two) => (one > two ? -1 : 1));
    //     break;
    //   }
    //   case 'severity' : {
    //     if(this.ascendingSort) {
    //       this.bugList.sort((one, two) => (one.severity < two.severity ? -1 : 1));
    //     } else {
    //       this.bugList.sort((one, two) => (one.severity > two.severity ? -1 : 1));
    //     }
    //     break;
    //   }
    //   case 'status' : {
    //     this.bugList.sort((one, two) => (one > two ? -1 : 1));
    //     break;
    //   }
    //   case 'targetDate' : {
    //     this.bugList.sort((one, two) => (one > two ? -1 : 1));
    //     break;
    //   }
    //   case 'title' : {
    //     this.bugList.sort((one, two) => (one > two ? -1 : 1));
    //     break;
    //   }
    //   case 'version' : {
    //     this.bugList.sort((one, two) => (one > two ? -1 : 1));
    //     break;
    //   }
    // }
  }

  exportToPdf(description: string, fixedVersion :string, severity: string, status: string, targetDate: string,
              title :string, version :string){
    var doc = new jsPDF('p' ,'pt' ,'a4',{pagesplit: true, margin: {top: 10, right: 10, bottom: 10, left: 10, useFor: 'content'}});
    doc.text('MSG ROMANIA: BUG '+title ,10, 10);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 20);
    doc.text('DESCRIPTION: '+description, 10, 30);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 40);
    doc.text('FIXED VERSION: '+fixedVersion, 10, 50);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 60);
    doc.text('SEVERITY: '+severity, 10, 70);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 80);
    doc.text('STATUS: '+status, 10, 90);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 100);
    doc.text('TARGET DATE: '+targetDate, 10, 110);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 120);
    doc.text('TITLE: '+title, 10, 130);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 140);
    doc.text('VERSION: '+version, 10, 150);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 160);
    doc.text('TEAM A ',10, 170);
    doc.save(title+'_'+version)

  }
}
