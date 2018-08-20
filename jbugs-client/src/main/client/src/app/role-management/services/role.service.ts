import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {User} from "../../user-management/services/user.service";
import {Role} from "../entities/role";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private baseURL = 'http://localhost:8080/jbugs/rest';

  constructor(private http: HttpClient) { }

  public getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(this.baseURL + '/roles');
  }

  public updateRole(role: Role): Observable<Role> {
    return this.http.post<Role>(this.baseURL + '/roles',role);
  }

}
