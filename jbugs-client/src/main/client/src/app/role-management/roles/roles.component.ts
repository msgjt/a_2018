import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Role} from "../entities/role";
import {RoleService} from "../services/role.service";
import {LSKEY, TOKENKEY} from "../../user-management/services/user.service";
import {Permission} from "../entities/permission";
import {FormControl} from '@angular/forms';
import {UtilService} from "../../shared/util.service";

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {

  loggedIn = true;
  roleList: Role[];
  formControlList: FormControl[];
  permissionsList: Permission[];
  columnsToDisplay = ['type', 'permissionTypes'];
  isDone = false;

  constructor(private roleService: RoleService, private router: Router,
              public utilService: UtilService) {

  }

  getFormControlForRole(role: Role): FormControl {
    let pos = this.roleList.findIndex(r => r.id == role.id);
    let formControl = pos === -1 ? null : this.formControlList[pos];
    return formControl;
  }

  ngOnInit() {
    this.formControlList = [];
    this.roleService.getAllRoles().subscribe((roles) => {
      this.roleList = roles;
      this.roleService.getAllPermissions().subscribe((permissions) => {
        this.permissionsList = permissions;
        this.roleList.forEach((role) => {
          let valid = [];
          this.permissionsList.forEach(perm => {
            if (role.permissions.findIndex(p => p.type === perm.type) != -1) {
              valid.push(perm);
            }
          });
          this.formControlList.push(new FormControl(valid));
        });
        this.isDone = true;
      });
    },(error)=>{
      if(error.status == 403){
        this.router.navigate(['/login']);
      }
      if(error.status == 401){
        this.router.navigate(['/norights']);
      }
    });
  }

  refresh(){

  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      this.router.navigate(['./login']);
      localStorage.removeItem(LSKEY);
      localStorage.removeItem(TOKENKEY);
      this.loggedIn = false;
    }
  }

  updateRole(role: Role) {
    role.permissions = this.getFormControlForRole(role).value;
    this.roleService.updateRole(role).subscribe(() => {
    });
  }
}
