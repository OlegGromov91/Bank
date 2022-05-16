import {Component, OnInit, ViewChild} from '@angular/core';
import {PaymentSystem} from "../../../../../model/bank/card/PaymentSystem";
import {CardType} from "../../../../../model/bank/card/CardType";
import {CardService} from "../../../services/card..service";
import {Card} from "../../../../../model/bank/card/Card";
import {ActivatedRoute, Router} from "@angular/router";
import {ChoseTransferDialogComponent} from "../../dialogs/chose-transfer-dialog/chose-transfer-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {BasicTransaction} from "../../../../../model/bank/transaction/BasicTransaction";
import {BalanceDialogComponent} from "../../dialogs/balance-dialog/balance-dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {History} from "../../../../../model/bank/history/History";
import {MatPaginator} from "@angular/material/paginator";
import {HistoryService} from "../../../services/history.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {OperationType} from "../../../../../model/bank/history/OperationType";
import {UnlockCardDialogComponent} from "../../dialogs/unlock-card-dialog/unlock-card-dialog.component";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";


@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']

})

export class CardComponent implements OnInit {

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
  private buttonColors: Map<CardType, string> = new Map<CardType, string>([
    [CardType.CREDIT, "credit-money-button"],
    [CardType.DEBUT, "debut-money-button"]
  ]);
  private dataDialog: Map<string, number> = new Map<string, number>();
  card: Card = {} as Card;
  hide = true;
  historyColumns: string[] = ['id', 'operation-type', 'card-recipient-number', 'bank-account-recipient-number', 'date-operation', 'money'];
  @ViewChild(MatPaginator) historyPaginator!: MatPaginator;
  historyData!: MatTableDataSource<History>;

  constructor(private cardService: CardService,
              private route: ActivatedRoute,
              private router: Router,
              private matDialog: MatDialog,
              private historyService: HistoryService,
              private snackBar: MatSnackBar,
              private storageService: TokenStorageService) {
    this.getCardById();
    this.getLastTwentyHistoryOperationWithCard();
  }

  ngOnInit(): void {
  }

  public getCardById(): void {
    const id = this.route.snapshot.params['cardId'];
    this.cardService.getCardById(id).subscribe((data: Card) => {
      this.card = data;
    });
  }

  public choseToGoTransfer(cardId: number, userId: number) {
    this.dataDialog.set("cardId", cardId);
    this.dataDialog.set("userId", userId);
    this.matDialog.open(ChoseTransferDialogComponent, {data: this.dataDialog});
  }

  public openIncreaseBalanceDialog() {
    const cardId = this.route.snapshot.params['cardId'];
    this.matDialog.open(BalanceDialogComponent, {data: cardId}).afterClosed().toPromise().then((data: BasicTransaction) => {
      this.card = data.card;
      this.snackBar.open("Баланс пополнен на " + data.money + " " + data.card.currencyType, "OK", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 3000,
        panelClass: ['success']
      })
    });
  }

  public openCardUnlockDialog() {
    this.dataDialog.set("cardId", Number(this.route.snapshot.params['cardId']));
    this.dataDialog.set("userId", Number(this.storageService.getUserId()));
    this.matDialog.open(UnlockCardDialogComponent, {data: this.dataDialog});
  }

  public showCardCurrencyType(history: History): string {
    return (history.operationType === OperationType.UNLOCK_CARD) ? "" : history.card.currencyType;
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

  public getButtonColorsClass(cardType: CardType): string {
    return this.buttonColors.get(cardType) || "";
  }

  public choseColumnClassColor(condition: boolean): string {
    return (condition) ? "" : "date-alert-column"
  }

  private getLastTwentyHistoryOperationWithCard() {
    this.historyService.getLastTwentyHistoryOperationWithCard(this.route.snapshot.params['cardId']).subscribe((data) => {
      this.historyData = new MatTableDataSource<History>(data);
      this.historyData.paginator = this.historyPaginator;
    })
  }

}
