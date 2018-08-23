import {Permission} from "./permission";
import {FormControl} from "@angular/forms";

export class Role{
  id: number;
  permissions: Permission[];
  type: string;
  selected: boolean;
}
