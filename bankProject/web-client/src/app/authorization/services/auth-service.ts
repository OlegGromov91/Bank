import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthLoginInfo} from "../model/auth-login-info";
import {AuthUserToken} from "../model/auth-user-token";
import {TokenStorageService} from "./token-storage.service";
import {Router} from "@angular/router";

const header = {
  'content-type': 'application/json',
  'Access-Control-Allow-Headers': '*'
};

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  private basicUriApi: string = `http://localhost:8090/api/auth/`;

  constructor(private http: HttpClient,
              private router: Router) {
  }


  authenticate(credentials: AuthLoginInfo): Observable<AuthUserToken> {
    // @ts-ignore
    return this.http.post<AuthUserToken>(this.basicUriApi + "login", credentials, header);
  }

  logOut(tokenStorageService: TokenStorageService): void {
    this.http.post<AuthUserToken>(this.basicUriApi + "logout", tokenStorageService).subscribe(() => {
      tokenStorageService.removeToken();
      this.router.navigate(["/login"]);
    });

  }

}
