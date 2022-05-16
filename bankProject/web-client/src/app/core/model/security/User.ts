import {UserRoleType} from "./UserRoleType";
import {Card} from "../bank/card/Card";

export interface User {
  userId: number;
  firstName: string;
  secondName: string;
  middleName: string;
  phoneNumber: string;
  isActive: boolean;
  userLogin: string;
  email: string;
  passwordHash: string;
  roleType: UserRoleType
  cards: Card[]
  sex:string;
}
