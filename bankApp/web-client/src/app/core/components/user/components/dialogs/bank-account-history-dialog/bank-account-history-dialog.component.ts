import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {BankAccount} from "../../../../../model/bank/bankAccount/BankAccount";
import {MatTableDataSource} from "@angular/material/table";
import {History} from "../../../../../model/bank/history/History";
import {MatPaginator} from "@angular/material/paginator";
import {HistoryService} from "../../../services/history.service";

@Component({
  selector: 'app-bank-account-history-dialog',
  templateUrl: './bank-account-history-dialog.component.html',
  styleUrls: ['./bank-account-history-dialog.component.scss']
})
export class BankAccountHistoryDialogComponent implements OnInit {

  historyData!: MatTableDataSource<History>;
  historyColumns: string[] = ['id', 'operation-type', 'recipient-number', 'date-operation', 'money'];
  @ViewChild(MatPaginator) historyPaginator!: MatPaginator;

  constructor(@Inject(MAT_DIALOG_DATA) private bankAccountData: BankAccount,
              private historyService: HistoryService) {
    this.getHistoryOperationWithBankAccount();
  }

  ngOnInit(): void {
  }

  private getHistoryOperationWithBankAccount() {
    this.historyService.getHistoryOperationWithBankAccount(this.bankAccountData.bankAccountId).subscribe((data) => {
      this.historyData = new MatTableDataSource<History>(data);
      this.historyData.paginator = this.historyPaginator;
    })
  }

}
