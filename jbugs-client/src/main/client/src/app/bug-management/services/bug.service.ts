import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {User} from "../../user-management/services/user.service";

export interface Bug {
  id: number;
  title: string;
  description: string;
  version: string;
  targetDate: string;
  status: string;
  fixedVersion: string;
  severity: string;
  attachment: string;
  assignedTo: User;
  createdBy: User;
}

@Injectable({
  providedIn: 'root'
})
export class BugService {

  baseURL = 'http://localhost:8080/jbugs/rest';

  constructor(private router: Router,private http: HttpClient) {
  }


  getAllBugs(): Observable<Bug[]> {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.get<Bug[]>(this.baseURL + '/bugs',{headers});
  }

  sendFile(formData: FormData){
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.post<boolean>(this.baseURL + '/bugs/upload', formData, {headers});
  }

  getUserForBugCreation(){
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    let id = localStorage.getItem("id");
    //check if id exists
    let url =  `${this.baseURL}/users/${id}`;
    return this.http.get<User>(url, {headers:headers});

  }

  getUserAssigned(username){
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    let url =  `${this.baseURL}/users/username/${username}`;
    return this.http.get<User>(url, {headers:headers});
  }

  createBug(bug: Bug) {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    let body = {
      'id' : bug.id,
      'title' : bug.title,
      'description' : bug.description,
      'version' : bug.version,
      'targetDate' : bug.targetDate,
      'status' : bug.status,
      'fixedVersion' : bug.fixedVersion,
      'severity' : bug.severity,
      'createdBy' : bug.createdBy,
      'assignedTo' : bug.assignedTo,
      'attachment' : bug.attachment
    };
    return this.http.post(this.baseURL + '/bugs', body, {headers});
  }

  updateBug(bug: Bug) {
    let currentUser = localStorage.getItem("currentUser");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});
    return this.http.put<boolean>(this.baseURL + '/bugs', bug,{headers});
  }
}
