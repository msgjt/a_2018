import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {Bug, BugService} from "../services/bug.service";
import {FormControl} from "@angular/forms";
import {PaginationInstance} from "ngx-pagination";
import {Router} from "@angular/router";
import {ExcelService} from "../services/excel.service";
import {FilterPipe} from "../../filter.pipe";
import * as jsPDF from 'jspdf';
import {ActiveToast, ToastrService} from "ngx-toastr";

//for commit
@Component({
  selector: 'app-bugs-view',
  templateUrl: './bugs-view.component.html',
  styleUrls: ['./bugs-view.component.css'],
  changeDetection: ChangeDetectionStrategy.Default

})
export class BugsViewComponent implements OnInit {

  selectedBug;
  bugList: Bug[];
  pagesFormControl : FormControl;
  filtersShow = [
    { show1: false },
    { show2: false },
    { show3: false },
    { show4: false },
    { show5: false },
    { show6: false },
    { show7: false },
    { show8: false },
    { show9: false }
  ];
  filter1 = '';
  filter2 = '';
  filter3 = '';
  filter4 = '';
  filter5 = '';
  filter6 = '';
  filter7 = '';
  filter8 = '';
  filter9 = '';
  bugListAux = [];
  detailedBug: Bug;
  ascendingSort = { description: true, fixedVersion: true, severity: true, status: true, targetDate: true, title: true, version: true, assignedTo: true, createdBy: true };
  bugModel: Bug;
  showInfoDiv: boolean = false;
  formData: FormData;

  //Pagination
  public filter = { };
  public config: PaginationInstance = {
    id: 'custom',
    itemsPerPage: 25,
    currentPage: 1
  };

  constructor(private toastr: ToastrService, private bugService: BugService, private router: Router,private excelService: ExcelService) {
    this.bugList = [];
    this.bugService.getAllBugs().subscribe((bug) => {
        this.bugList = bug;
        this.bugListAux = bug;
        this.selectedBug = bug[0];
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

  ngOnInit() {
    this.pagesFormControl = new FormControl(0);
  }

  exportToExcel() {
    let copyOfBugList = JSON.parse(JSON.stringify(this.bugList));
    this.excelService.exportAsExcelFile(copyOfBugList);
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
      case 'description' : {
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

  exportToPdf(description: string, fixedVersion :string, severity: string, status: string, targetDate: string,
              title :string, version :string){
    let doc = new jsPDF('p' ,'pt' ,'a4');
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

  passDataToEditModal(bug: Bug) {
    this.selectedBug = bug;
  }

  isBUG_EXPORT_PDF(): boolean {
    return localStorage.getItem('BUG_EXPORT_PDF') != null;}


  submitAddData(){
    this.bugService.sendFile(this.formData)
      .subscribe(
        (response) => {
          console.log(response);
          this.bugService.createBug(this.bugModel,this.formData)

        },
        (error) =>{
          console.log(error);
          //TODO show error to user
        }
      );
    // this.bugService.createBug(this.bugModel,this.formData);
      /*.subscribe(
        (response) => {
         /!* this.userService.getAllUsers().subscribe((user)=>this.userList=user);
          this.errorOccurred = false;
          this.positiveResponse = true;
          this.clearUserModelFields();*!/
         console.log(response);
        },
        (error) => {
      /!*    this.errorMessage = error['error'];
          this.positiveResponse = false;
          this.errorOccurred = true;*!/
      console.log(error);
        }
      );*/
  }

  showInfo() {
    this.showInfoDiv = true;
  }

  hideInfo() {
    this.showInfoDiv = false;
  }

  showNotif() {
    this.toastr.info('-Nelson Mondialu\'', 'Daca-mi face figuri, ii arat si io figuri.').onShown.subscribe(() => {
      let snd = new Audio("../../assets/notificationsound.mp3");
      snd.play();
    });
  }

  fileChange(event){
    let files = event.target.files;
    if (files.length > 0) {
      this.formData = new FormData();
      this.formData.append('file', files[0]);
    }
  }
}
