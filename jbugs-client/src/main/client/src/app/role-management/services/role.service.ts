import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Role} from "../entities/role";
import {Permission} from "../entities/permission";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private baseURL = 'http://localhost:8080/jbugs/rest';

  constructor(private http: HttpClient) { }

  public getAllRoles(): Observable<Role[]> {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.get<Role[]>(this.baseURL + '/roles',{headers});
  }

  public updateRole(role: Role): Observable<Role> {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.post<Role>(this.baseURL + '/roles',role,{headers});
  }

  public getAllPermissions(): Observable<Permission[]> {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.get<Permission[]>(this.baseURL + '/permissions',{headers});
  }

}
