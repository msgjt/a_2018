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
    for(let key in json) {
      if(json.hasOwnProperty(key)) {
        json[key]['assignedTo'] = json[key]['assignedTo'].username;
        json[key]['createdBy'] = json[key]['createdBy'].username;
      }
    }
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    const workbook: XLSX.WorkBook = {Sheets: {'data': worksheet}, SheetNames: ['data']};
    XLSX.writeFile(workbook, ExcelService.toExportFileName());
  }
}
