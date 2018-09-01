import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";


export interface Error {
  id: number,
  type: string,
  details: {detail: string, message: string}[]
}
export interface Warning {
  message: string;
  recommendation: string;
  display: boolean;
}
export interface Information {
  message: string;
  display: boolean;
}
@Component({
  selector: 'app-communication',
  templateUrl: './communication.component.html',
  styleUrls: ['./communication.component.css']
})
export class CommunicationComponent implements OnInit {

  @Input() errors: Error[] = [];
  @Input() warnings: Warning[] = [];
  @Input() informations: Information[] = [];

  constructor(private router: Router) { }

  ngOnInit() {

  }

  isUserLoggedIn() {
    return localStorage.getItem('id') != null;
  }

  localStorageValidation() {
    return localStorage.getItem('currentUser') != null &&
      localStorage.getItem('id') != null
  }

}
