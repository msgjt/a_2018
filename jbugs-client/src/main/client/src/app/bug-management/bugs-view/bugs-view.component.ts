import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";
import {Router} from "@angular/router";
import {FilterPipe} from "../../filter.pipe";

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

  //Pagination
  public filter = { };
  public config: PaginationInstance = {
    id: 'custom',
    itemsPerPage: 25,
    currentPage: 1
  };

  constructor(private bugService: BugService, private router: Router) {
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
}
