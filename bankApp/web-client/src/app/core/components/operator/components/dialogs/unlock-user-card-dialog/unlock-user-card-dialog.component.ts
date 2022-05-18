import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder} from "@angular/forms";
import {UnlockRequest} from "../../../../../model/security/UnlockRequest";
import {UnlockRequestService} from "../../../services/unlock-request.service";
import {HistoryService} from "../../../../user/services/history.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-unlock-user-card-dialog',
  templateUrl: './unlock-user-card-dialog.component.html',
  styleUrls: ['./unlock-user-card-dialog.component.scss']
})
export class UnlockUserCardDialogComponent implements OnInit {

  unlockRequest!: UnlockRequest;

  constructor(@Inject(MAT_DIALOG_DATA) private data: UnlockRequest,
              private formBuilder: FormBuilder,
              private unlockRequestService: UnlockRequestService,
              private dialogRef: MatDialogRef<UnlockUserCardDialogComponent>,
              private matDialog: MatDialog,
              private historyService: HistoryService,
              private snackBar: MatSnackBar) {
    this.unlockRequest = data;
  }

  ngOnInit(): void {
  }

  public unlockCard(unlockRequest: UnlockRequest): void {
    this.unlockRequestService.unlockUserCard(unlockRequest.cardId, unlockRequest).subscribe((data) => {
      this.snackBar.open("Карта разблокирована", "OK", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 3000,
        panelClass: ['success']
      });
      this.dialogRef.close();
    });
  }
}
