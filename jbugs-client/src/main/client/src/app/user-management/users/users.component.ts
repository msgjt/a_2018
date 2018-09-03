import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {LSKEY, User, UserService} from "../services/user.service";
import {Router} from "@angular/router";
import {Role} from "../../role-management/entities/role";
import {FormControl, NgForm} from "@angular/forms";
import {UtilService} from "../../shared/util.service";
import {Success, Warning} from "../../communication/communication.component";

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
  //errorMessage: string;
  roles: Role[];
  rolesFormControl: FormControl;
  editRolesFormControl: FormControl;
  errorOccurred: boolean;
  positiveResponse: boolean;
  @ViewChild('infoDiv') infoDiv: ElementRef;
  @ViewChild('formControlAdd') addFormControl: NgForm;
  @ViewChild('formControl') editFormControl: NgForm;
  showInfoDiv: boolean = false;
  errorMessage: any;
  warningMessage: Warning;
  generalError: boolean;
  submitAddPerformed: boolean = false;
  submitEditPerformed: boolean = false;
  succcessMessage: Success;

  constructor(private userService: UserService, private router: Router,
              public utilService: UtilService) {
    this.refresh();
  }

  ngOnInit() {
    this.refresh();
    this.succcessMessage = {
      message: "Action completed successfully",
      display: false
    };


  }

  resetForm(){
    if( ! ( JSON.stringify(this.userModel) == JSON.stringify(this.getDefaultUserModel())) )
    {
      this.succcessMessage.display = false;
      this.errorMessage = null;

    }
  }

  isLoggedIn() {
    let result = localStorage.getItem(LSKEY) != null;
    if(result == false)
      this.router.navigate(['/login']);
    return result;
  }

  getDefaultUserModel() {
    const user = {
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
    return user;
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
    this.userModel = this.getDefaultUserModel();
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
    this.errorMessage = "";
    this.generalError = true;
    this.userService.deactivateUser(user.id).subscribe(
      (response) => {
        this.errorOccurred = false;
        this.positiveResponse = true;
        this.userList[user.id - 1].isActive = false;
        this.activeUser = false;
        this.errorMessage = "";
      },
      (error) => {
        this.errorOccurred = true;
        this.positiveResponse = false;
        this.errorMessage = error['error'];
      }
    );
  }

  enableUser(user: any) {
    this.errorMessage = "";
    this.generalError = true;
    this.userService.activateUser(user.id).subscribe(
      (response) => {
        this.errorMessage = "";
      },
      (error) => {
        this.errorMessage = error['error'];
      }
    );
    this.userList[user.id - 1].isActive = true;
    this.activeUser = true;
  }

  submitEditForm() {
    this.editFormControl.valueChanges.subscribe( () => this.resetForm());
    this.submitEditPerformed = true;
    this.generalError = false;
    this.errorMessage = "";
    let roles = this.editRolesFormControl.value;
    if(roles.length == 0){
      roles = this.roles.find(r => r.type == 'DEV');
    }
    this.userService.updateUser(this.userModel.id, this.userModel.firstName, this.userModel.lastName, this.userModel.email, this.userModel.phoneNumber, roles)
      .subscribe(
        (response) => {
          this.userService.getAllUsers().subscribe((user)=>this.userList=user);
          this.errorOccurred = false;
          this.succcessMessage.display = true;
          this.pressedEdit = false;
          this.succcessMessage.display = true;
        },
        (error) => {
          this.errorMessage = error['error'];
          this.errorOccurred = true;
          this.succcessMessage.display = false;
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
    if( this.userModel.id ) {
      this.userModel = this.getDefaultUserModel();
      this.addFormControl.reset();

    }
    this.pressedAdd = true;
    this.userService.getAllRoles().subscribe(
      (roles) => {this.roles = roles;}
    );
  }

  submitAddForm(){
    this.addFormControl.valueChanges.subscribe(() => this.resetForm());
    this.submitAddPerformed = true;
    this.generalError = false;
    this.errorMessage = "";
    this.succcessMessage.display = false;
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
          this.userModel.roles = [];
          this.submitAddPerformed = false;
          this.addFormControl.resetForm();
          this.succcessMessage.display = true;
        },
        (error) => {
          this.errorMessage = error['error'];
          this.succcessMessage.display = false;
          this.errorOccurred = true;
        }
      );
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
