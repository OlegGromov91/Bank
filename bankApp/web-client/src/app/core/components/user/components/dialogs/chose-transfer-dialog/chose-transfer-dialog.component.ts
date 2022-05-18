import {Component, Inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {MAT_DIALOG_DATA, MatDialog} from "@angular/material/dialog";
import {TransferWithinSystemByCardDialogComponent} from "../transfer-within-system-by-card-dialog/transfer-within-system-by-card-dialog.component";
import {TransferOutsideSystemDialogComponent} from "../transfer-outside-system-dialog/transfer-outside-system-dialog.component";

@Component({
  selector: 'app-chose-transfer-dialog',
  templateUrl: './chose-transfer-dialog.component.html',
  styleUrls: ['./chose-transfer-dialog.component.scss']
})
export class ChoseTransferDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) private data: Map<string, number>,
              private tokenService: TokenStorageService,
              private router: Router,
              private matDialog: MatDialog) {
  }


  ngOnInit(): void {
  }

  public choseTransferWithinSystem(): void {
    this.openTransferWithinSystemDialog();
  }

  public choseTransferOutsideSystem(): void {
    this.openTransferOutsideSystemDialog();
  }

  private openTransferWithinSystemDialog() {
    this.matDialog.open(TransferWithinSystemByCardDialogComponent, {data: this.data});
  }

  private openTransferOutsideSystemDialog() {
    this.matDialog.open(TransferOutsideSystemDialogComponent, {data: this.data});
  }

}
