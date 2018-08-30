import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'tableFilter'
})
export class FilterPipe implements PipeTransform {

  transform(items: Array<any>, filter: { [key: string]: any }): Array<any> {
    return items.filter(item => {
      let notMatchingField = Object.keys(filter)
        .find(key => (key=='assignedTo' || key=='createdBy') ?  (-1 == item[key]['username'].toString().toLowerCase().indexOf(filter[key].toString().toLowerCase())) : (-1 == item[key].toString().toLowerCase().indexOf(filter[key].toString().toLowerCase())));
      return !notMatchingField;
    });
  }
}
