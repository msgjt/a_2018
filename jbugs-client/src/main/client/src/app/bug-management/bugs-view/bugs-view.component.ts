import {ChangeDetectionStrategy, Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";
import {Router} from "@angular/router";
import {ExcelService} from "../services/excel.service";
import {FilterPipe} from "../../filter.pipe";
import {ToastrService} from "ngx-toastr";
import {User, UserService} from "../../user-management/services/user.service";
import {UtilService} from "../../shared/util.service";
import {Error} from "../../communication/communication.component";
import {EditBugComponent} from "../edit-bug/edit-bug.component";

@Component({
  selector: 'app-bugs-view',
  templateUrl: './bugs-view.component.html',
  styleUrls: ['./bugs-view.component.css'],
  changeDetection: ChangeDetectionStrategy.Default

})
export class BugsViewComponent implements OnInit {

  selectedBug: Bug;
  bugList: Bug[];
  pagesFormControl : FormControl;
  filter1 = '';
  filter2 = '';
  filter3 = '';
  filter4 = '';
  filter5 = '';
  filter6 = '';
  filter7 = '';
  filter8 = '';
  filter9 = '';
  filter10: '';
  bugListAux = [];
  detailedBug: Bug;
  ascendingSort = { id: true, description: true, fixedVersion: true, severity: true, status: true, targetDate: true, title: true, version: true, assignedTo: true, createdBy: true };
  askedForSort = { id: false, description: false, fixedVersion: false, severity: false, status: false, targetDate: false, title: false, version: false, assignedTo: false, createdBy: false };
  bugModel: Bug;
  formData: FormData;
  errorMessage: string;
  editErrorMessage: Error = null;

  //Pagination
  public filter = { };
  public config: PaginationInstance = {
    id: 'custom',
    itemsPerPage: 25,
    currentPage: 1
  };

  copyBugModel(bug: Bug): Bug {
    return JSON.parse(JSON.stringify(bug));
  }

  constructor(private toastr: ToastrService, private bugService: BugService, private router: Router,
              private excelService: ExcelService, private userService: UserService,
              public utilService: UtilService) {
    this.bugList = [];
    this.bugService.getAllBugs().subscribe((bug) => {

        this.bugList = bug;
        this.bugListAux = bug;
      },
      (error) => {
        if (error.status == 403) {
          router.navigate(['/error']);
        }
        if (error.status == 401) {
          router.navigate(['/norights']);
        }
      });
    this.bugModel = {
      id: 0,
      title: '',
      description: '',
      status: '',
      severity: '',
      fixedVersion: '',
      targetDate: '',
      version: '',
      attachment: '',
      assignedTo: {
        id: 0,
        firstName: '',
        lastName: '',
        isActive: false,
        phoneNumber: '',
        email: '',
        roles: [],
        username: '',
        password: ''
      },
      createdBy: {
        id: 0,
        firstName: '',
        lastName: '',
        isActive: false,
        phoneNumber: '',
        email: '',
        roles: [],
        username: '',
        password: ''
      }
    }
  }

  getErrorMessage(selectedBug){
    return null;
  }

  ngOnInit() {
    this.pagesFormControl = new FormControl(0);
    this.formData = new FormData();

  }

  exportToExcel() {
    let copyOfBugList = JSON.parse(JSON.stringify(this.bugList));
    this.excelService.exportAsExcelFile(copyOfBugList);
  }

  addFilters(filterBy: string) {
    switch (filterBy) {
      case 'id' : {
        this.filter[filterBy] = this.filter10;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
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
      case 'assignedTo' : {
        this.filter[filterBy] = this.filter8;
        if(this.filter[filterBy] == '') {
          delete this.filter[filterBy];
        }
        break;
      }
      case 'createdBy' : {
        this.filter[filterBy] = this.filter9;
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
    let startIndex = (this.config.currentPage-1)*25;
    let endIndex = ((this.config.currentPage*this.config.itemsPerPage) - ((this.bugList.length > this.config.currentPage*this.config.itemsPerPage) ? 0 : (this.config.currentPage*this.config.itemsPerPage-this.bugList.length)));
    let bugListCopy = this.bugList;
    let toSortBugList = bugListCopy.slice(startIndex, endIndex);
    switch (sortBy) {
      case 'id' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.id - bug2.id;
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.id - bug1.id;
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'description' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.description.toLowerCase().localeCompare(bug2.description.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.description.toLowerCase().localeCompare(bug1.description.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'fixedVersion' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.fixedVersion.toLowerCase().localeCompare(bug2.fixedVersion.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.fixedVersion.toLowerCase().localeCompare(bug1.fixedVersion.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'severity' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.severity.toLowerCase().localeCompare(bug2.severity.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.severity.toLowerCase().localeCompare(bug1.severity.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'status' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.status.toLowerCase().localeCompare(bug2.status.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.status.toLowerCase().localeCompare(bug1.status.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'targetDate' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.targetDate.toLowerCase().localeCompare(bug2.targetDate.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.targetDate.toLowerCase().localeCompare(bug1.targetDate.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'title' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.title.toLowerCase().localeCompare(bug2.title.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.title.toLowerCase().localeCompare(bug1.title.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'version' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.version.toLowerCase().localeCompare(bug2.version.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.version.toLowerCase().localeCompare(bug1.version.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'assignedTo' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.assignedTo.username.toLowerCase().localeCompare(bug2.assignedTo.username.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.assignedTo.username.toLowerCase().localeCompare(bug1.assignedTo.username.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
      case 'createdBy' : {
        this.askedForSort[sortBy] = true;
        if(this.ascendingSort[sortBy]) {
          toSortBugList.sort(function (bug1, bug2) {
            return bug1.createdBy.username.toLowerCase().localeCompare(bug2.createdBy.username.toLowerCase());
          });
        } else {
          toSortBugList.sort(function (bug1, bug2) {
            return bug2.createdBy.username.toLowerCase().localeCompare(bug1.createdBy.username.toLowerCase());
          });
        }
        let i = startIndex;
        toSortBugList.forEach((element) => {
          this.bugList[i++] = element;
        });
        this.ascendingSort[sortBy] = !this.ascendingSort[sortBy];
        break;
      }
    }
  }

  passDataToEditModal(bug: Bug) {
    this.selectedBug = this.copyBugModel(bug);
  }

  getSeverityFormControl(bug: Bug){
    let possibleSeverities = ['LOW','MEDIUM','HIGH'];
    let s = possibleSeverities.find(s => s == bug.severity);
    return new FormControl(s);
  }

  getStatusFormControl(bug: Bug){
    let allStates = [
      {key: 'Open', values: ['Open','InProgress','Rejected']},
      {key: 'InProgress', values: ['InProgress','Rejected','Fixed','InfoNeeded']},
      {key: 'Rejected', values: ['Rejected','Closed']},
      {key: 'Fixed', values: ['Fixed','Closed']},
      {key: 'InfoNeeded', values: ['InfoNeeded','InProgress']},
      {key: 'Closed', values: []}
    ];

    let possibleStates = allStates.find(s => s.values.findIndex( v => v == bug.status) != -1).values;

    return new FormControl(possibleStates.find(s => bug.status == s));
  }
}
