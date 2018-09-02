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
export interface Success {
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
  @Input() successes: Success[] = [];

  constructor(private router: Router) { }

  ngOnInit() {

  }


}
