import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {LSKEY, TOKENKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import { Popup } from  'ng2-opd-popup';
import {Role} from "../../role-management/entities/role";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  loggedIn = true;
  userList: User[];
  @ViewChild('popup') popup: Popup;
  pressedEdit: boolean = false;
  @Input('show-modal') showModal: boolean;
  @ViewChild('addUserPopup') addUserPopup: Popup;
  pressedAdd: boolean = false;
  userModel: User;
  activeUser: boolean;
  errorMessage: string;
  roles: Role[];
  rolesFormControl: FormControl;
  editRolesFormControl: FormControl;
  @ViewChild('popup') errorPopup: Popup;
  errorOccurred: boolean;
  positiveResponse: boolean;
  @ViewChild('infoDiv') infoDiv: ElementRef;
  showInfoDiv: boolean = false;

  constructor(private userService: UserService, private router: Router) {
    this.userService.getAllUsers().subscribe((user) => {
      this.userList = user;
    },(error)=>{
      if(error.status == 403){
        router.navigate(['/error']);
      }
      if(error.status == 401){
        router.navigate(['/norights']);
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
    },(error)=>{console.log('USER NOT ALLOWED TO GET THE ROLES')});  }

  ngOnInit() {
    this.editRolesFormControl = new FormControl();
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

  showRoleDropdown() {

  }

  showEditPopup() {
    this.pressedEdit = true;
  }

  disableUser(user: any) {
    this.userService.deactivateUser(user.id).subscribe(
      (response) => {
        console.log('response ' + JSON.stringify(response));
      },
      (error) => {
        this.errorMessage = error['error'];
        this.popup.show(this.popup.options);
      }
    );
    this.userList[user.id - 1].isActive = false;
    this.activeUser = false;
  }

  enableUser(user: any) {
    this.userService.activateUser(user.id).subscribe(
      (response) => {
        console.log('response ' + JSON.stringify(response));
      },
      (error) => {
        this.errorMessage = error['error'];
        this.popup.show(this.popup.options);
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
          console.log('response ' + JSON.stringify(response));
        },
        (error) => {
          this.errorMessage = error['error'];
          this.popup.show(this.popup.options);
        }
      );
  }

  passDataToModal(user: User) {

    this.userModel = user;
    console.log(this.userModel.roles);
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
          console.log('response ' + JSON.stringify(response));
          this.userService.getAllUsers().subscribe((user)=>this.userList=user);
          this.errorOccurred = false;
          this.positiveResponse = true;
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

}
