import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserRoutingModule} from './user-routing.module';


import {HomeComponent} from './components/core/home/home.component';
import {UserDashboardComponent} from './components/core/user-dashboard/user-dashboard.component';
import {NgxPaginationModule} from "ngx-pagination";
import {SideBarComponent} from './components/core/side-bar/side-bar.component';
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
import {ChoseTransferDialogComponent} from './components/dialogs/chose-transfer-dialog/chose-transfer-dialog.component';
import {CardComponent} from './components/bank/card/card.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {BalanceDialogComponent} from "./components/dialogs/balance-dialog/balance-dialog.component";
import {TransferOutsideSystemDialogComponent} from "./components/dialogs/transfer-outside-system-dialog/transfer-outside-system-dialog.component";
import {TransferWithinSystemByCardDialogComponent} from "./components/dialogs/transfer-within-system-by-card-dialog/transfer-within-system-by-card-dialog.component";
import {PinCodeDialogComponent} from "./components/dialogs/pin-code-dialog/pin-code-dialog.component";
import {FooterComponent} from "./components/core/footer/footer.component";
import {HeaderComponent} from "./components/core/header/header.component";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSidenavModule} from "@angular/material/sidenav";
import {CreateCardDialogComponent} from './components/dialogs/create-card-dialog/create-card-dialog.component';
import {UnlockCardDialogComponent} from './components/dialogs/unlock-card-dialog/unlock-card-dialog.component';
import {TransferWithinSystemByAccountDialogComponent} from './components/dialogs/transfer-within-system-by-account-dialog/transfer-within-system-by-account-dialog.component';
import {BankAccountHistoryDialogComponent} from './components/dialogs/bank-account-history-dialog/bank-account-history-dialog.component';
import {ChoseBankAccountOperationDialogComponent} from './components/dialogs/chose-bank-account-operation-dialog/chose-bank-account-operation-dialog.component';


@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    UserDashboardComponent,
    SideBarComponent,
    ChoseTransferDialogComponent,
    CardComponent,
    PinCodeDialogComponent,
    BalanceDialogComponent,
    TransferWithinSystemByCardDialogComponent,
    TransferOutsideSystemDialogComponent,
    CreateCardDialogComponent,
    UnlockCardDialogComponent,
    TransferWithinSystemByAccountDialogComponent,
    BankAccountHistoryDialogComponent,
    ChoseBankAccountOperationDialogComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
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
export class UserModule {
}
