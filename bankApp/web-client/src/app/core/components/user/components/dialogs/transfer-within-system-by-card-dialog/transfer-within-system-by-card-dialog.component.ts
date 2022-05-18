import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Card} from "../../../../../model/bank/card/Card";
import {FormBuilder, Validators} from "@angular/forms";
import {TransferService} from "../../../services/transfer.service";
import {CardService} from "../../../services/card..service";
import {BankAccountService} from "../../../services/bank-account.service";
import {BankAccount} from "../../../../../model/bank/bankAccount/BankAccount";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";

const SBER_BANK_IDENTIFY: string = "276160";

@Component({
  selector: 'app-transfer-within-system-by-card-dialog',
  templateUrl: './transfer-within-system-by-card-dialog.component.html',
  styleUrls: ['./transfer-within-system-by-card-dialog.component.scss']
})
export class TransferWithinSystemByCardDialogComponent implements OnInit {

  minMoneyAmount: number = 10;
  card: Card = {} as Card;

  transferToOtherUserForm = this.formBuilder.group({
    cardRecipientNumber: ['', [Validators.required, Validators.pattern("\\d{1}276160\\d{9}"), Validators.maxLength(16), Validators.maxLength(16)]],
    money: ['', [Validators.required, Validators.pattern('^\\d*\\.?(\\d{1}|\\d{2})$'), Validators.min(this.minMoneyAmount)]]
  });
  transferToUserSelfForm = this.formBuilder.group({
    cardNumber: ['', [Validators.required]],
    money: ['', [Validators.required, Validators.pattern('^\\d*\\.?(\\d{1}|\\d{2})$'), Validators.min(this.minMoneyAmount)]]
  });
  transferFromCardToBankAccountForm = this.formBuilder.group({
    beneficiaryAccount: ['', [Validators.required, Validators.pattern("\\d{20}"), Validators.maxLength(20), Validators.maxLength(20)]],
    money: ['', [Validators.required, Validators.pattern('^\\d*\\.?(\\d{1}|\\d{2})$'), Validators.min(this.minMoneyAmount)]]
  });
  private cardsEntry: Array<Card>;
  private bankAccountEntry: Array<BankAccount>;


  constructor(@Inject(MAT_DIALOG_DATA) private data: Map<string, number>,
              private formBuilder: FormBuilder,
              private transferService: TransferService,
              private cardService: CardService,
              private bankAccountService: BankAccountService,
              private snackBar: MatSnackBar,
              private tokenStorageService: TokenStorageService) {
    this.getCardById();
    this.cardsEntry = new Array<Card>();
    this.bankAccountEntry = new Array<BankAccount>();
    this.showUserCards();
    this.showUserBankAccounts();
  }


  ngOnInit(): void {
  }

  public getIdentify(): string {
    return SBER_BANK_IDENTIFY;
  }

  public transferMoneyWithCardWithinSystem(): void {
    this.transferService.transferMoneyWithCardWithinSystem(this.data.get('cardId')!, this.transferToOtherUserForm.value).toPromise().then(() => {
      this.close();
    }).catch((err: HttpErrorResponse) => {
      this.snackBar.open(JSON.stringify(err.error), "", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 5000,
        panelClass: ['error']
      })
    });
  }

  public transferMoneyFromCardToBankAccount(): void {
    this.transferService.transferMoneyFromCardToBankAccount(this.data.get('cardId')!, this.transferFromCardToBankAccountForm.value).subscribe(() => {
      this.close();
    });
  }

  public transferMoneyToUserSelf(): void {
    console.log(this.transferToUserSelfForm.value);
    this.transferService.transferMoneyToUserSelf(Number(this.tokenStorageService.getUserId()), this.data.get('cardId')!, this.transferToUserSelfForm.value).toPromise().then(() => {
      this.close();
    }).catch((err: HttpErrorResponse) => {
      this.snackBar.open(JSON.stringify(err.error), "", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 5000,
        panelClass: ['error']
      })
    });
  }

  public excludeUserFromCard(cardNumber: string): Array<Card> {
    return this.cardsEntry.filter(card => card.cardNumber !== cardNumber);
  }

  public getUserBankAccountEntry(): BankAccount[] {
    return this.bankAccountEntry;
  }

  public changeCardClassToCol(transferFormIsDirty: boolean): string {
    return (transferFormIsDirty) ? "col" : "";
  }


  private close(): void {
    location.reload();
  }

  private getCardById(): void {
    this.cardService.getCardById(this.data.get('cardId')!).subscribe((data: Card) => {
      this.card = data;
    });
  }

  private showUserCards(): void {
    this.cardService.showCards(Number(this.tokenStorageService.getUserId())).subscribe(data => {
      this.cardsEntry = data;
    });
  }

  private showUserBankAccounts(): void {
    this.bankAccountService.findAllBankAccountsByUserId(Number(this.tokenStorageService.getUserId())).subscribe(data => {
      this.bankAccountEntry = data;
    });

  }


}
