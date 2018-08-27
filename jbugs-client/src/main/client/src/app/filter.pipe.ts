import { Pipe, PipeTransform } from '@angular/core';
import {Bug} from "./bug-management/services/bug.service";
@Pipe({
  name: 'tableFilter'
})
export class FilterPipe implements PipeTransform {

  transform(items: Array<any>, filter: {[key: string]: any }): Array<any> {
    return items.filter(item => {
      let notMatchingField = Object.keys(filter)
        .find(key => -1 == item[key].toLowerCase().indexOf(filter[key].toLowerCase()));

      return !notMatchingField;
    });
  }
}
