import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {FormBuilder, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BankAccount} from "../../../../../model/bank/bankAccount/BankAccount";
import {BankAccountService} from "../../../services/bank-account.service";
import {TransferService} from "../../../services/transfer.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-transfer-within-system-by-account-dialog',
  templateUrl: './transfer-within-system-by-account-dialog.component.html',
  styleUrls: ['./transfer-within-system-by-account-dialog.component.scss']
})
export class TransferWithinSystemByAccountDialogComponent implements OnInit {

  minMoneyAmount: number = 10;
  private bankAccountEntry: Array<BankAccount>;
  transferFromBankAccountToBankAccountForm = this.formBuilder.group({
    beneficiaryBankAccountFrom: [this.bankAccountData],
    beneficiaryAccount: ['', [Validators.required, Validators.pattern("\\d{20}"), Validators.maxLength(20), Validators.maxLength(20)]],
    money: ['', [Validators.required, Validators.pattern('^\\d*\\.?(\\d{1}|\\d{2})$'), Validators.min(this.minMoneyAmount)]]
  });

  constructor(@Inject(MAT_DIALOG_DATA) private bankAccountData: BankAccount,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar,
              private bankAccountService: BankAccountService,
              private tokenStorageService: TokenStorageService,
              private transferService: TransferService,
              private dialogRef: MatDialogRef<TransferWithinSystemByAccountDialogComponent>) {
    this.bankAccountEntry = new Array<BankAccount>();
    this.showUserBankAccounts();
  }

  ngOnInit(): void {
  }

  public transferMoneyFromBankAccountToBankAccount(): void {
    console.log(this.transferFromBankAccountToBankAccountForm.value);
    this.transferService.transferMoneyFromBankAccountToBankAccount(this.transferFromBankAccountToBankAccountForm.value).toPromise().then(() => {
        this.snackBar.open("Деньги отправлены", "OK", {
            horizontalPosition: "end",
            verticalPosition: "top",
            duration: 3000,
            panelClass: ['success']
          }
        );
        this.dialogRef.close()
      }
    ).catch((err: HttpErrorResponse) => {
      this.snackBar.open(JSON.stringify(err.error), "", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 5000,
        panelClass: ['error']
      })
    });
  }

  public getBankAccountData(): BankAccount {
    return this.bankAccountData;
  }

  public excludeUserFromBankAccount(bankAccountNumber: string): Array<BankAccount> {
    return this.bankAccountEntry.filter(bankAccount => bankAccount.beneficiaryAccount !== bankAccountNumber);
  }

  private showUserBankAccounts() {
    this.bankAccountService.findAllBankAccountsByUserId(Number(this.tokenStorageService.getUserId())).subscribe(data => {
      this.bankAccountEntry = data;
    });

  }


}
