import {Component, OnInit} from '@angular/core';
import {UnlockRequestService} from "../../../services/unlock-request.service";
import {Card} from "../../../../../model/bank/card/Card";
import {UnlockRequest} from "../../../../../model/security/UnlockRequest";
import {BalanceDialogComponent} from "../../../../user/components/dialogs/balance-dialog/balance-dialog.component";
import {BasicTransaction} from "../../../../../model/bank/transaction/BasicTransaction";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UnlockUserCardDialogComponent} from "../../dialogs/unlock-user-card-dialog/unlock-user-card-dialog.component";
import {UnlockRequestHistory} from "../../../../../model/bank/history/UnlockRequestHistory";
import {HistoryService} from "../../../../user/services/history.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  unlockRequestEntity: Array<UnlockRequest>;
  totalUnlockRequestRecords!: number;
  currentUnlockRequestPageNumber: number = 1;

  unlockRequestHistoryEntity: Array<UnlockRequestHistory>;
  totalUnlockRequestHistoryRecords!: number;
  currentUnlockRequestHistoryPageNumber: number = 1;


  constructor(private unlockRequestService: UnlockRequestService,
              private matDialog: MatDialog,
              private historyService: HistoryService,
  ) {
    this.unlockRequestEntity = new Array<UnlockRequest>();
    this.unlockRequestHistoryEntity = new Array<UnlockRequestHistory>();
    this.showUnlockRequests();
    this.showUnlockRequestsHistory();
  }

  ngOnInit(): void {
  }


  public activeUserCardForm(unlockRequest: UnlockRequest) {
    this.matDialog.open(UnlockUserCardDialogComponent, {data: unlockRequest}).afterClosed().toPromise().then(
      () => {
        this.showUnlockRequests();
        this.showUnlockRequestsHistory();
      });
  }


  private showUnlockRequestsHistory(): void {
    this.historyService.getUnlockRequestHistory().subscribe((data) => {
      this.unlockRequestHistoryEntity = data;
      this.totalUnlockRequestHistoryRecords = this.unlockRequestHistoryEntity.length
    });
  }


  private showUnlockRequests(): void {
    this.unlockRequestService.showUnlockRequests().subscribe((data) => {
      this.unlockRequestEntity = data;
      this.totalUnlockRequestRecords = this.unlockRequestEntity.length;
    });
  }

}
