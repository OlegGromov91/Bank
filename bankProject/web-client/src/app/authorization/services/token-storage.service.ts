import {Injectable} from '@angular/core';
import {AuthUserToken} from "../model/auth-user-token";




const TOKEN_KEY = 'AuthToken';
const ID_KEY = 'AuthUserId';
const ROLE_KEY = 'AuthUserRole';

@Injectable({
  providedIn: 'root'
})

/**
 * Токены храняться в сессии
 */
export class TokenStorageService {

  constructor() {
  }

  authUserToken!: AuthUserToken;


  public saveToken(authUserToken: AuthUserToken): void {
    this.authUserToken = authUserToken;
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, authUserToken.token);
    this.saveUserId();
    this.saveUserRole();
  }

  public removeToken(): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
  }

  public isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  private saveUserId(): void {
    window.sessionStorage.removeItem(ID_KEY);
    window.sessionStorage.setItem(ID_KEY, this.authUserToken.userId.toString());
  }

  private saveUserRole(): void {
    window.sessionStorage.removeItem(ROLE_KEY);
    window.sessionStorage.setItem(ROLE_KEY, this.authUserToken.roleType);
  }

  public getUserId(): string {
    return window.sessionStorage.getItem(ID_KEY)!;
  }

  public getUserRole(): string {
    return window.sessionStorage.getItem(ROLE_KEY)!;
  }

  public getToken(): string {
    return window.sessionStorage.getItem(TOKEN_KEY)!;
  }
}
