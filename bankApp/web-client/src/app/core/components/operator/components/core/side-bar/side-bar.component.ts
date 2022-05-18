import {Component, OnInit} from '@angular/core';
import {User} from "../../../../../model/security/User";
import {UserRoleType} from "../../../../../model/security/UserRoleType";
import {UserService} from "../../../../user/services/user.service";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent implements OnInit {
  user: User = {} as User;
  private userLogoClass: Map<string, string> = new Map<string, string>([
    [UserRoleType.ADMIN, "admin-logo"],
    [UserRoleType.USER + "female", "f-user-logo"],
    [UserRoleType.USER + "male", "m-user-logo"],
    [UserRoleType.OPERATOR, "operator-logo"]
  ]);
  private unknownUSerLogo: string = "unknown-logo";

  constructor(private userService: UserService,
              private tokenService: TokenStorageService) {
    userService.getUser(Number(this.tokenService.getUserId())).subscribe((data: User) => {
      this.user = data;
    });
  }

  ngOnInit(): void {
  }

  public getUserLogoClass(userSex: string): string {
    return (this.tokenService.getUserRole() != UserRoleType.USER) ?
      this.userLogoClass.get(this.tokenService.getUserRole()) || this.unknownUSerLogo :
      this.userLogoClass.get(this.tokenService.getUserRole() + userSex) || this.unknownUSerLogo
  }

  public showUserRole(): string {
    const localizedRole = new Map<string, string>([
      [UserRoleType.OPERATOR, "Оператор"],
      [UserRoleType.ADMIN, "Администратор"],
      [UserRoleType.USER, "Пользователь"]
    ]);
    return localizedRole.get(this.tokenService.getUserRole()) || "";
  }

}
