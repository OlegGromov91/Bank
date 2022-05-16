import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {CreateCardDialogComponent} from "../../dialogs/create-card-dialog/create-card-dialog.component";
import {UserService} from "../../../services/user.service";
import {User} from "../../../../../model/security/User";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {UserRoleType} from "../../../../../model/security/UserRoleType";
import {BankAccountService} from "../../../services/bank-account.service";
import {MatSnackBar} from "@angular/material/snack-bar";

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

  constructor(private matDialog: MatDialog,
              private userService: UserService,
              private tokenService: TokenStorageService,
              private bankAccountService: BankAccountService,
              private snackBar: MatSnackBar) {
    userService.getUser(Number(this.tokenService.getUserId())).subscribe((data: User) => {
      this.user = data;
    });
  }

  ngOnInit(): void {
  }

  public openBankAccount(): void {
    this.bankAccountService.createNewBankAccount(Number(this.tokenService.getUserId())).toPromise().then((data) => {
      this.snackBar.open("Счет открыт", "OK", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 3000,
        panelClass: ['success']
      });
      location.reload();
    });
  }

  public openCreateCardDialog(): void {
    this.matDialog.open(CreateCardDialogComponent).afterClosed().toPromise().then(
      () => location.reload()
    );
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
