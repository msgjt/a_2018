import { Pipe, PipeTransform } from '@angular/core';
import {Bug} from "./bug-management/services/bug.service";
@Pipe({
  name: 'tableFilter'
})
export class FilterPipe implements PipeTransform {

  transform(value: Bug[], q: string) {
    if (!q || q === '') {
      return value;
    }
    return value.filter(item => (-1 < item.description.toLowerCase().indexOf(q.toLowerCase())) ||
      (-1 < item.fixedVersion.toLowerCase().indexOf(q.toLowerCase())) ||
      (-1 < item.severity.toLowerCase().indexOf(q.toLowerCase())) ||
      (-1 < item.status.toLowerCase().indexOf(q.toLowerCase())) ||
      (-1 < item.targetDate.toLowerCase().indexOf(q.toLowerCase())) ||
      (-1 < item.title.toLowerCase().indexOf(q.toLowerCase())) ||
      (-1 < item.version.toLowerCase().indexOf(q.toLowerCase()))
    );
  }

}
