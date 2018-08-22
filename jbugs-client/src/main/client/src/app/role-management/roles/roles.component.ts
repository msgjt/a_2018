import {Component, OnInit, ViewChild} from '@angular/core';
import {Popup} from "ng2-opd-popup";
import {Router} from "@angular/router";
import {Role} from "../entities/role";
import {RoleService} from "../services/role.service";
import {LSKEY, TOKENKEY} from "../../user-management/services/user.service";
import {Permission} from "../entities/permission";

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {

  loggedIn = true;
  message = '';
  selectedRole: Role;
  roleList: Role[];
  permissionsList: Permission[];
  @ViewChild('popup') popup: Popup;
  @ViewChild('permissionPopup') permissionPopup: Popup;
  columnsToDisplay = ['type','permissionTypes','permissionDescriptions'];
  pressedEdit: boolean = false;

  constructor(private roleService: RoleService, private router: Router) {

  }

  ngOnInit() {
    this.roleService.getAllRoles().subscribe((roles) => {
      this.roleList = roles;
    });
    this.roleService.getAllPermissions().subscribe( (permissions) => {
      this.permissionsList = permissions;
    });
    this.selectedRole = new Role();
  }

  logout() {
    if (localStorage.getItem(LSKEY)) {
      this.router.navigate(['./login']);
      localStorage.removeItem(LSKEY);
      localStorage.removeItem(TOKENKEY);
      this.loggedIn = false;
    }
  }


  contains(permission: Permission): boolean {
    if( ! this.selectedRole.permissions )
      return false;
    let result = this.selectedRole.permissions.findIndex(p => p.type == permission.type) != -1;

    return result;
  }

  removePermission(permission: Permission): void {
    this.selectedRole.permissions = this.selectedRole.permissions.filter(x => x.type !== permission.type);
    this.roleService.updateRole(this.selectedRole).subscribe(() => {});
  }


  addPermission(permission: Permission): void {
    this.selectedRole.permissions.push(permission);
    this.selectedRole.permissions = this.selectedRole.permissions.slice();
    this.roleService.updateRole(this.selectedRole).subscribe(() => {});
  }

  addPermissions(): void {
    this.permissionPopup.options = {
      header: "Role info",
      color: "darkred", // red, blue....
      widthPercentage: 50, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      animation: "fadeInDown" // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };
    this.popup.hide();
    this.permissionPopup.show(this.permissionPopup.options);
  }

  editRole(role: Role): void {
    this.message = 'You are now editing the role: ' + role.type;
    this.selectedRole = role;
    this.popup.options = {
      header: "Role info",
      color: "darkred", // red, blue....
      widthPercentage: 50, // The with of the popou measured by browser width
      animationDuration: 1, // in seconds, 0 = no animation
      showButtons: false, // You can hide this in case you want to use custom buttons
      animation: "fadeInDown" // 'fadeInLeft', 'fadeInRight', 'fadeInUp', 'bounceIn','bounceInDown'
    };
    this.popup.show(this.popup.options);

  }

  showEditPopup() {
    this.pressedEdit = true;
  }

  disableRole() {

  }

  submitAddPermissionsForm() {
    this.permissionPopup.hide();
    this.popup.show();
  }

  submitRoleForm() {
    console.log('done');
  }

  hidePopup() {
    this.popup.hide();
    this.pressedEdit = false;
  }
}
