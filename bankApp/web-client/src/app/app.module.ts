import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginComponent} from "./authorization/component/login/login.component";
import {AuthService} from "./authorization/services/auth-service";
import {TokenStorageService} from "./authorization/services/token-storage.service";
import {httpInterceptorProviders} from "./authorization/component/auth-interceptor";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSliderModule} from "@angular/material/slider";
import {MatIconModule} from "@angular/material/icon";
import {DatePipe} from "@angular/common";





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatSliderModule,
    BrowserAnimationsModule,
    MatIconModule],
  providers: [AuthService, TokenStorageService, httpInterceptorProviders, DatePipe],
  exports: [
    LoginComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
