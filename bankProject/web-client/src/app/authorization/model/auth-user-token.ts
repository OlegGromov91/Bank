import {UserRoleType} from "../../core/model/security/UserRoleType";

export interface AuthUserToken {
  userId: number;
  login: string;
  token: string;
  roleType: UserRoleType;
}
