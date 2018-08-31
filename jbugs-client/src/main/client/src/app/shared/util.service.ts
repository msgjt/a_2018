import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {LSKEY} from "../user-management/services/user.service";

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private router: Router)
  {

  }

  routeIfNotLoggedIn()
  {
    if( localStorage.getItem(LSKEY)){
      return true;
    }
    else {
      this.router.navigate(['./login']);
      return false;
    }
  }

}
