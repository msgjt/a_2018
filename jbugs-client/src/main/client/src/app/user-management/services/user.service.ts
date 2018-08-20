import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Router} from "@angular/router";
import {catchError, map} from "rxjs/operators";

export interface User {
  firstName: string;
  lastName: string;
  isActive: number;
  mobileNumber: string;
  email: string;
  roles: string;
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

  constructor(private router: Router,private http: HttpClient) {
  }

  addUser(firstname: string, lastname: string, email: string, mobileNumber: string, username: string, password: string) {
    let body = {
      'firstName': firstname,
      'lastName': lastname,
      'email': email,
      'phoneNumber': mobileNumber,
      'username': username,
      'password': password
    };
    return this.http.post<boolean>(this.baseURL + '/users', body);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseURL + '/users');
  }

  updateUser( firstname: string, lastname: string, email: string, mobileNumber: string, username: string, password: string) {
    let body = {
      'firstName': firstname,
      'lastName': lastname,
      'email': email,
      'phoneNumber': mobileNumber,
      'username': username,
      'password': password
    };
    return this.http.put<boolean>(this.baseURL + '/users', body);
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
}
