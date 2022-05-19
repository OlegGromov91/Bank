import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth-service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TokenStorageService} from "../../services/token-storage.service";
import {UserRoleType} from "../../../core/model/security/UserRoleType";

/**
 * Компонент логинит пользователя
 */

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  private UserRoleNavigation: Map<string, string> = new Map<string, string>([
    [UserRoleType.USER, "/user"],
    [UserRoleType.OPERATOR, "/operator"],
    [UserRoleType.ADMIN, "/admin"]
  ]);

  constructor(private service: AuthService,
              private route: ActivatedRoute,
              private router: Router,
              private tokenStorage: TokenStorageService,
  ) {
  }


  ngOnInit(): void {
    this.loginForm = new FormGroup(
      {
        'login': new FormControl('', [Validators.required]),
        'password': new FormControl('', [Validators.required])
      }
    );
    if (this.tokenStorage.isLoggedIn()) {
      this.router.navigate([this.UserRoleNavigation.get(this.tokenStorage.getUserRole()), this.tokenStorage.getUserId()]);
    }
  }

  public submitLogin(): void {
    this.authenticate();
  }

  private authenticate() {
    this.service.authenticate(this.loginForm.value).toPromise().then((data) => {
        this.tokenStorage.saveToken(data!);
        console.log(data, " authData");
        this.router.navigate([this.UserRoleNavigation.get(data!.roleType), this.tokenStorage.authUserToken.userId]);
      }
    ).catch(
      () => alert("Неверный логин или пароль! \nУчетки: \n" +
        "user male - login: user password: user\n" +
        "user female - login: FUser password: user\n" +
        "operator - login: oper password: oper"
      )
    )
  }


}
