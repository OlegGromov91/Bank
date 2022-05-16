import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {OperatorRoutingModule} from './operator-routing.module';
import {OperatorDashboardComponent} from './components/core/operator-dashboard/operator-dashboard.component';
import {SideBarComponent} from './components/core/side-bar/side-bar.component';
import {HomeComponent} from './components/core/home/home.component';
import {HeaderComponent} from "./components/core/header/header.component";
import {FooterComponent} from "./components/core/footer/footer.component";
import {NgxPaginationModule} from "ngx-pagination";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSliderModule} from "@angular/material/slider";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import {MatDialogModule} from "@angular/material/dialog";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {UnlockUserCardDialogComponent} from './components/dialogs/unlock-user-card-dialog/unlock-user-card-dialog.component';


@NgModule({
  declarations: [
    OperatorDashboardComponent,
    HeaderComponent,
    FooterComponent,
    SideBarComponent,
    HomeComponent,
    UnlockUserCardDialogComponent,
  ],
  imports: [
    CommonModule,
    OperatorRoutingModule,
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    MatSliderModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatFormFieldModule,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    MatDialogModule,
    MatGridListModule,
    MatTableModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatSidenavModule
  ]
})
export class OperatorModule {
}
