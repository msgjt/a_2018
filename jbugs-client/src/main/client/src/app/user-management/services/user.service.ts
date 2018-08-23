import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Router} from "@angular/router";
import {Role} from "../../role-management/entities/role";
import {RoleService} from "../../role-management/services/role.service";

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
      'roleDTOS': roles
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

  updateUser( id: number, firstname: string, lastname: string, email: string, mobileNumber: string) {
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
    };
    return this.http.put<boolean>(this.baseURL + '/users', body,{headers});
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

  logout(username: String):Observable<any>{
    return this.http.post(this.baseURL + '/logout',username);;
  }

  getAllRoles(): Observable<Role[]> {
    return this.roleService.getAllRoles();
  }

}
