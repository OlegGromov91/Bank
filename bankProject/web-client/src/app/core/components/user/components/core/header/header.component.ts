import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {AuthService} from "../../../../../../authorization/services/auth-service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private tokenStorageService: TokenStorageService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  public logOut(): void {
    this.authService.logOut(this.tokenStorageService);
  }

}
