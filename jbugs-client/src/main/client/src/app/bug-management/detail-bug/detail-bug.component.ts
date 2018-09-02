import {Component, Input, OnInit} from '@angular/core';
import {toBase64String} from "@angular/compiler/src/output/source_map";
import {BugService} from "../services/bug.service";
import { saveAs } from 'file-saver/FileSaver';
import {HttpClient} from "@angular/common/http";
declare var jsPDF: any;

@Component({
  selector: 'app-detail-bug',
  templateUrl: './detail-bug.component.html',
  styleUrls: ['./detail-bug.component.css']
})
export class DetailBugComponent implements OnInit {


  @Input() detailedBug;
  errorMessage: string;
  errorOccurred: boolean;

  constructor(private bugService: BugService, private http:HttpClient) { }

  ngOnInit() {
    this.isBUG_EXPORT_PDF_ON_SERVER();
    this.errorMessage = "";
    this.errorOccurred = false;
  }

  isBUG_EXPORT_PDF(): boolean {
    return localStorage.getItem('BUG_EXPORT_PDF') != null;}

  isBUG_EXPORT_PDF_ON_SERVER(){
    this.bugService.isBUG_EXPORT_PDF_ON_SERVER().subscribe(response => {
      if (response == true) {
        if(localStorage.getItem("BUG_EXPORT_PDF")=="1"){
        }else {
          localStorage.setItem("BUG_EXPORT_PDF","1");
        }
      } else {
        localStorage.removeItem("BUG_EXPORT_PDF");
      }
    })
  }

  exportToPdf(description: string, fixedVersion :string, severity: string, status: string, targetDate: string,
              title :string, version :string){

    let imgData = 'data:image/png;base64,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';
    let doc = new jsPDF('p', 'pt', 'a4');
    doc.addImage(imgData, 'PNG', 15, 40, 90, 27);
    doc.setFontSize(24);
    doc.text('Team A', 485, 65);
    doc.setLineWidth(1);
    doc.line(15,75,570,75);
    let columns = [
      { title: 'Title', dataKey: 'title' },
      { title: 'Description', dataKey: 'desc' },
      { title: 'Version', dataKey: 'version' },
      { title: 'Status', dataKey: 'status' },
      { title: 'Severity', dataKey: 'severity' },
      { title: 'Target date', dataKey: 'date' },
      { title: 'Fixed in version', dataKey: 'fixedVersion' },
    ];
    let row = [
      {
        'title': title,
        'desc': description,
        'version': version,
        'status': status,
        'severity': severity,
        'date': targetDate,
        'fixedVersion': fixedVersion
      }
    ];
    doc.autoTable(columns, row, {
      startY: 150,
      bodyStyles: {
        valign: 'middle',
        halign: 'center'
      },
      styles: {
        overflow: 'linebreak',
        columnWidth: 'wrap'
      },
      headerStyles: {
        fillColor: [139, 0 ,0],
        halign: 'center'
      },
      columnStyles: {
        desc: {
          columnWidth: 'auto'
        }
      }
    });
    doc.save(title+'_'+version+'.pdf');
  }

  downloadAttachment(id){
    this.bugService.downloadAttachment(id)
      .subscribe(
        (response) => {
          let filename = this.detailedBug.attachment;
          saveAs(response, filename);
          },
        () => {
          this.errorOccurred = true;
          this.errorMessage = 'File could not be downloaded';
        }
      );
  }
}
