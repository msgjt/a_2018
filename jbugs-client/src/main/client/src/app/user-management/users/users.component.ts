import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {LSKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {Role} from "../../role-management/entities/role";
import {FormControl} from "@angular/forms";
import {UtilService} from "../../shared/util.service";

@Component({
  selector: 'app-profile',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  loggedIn = true;
  userList: User[];
  pressedEdit: boolean = false;
  @Input('show-modal') showModal: boolean;
  pressedAdd: boolean = false;
  userModel: User;
  activeUser: boolean;
  errorMessage: string;
  roles: Role[];
  rolesFormControl: FormControl;
  editRolesFormControl: FormControl;
  errorOccurred: boolean;
  positiveResponse: boolean;
  @ViewChild('infoDiv') infoDiv: ElementRef;
  showInfoDiv: boolean = false;

  constructor(private userService: UserService, private router: Router,
              public utilService: UtilService) {
    this.refresh();
  }

  ngOnInit() {
    this.refresh();
  }

  refresh(){
    this.editRolesFormControl = new FormControl();
    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    },(error)=>{
      if(error.status == 403){
        localStorage.clear();
        this.router.navigate(['/login']);
      }
      if(error.status == 401){
        this.router.navigate(['/norights']);
      }
    });
    this.userModel = {
      id: 0,
      firstName: '',
      lastName: '',
      isActive: false,
      phoneNumber: '',
      email: '',
      roles: [],
      username: '',
      password: ''
    };
    this.rolesFormControl = new FormControl();
    this.errorOccurred = false;
    this.positiveResponse = false;
    this.userService.getAllRoles().subscribe((roles) => {
      this.roles = roles;
    },(error)=>{console.log('USER NOT ALLOWED TO GET THE ROLES')});
  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      localStorage.clear();
      this.userService.logout(localStorage.getItem(LSKEY)).subscribe(response=>console.log(response.toString()));
      this.loggedIn = false;
      this.router.navigate(['./login']);
    }
  }

  setRoles(roles: Role[]) {
    this.userModel.roles = roles;
  }

  disableUser(user: any) {
    this.userService.deactivateUser(user.id).subscribe(
      (response) => {
        this.errorOccurred = false;
        this.positiveResponse = true;
        this.userList[user.id - 1].isActive = false;
        this.activeUser = false;
      },
      (error) => {
        this.errorOccurred = true;
        this.positiveResponse = false;
        this.errorMessage = error['error'];
        console.log(this.errorMessage);
      }
    );
  }

  enableUser(user: any) {
    this.userService.activateUser(user.id).subscribe(
      (response) => {
      },
      (error) => {
        this.errorMessage = error['error'];
        console.log(this.errorMessage);
      }
    );
    this.userList[user.id - 1].isActive = true;
    this.activeUser = true;
  }

  submitEditForm() {
    this.userService.updateUser(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber, this.editRolesFormControl.value)
      .subscribe(
        (response) => {
          this.userService.getAllUsers().subscribe((user)=>this.userList=user);
          this.errorOccurred = false;
          this.positiveResponse = true;
          this.pressedEdit = false;
        },
        (error) => {
          this.errorMessage = error['error'];
          this.errorOccurred = true;
          this.positiveResponse = false;
        }
      );
  }

  passDataToModal(user: User) {
    this.pressedEdit = true;
    this.userModel = user;
    let selectedRoles = [];
    this.roles.forEach( role => {
      if( this.userModel.roles.findIndex(r => r.id === role.id) != -1){
        selectedRoles.push(role);
      }
    });
    this.editRolesFormControl = new FormControl(selectedRoles);
  }

  showAddPopup(){
    this.pressedAdd = true;
    this.userService.getAllRoles().subscribe(
      (roles) => {this.roles = roles;}
    );
  }

  submitAddForm(){
    this.roles.forEach(role =>
    {
      if (role.selected){
        role.selected = false;
        this.userModel.roles.push(role);
      }
    });
    this.userService.addUser(this.userModel.firstName,this.userModel.lastName,this.userModel.email,this.userModel.phoneNumber,this.userModel.username, this.userModel.password, this.userModel.roles)
      .subscribe(
        (response) => {
          this.userService.getAllUsers().subscribe((user)=>this.userList=user);
          this.errorOccurred = false;
          this.positiveResponse = true;
          this.clearUserModelFields();
        },
        (error) => {
          this.errorMessage = error['error'];
          this.positiveResponse = false;
          this.errorOccurred = true;
        }
      );
    this.userModel.roles = [];
  }

  showInfo() {
    this.showInfoDiv = true;
  }

  hideInfo() {
    this.showInfoDiv = false;
  }

  clearUserModelFields(){
    this.userModel = {
      id: 0,
      firstName: '',
      lastName: '',
      isActive: false,
      phoneNumber: '',
      email: '',
      roles: [],
      username: '',
      password: ''
    };
  }
}
