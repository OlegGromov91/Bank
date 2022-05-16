import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanDeactivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenStorageService} from "../authorization/services/token-storage.service";
import {UserRoleType} from "../core/model/security/UserRoleType";

@Injectable({
  providedIn: 'root'
})


export class AuthGuard implements CanActivate, CanDeactivate<unknown> {


  constructor(private tokenService: TokenStorageService, private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return (this.tokenService.isLoggedIn() && this.tokenService.getUserRole() == UserRoleType.USER) ? true : this.router.navigate(['/login']);
  }

  canDeactivate(
    component: unknown,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }

}
