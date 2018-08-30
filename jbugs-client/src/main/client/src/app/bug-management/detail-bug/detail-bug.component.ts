import {Component, Input, OnInit} from '@angular/core';
import * as jsPDF from 'jspdf';

@Component({
  selector: 'app-detail-bug',
  templateUrl: './detail-bug.component.html',
  styleUrls: ['./detail-bug.component.css']
})
export class DetailBugComponent implements OnInit {


  @Input() detailedBug;

  constructor() { }

  ngOnInit() {
  }

  isBUG_EXPORT_PDF(): boolean {
    return localStorage.getItem('BUG_EXPORT_PDF') != null;}

  exportToPdf(description: string, fixedVersion :string, severity: string, status: string, targetDate: string,
              title :string, version :string){
    let doc = new jsPDF('p' ,'pt' ,'a4');
    doc.text(doc.splitTextToSize('MSG ROMANIA: BUG '+title , 180), 10, 10);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 20);
    doc.text(doc.splitTextToSize('DESCRIPTION: '+description, 580),  10, 30);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 40);
    doc.text('FIXED VERSION: '+fixedVersion, 10, 50);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 60);
    doc.text('SEVERITY: '+severity, 10, 70);
    doc.text('-----------------------------------------------------------------------------------------------------',10, 80);
    doc.text('STATUS: '+status, 10, 90);
    doc.text('TARGET DATE: '+targetDate, 10, 110);
    doc.text('TITLE: '+title, 10, 130);
    doc.text('VERSION: '+version, 10, 150);
    doc.text('TEAM A ',10, 170);
    doc.save(title+'_'+version+'.pdf');

  }
}
