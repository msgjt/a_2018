import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "./user-management/services/user.service";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  baseURL = 'http://localhost:8080/jbugs/rest';

  constructor(private http: HttpClient) { }

  getNewNotificationForUser(id: number){
    return this.http.get<Notification[]>(this.baseURL + '/getnotifications/'+id);
  }

  getOldNotificationForUser(id: number){
    return this.http.get<Notification[]>(this.baseURL + '/getoldnotifications/'+id);
  }
}
