import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";
import {User} from "../../user-management/services/user.service";

export interface Bug {
  id: number;
  title: string;
  description: string;
  status: string;
  severity: string;
  fixedVersion: string;
  targetDate: string;
  version: string;
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

  createBug(bug: Bug) {

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
