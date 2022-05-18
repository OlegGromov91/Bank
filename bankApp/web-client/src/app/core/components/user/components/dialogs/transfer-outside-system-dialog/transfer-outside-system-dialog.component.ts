import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {FormBuilder, Validators} from "@angular/forms";
import {TransferService} from "../../../services/transfer.service";
import {CardService} from "../../../services/card..service";
import {Card} from "../../../../../model/bank/card/Card";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {BanksCommission} from "../../../../../model/bank/transaction/BanksCommission";


@Component({
  selector: 'app-transfer-outside-system-dialog',
  templateUrl: './transfer-outside-system-dialog.component.html',
  styleUrls: ['./transfer-outside-system-dialog.component.scss']
})
export class TransferOutsideSystemDialogComponent implements OnInit {

  bankCommissionType: Array<BanksCommission> = [BanksCommission.ALPHA_BANK, BanksCommission.TINKOFF_BANK];
  commissions: Map<BanksCommission, number> = new Map<BanksCommission, number>([
    [BanksCommission.TINKOFF_BANK, 25],
    [BanksCommission.ALPHA_BANK, 30]
  ]);
  private bankCommissionTypeLocalized: Map<string, string> = new Map<string, string>([
    [BanksCommission.TINKOFF_BANK, "Тинькофф Банк"],
    [BanksCommission.ALPHA_BANK, "Альфа Банк"]
  ]);
  minMoneyAmount: number = 10;
  card: Card = {} as Card;
  transferToOtherUserForm = this.formBuilder.group({
    cardRecipientNumber: ['', [Validators.required, Validators.pattern("\\d{16}")]],
    money: ['', [Validators.required, Validators.pattern('^\\d*\\.?(\\d{1}|\\d{2})$'), Validators.min(this.minMoneyAmount)]],
    commissionBankType: ['', [Validators.required]]
  });

  constructor(@Inject(MAT_DIALOG_DATA) private data: Map<string, number>,
              private formBuilder: FormBuilder,
              private transferService: TransferService,
              private _snackBar: MatSnackBar,
              private cardService: CardService) {
    this.getCardById();
  }

  ngOnInit(): void {
  }

  public getCommissionType(bankCommissionType: BanksCommission): string {
    return this.bankCommissionTypeLocalized.get(bankCommissionType)!
  }

  public getCommissionMoney(bankCommissionType: BanksCommission): number {
    return this.commissions.get(bankCommissionType)!;
  }

  public transferMoneyWithCardOutsideSystem(): void {
    this.transferService.transferMoneyWithCardOutsideSystem(this.data.get('cardId')!, this.transferToOtherUserForm.value)
      .toPromise()
      .then(() => this.close())
      .catch((err: HttpErrorResponse) => {
        this._snackBar.open(JSON.stringify(err.error), "OK", {
          horizontalPosition: "end",
          verticalPosition: "top",
          duration: 5000,
          panelClass: ['error']
        });
      });
  }

  public close(): void {
    location.reload();
  }

  private getCardById(): void {
    this.cardService.getCardById(this.data.get('cardId')!).subscribe((data: Card) => {
      this.card = data;
    });
  }
}
