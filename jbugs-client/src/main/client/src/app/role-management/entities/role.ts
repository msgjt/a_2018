import {Permission} from "./permission";

export class Role{
  id: number;
  permissions: Permission[];
  type: string;
  selected: boolean;
}
