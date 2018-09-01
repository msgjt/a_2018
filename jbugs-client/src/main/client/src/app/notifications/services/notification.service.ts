import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Notification} from "../entities/Notification";
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private baseURL = 'http://localhost:8080/jbugs/rest';
  private instantiated: boolean = false;
  private instantiatedForOld: boolean = false;

  constructor(private http: HttpClient) {

  }

  wasInstantiated(): boolean {
    return this.instantiated == true;
  }

  instantiate() {
    this.instantiated = true;
  }

  deinstantiate() {
    this.instantiated = false;
  }

  deinstantiateForOld(){
    this.instantiatedForOld = false;
  }


  wasInstantiatedForOld(): boolean {
    return this.instantiatedForOld == true;
  }

  instantiateForOld() {
    this.instantiatedForOld = true;
  }




  getNewNotifications(): Observable<Notification[]> {
    let currentUser = localStorage.getItem("currentUser");
    let id = localStorage.getItem("id");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});

    let options = {
      headers: headers,
      params: new HttpParams().set('id',id)
    };

    return this.http.get<Notification[]>
      (this.baseURL + "/notifications",options);
  }

  getOldNotifications(): Observable<Notification[]>{
    let currentUser = localStorage.getItem("currentUser");
    let id = localStorage.getItem("id");
    let webtoken = localStorage.getItem("webtoken");
    let headers = new HttpHeaders(
      {'currentUser':currentUser,
        'webtoken':webtoken});


    let options = {
      headers: headers,
      params: new HttpParams().set('id',id)
    };


    return this.http.get<Notification[]>
    (this.baseURL + "/oldnotifications",options);
  }

}
