import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";

export interface Bug {
  id: number;
  title: string;
  description: string;
  status: string;
  severity: string;
  fixedVersion: string;
  targetDate: string;
  version: string;
  assignedTo: number;
  createdBy: number;
}

@Injectable({
  providedIn: 'root'
})
export class BugService {

  baseURL = 'http://localhost:8080/jbugs/rest';

  constructor(private router: Router,private http: HttpClient) {
  }


  getAllBugs(): Observable<Bug[]> {
    return this.http.get<Bug[]>(this.baseURL + '/bugs');
  }
}
