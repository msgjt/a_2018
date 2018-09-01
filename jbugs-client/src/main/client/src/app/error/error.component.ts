import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";




@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {


  constructor() { }

  ngOnInit() {

  }

  isUserLoggedIn() {
    return localStorage.getItem('id') != null;
  }



}
