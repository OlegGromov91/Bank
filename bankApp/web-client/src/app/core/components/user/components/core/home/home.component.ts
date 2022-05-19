import {Component, OnInit} from '@angular/core';
import {CardService} from "../../../services/card..service";
import {Card} from "../../../../../model/bank/card/Card";
import {ActivatedRoute, Router} from "@angular/router";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {PaymentSystem} from "../../../../../model/bank/card/PaymentSystem";
import {CardType} from "../../../../../model/bank/card/CardType";
import {MatDialog} from "@angular/material/dialog";
import {PinCodeDialogComponent} from "../../dialogs/pin-code-dialog/pin-code-dialog.component";
import {BankAccount} from "../../../../../model/bank/bankAccount/BankAccount";
import {BankAccountService} from "../../../services/bank-account.service";
import {ChoseBankAccountOperationDialogComponent} from "../../dialogs/chose-bank-account-operation-dialog/chose-bank-account-operation-dialog.component";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})


export class HomeComponent implements OnInit {
  cardsEntry: Array<Card>;
  totalCardRecords!: number;
  currentCardPageNumber: number = 1;
  cardLogoSrc: string = "/assets/img/card/sber-logo.png";
  private paymentSystemLogoSrc: Map<PaymentSystem, string> = new Map<PaymentSystem, string>([
    [PaymentSystem.MASTERCARD, "/assets/img/card/master-logo.png"],
    [PaymentSystem.VISA, "/assets/img/card/visa-logo.png"],
    [PaymentSystem.MIR, "/assets/img/card/mir-logo.png"]
  ]);
  private cardTypeBackgroundSrc: Map<CardType, string> = new Map<CardType, string>([
    [CardType.CREDIT, "/assets/img/card/background/background-credit-card.jpg"],
    [CardType.DEBUT, "/assets/img/card/background/background-debit-card.jpg"]
  ]);
  private textColors: Map<CardType, string> = new Map<CardType, string>([
    [CardType.CREDIT, "gradient-credit-text"],
    [CardType.DEBUT, "gradient-debut-text"]
  ]);

  bankAccountEntry: Array<BankAccount>;
  totalBankAccountRecords!: number;
  currentBankAccountPageNumber: number = 1;

  constructor(private cardService: CardService,
              private activatedRoute: ActivatedRoute,
              private tokenService: TokenStorageService,
              private router: Router,
              private matDialog: MatDialog,
              private bankAccountService: BankAccountService) {
    this.cardsEntry = new Array<Card>();
    this.bankAccountEntry = new Array<BankAccount>();
    this.showCards();
    this.showBankAccounts();
  }

  ngOnInit(): void {
  }

  public validatePinCode(cardId: number): void {
    this.matDialog.open(PinCodeDialogComponent, {data: cardId});
  }

  public openChoseBankAccountOperationDialog(bankAccount: BankAccount): void {
    this.matDialog.open(ChoseBankAccountOperationDialogComponent, {data: bankAccount});
  }

  public showCards(): void {
    console.log(this.tokenService.getUserId(), " tokenService");
    this.cardService.showCards(Number(this.tokenService.getUserId())).subscribe(data => {
      this.cardsEntry = data;
      this.totalCardRecords = this.cardsEntry.length;
    });
  }

  public showBankAccounts(): void {
    this.bankAccountService.findAllBankAccountsByUserId(Number(this.tokenService.getUserId())).subscribe((data: BankAccount[]) => {
      this.bankAccountEntry = data;
      this.totalBankAccountRecords = this.bankAccountEntry.length;
    })
  }

  public isPurseIsEmpty(): boolean {
    return this.cardsEntry.length == 0 && this.bankAccountEntry.length == 0;
  }

  public getPaymentSystemSrc(paymentSystem: PaymentSystem): string {
    return this.paymentSystemLogoSrc.get(paymentSystem) || "";
  }

  public getCardTypeBackgroundSrc(cardType: CardType): string {
    return this.cardTypeBackgroundSrc.get(cardType) || "";
  }

  public getTextColorsClass(cardType: CardType): string {
    return this.textColors.get(cardType) || "";
  }

}
