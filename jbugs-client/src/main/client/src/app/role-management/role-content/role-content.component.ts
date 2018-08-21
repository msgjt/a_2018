import { Component, OnInit } from '@angular/core';
import {RoleService} from "../services/role.service";
import {Role} from "../entities/role";

@Component({
  selector: 'app-role-content',
  templateUrl: './role-content.component.html',
  styleUrls: ['./role-content.component.css']
})
export class RoleContentComponent implements OnInit {

  public roles: Role[];

  constructor(private roleService: RoleService) {

  }

  ngOnInit() {
    this.roleService.getAllRoles().subscribe( r => this.roles = r);
  }

}
