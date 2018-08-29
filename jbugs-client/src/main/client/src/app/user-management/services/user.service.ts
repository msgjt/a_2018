import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Router} from "@angular/router";
import {Role} from "../../role-management/entities/role";
import {RoleService} from "../../role-management/services/role.service";
import {Permission} from "../../role-management/entities/permission";

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  isActive: boolean;
  phoneNumber: string;
  email: string;
  roles: Role[];
  username: string;
  password: string;
}

export const LSKEY = 'currentUser';
export const TOKENKEY = 'webtoken';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseURL = 'http://localhost:8080/jbugs/rest';


  constructor(private router: Router,private http: HttpClient, private roleService: RoleService) {
  }

  addUser(firstname: string, lastname: string, email: string, mobileNumber: string, username: string, password: string, roles: Role[]) {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    let body = {
      'firstName': firstname,
      'lastName': lastname,
      'email': email,
      'phoneNumber': mobileNumber,
      'username': username,
      'password': password,
      'roles': roles
    };
    return this.http.post<boolean>(this.baseURL + '/users', body,{headers});
  }

  getAllUsers(): Observable<User[]> {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.get<User[]>(this.baseURL + '/users',{headers});
  }

  updateUser( id: number, firstname: string, lastname: string, email: string, mobileNumber: string, roles: Role[]) {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    let body = {
      'id': id,
      'firstName': firstname,
      'lastName': lastname,
      'email': email,
      'phoneNumber': mobileNumber,
      'roles': roles
    };
    return this.http.put<boolean>(this.baseURL + '/users', body,{headers});
  }
  updateUserPassword( id: number, password: string){
    let body = {
      'id': id,
      'password': password};
    return this.http.put<boolean>(this.baseURL + '/users/changePassword', body);
  }

  deactivateUser(id: number){
    let body = {
      'id': id};
    return this.http.put<boolean>(this.baseURL + '/users/deactivate', body);
  }

  activateUser(id: number){
    let body = {
      'id': id};
    return this.http.put<boolean>(this.baseURL + '/users/activate', body);
  }

  validateUserCredentials(username: string, password: string): Observable<any> {
    let body = {
      'username': username,
      'password': password
    };
    return this.http.post(this.baseURL + '/authorize',body);
  }

  isLoggedIn() {
    let username = localStorage.getItem(LSKEY);
    return username ? true : false;
  }

  isLoggedInOnServer():Observable<boolean>{
    let body=localStorage.getItem(LSKEY)+"   "+localStorage.getItem(TOKENKEY);
    return this.http.post<boolean>(this.baseURL+'/loggedin',body);
  }

  logout(username: String):Observable<any>{
    return this.http.post(this.baseURL + '/logout',username);
  }

  getAllRoles(): Observable<Role[]> {
    return this.roleService.getAllRoles();
  }

  getUsersPermissions(currentUser: string): Observable<any> {
    return this.http.get<String[]>(this.baseURL+'/userpermissions/'+currentUser);
  }



}
