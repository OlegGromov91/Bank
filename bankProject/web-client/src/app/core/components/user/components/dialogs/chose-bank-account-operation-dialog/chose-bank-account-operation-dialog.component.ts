import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {Router} from "@angular/router";
import {TransferWithinSystemByCardDialogComponent} from "../transfer-within-system-by-card-dialog/transfer-within-system-by-card-dialog.component";
import {TransferOutsideSystemDialogComponent} from "../transfer-outside-system-dialog/transfer-outside-system-dialog.component";
import {BankAccount} from "../../../../../model/bank/bankAccount/BankAccount";
import {TransferWithinSystemByAccountDialogComponent} from "../transfer-within-system-by-account-dialog/transfer-within-system-by-account-dialog.component";
import {BankAccountHistoryDialogComponent} from "../bank-account-history-dialog/bank-account-history-dialog.component";

@Component({
  selector: 'app-chose-bank-account-operation-dialog',
  templateUrl: './chose-bank-account-operation-dialog.component.html',
  styleUrls: ['./chose-bank-account-operation-dialog.component.scss']
})
export class ChoseBankAccountOperationDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) private bankAccountData: BankAccount,
              private tokenService: TokenStorageService,
              private router: Router,
              private matDialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  public choseTransferWithinSystemByAccount(): void {
    this.openTransferWithinSystemByAccountDialog();
  }

  public choseHistoryAccount(): void {
    this.openBankAccountHistoryDialog();
  }

  private openTransferWithinSystemByAccountDialog() {
    this.matDialog.open(TransferWithinSystemByAccountDialogComponent, {data: this.bankAccountData});
  }

  private openBankAccountHistoryDialog() {
    this.matDialog.open(BankAccountHistoryDialogComponent, {data: this.bankAccountData});
  }

}
