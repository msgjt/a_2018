import {Injectable} from '@angular/core';
import * as XLSX from 'xlsx';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor() { }

  static toExportFileName(): string {
    return `Bugs_to_export_${new Date().getTime()}.xlsx`;
  }

  public exportAsExcelFile(json: any[]): void {
    let copyOfJSON = [];
    for(let key in json) {
      copyOfJSON[key] = json[key];
    }
    for(let key in copyOfJSON) {
      if(copyOfJSON.hasOwnProperty(key)) {
        copyOfJSON[key]['assignedTo'] = copyOfJSON[key]['assignedTo'].username;
        copyOfJSON[key]['createdBy'] = copyOfJSON[key]['createdBy'].username;
      }
    }
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(copyOfJSON);
    const workbook: XLSX.WorkBook = {Sheets: {'data': worksheet}, SheetNames: ['data']};
    XLSX.writeFile(workbook, ExcelService.toExportFileName());
  }
}
