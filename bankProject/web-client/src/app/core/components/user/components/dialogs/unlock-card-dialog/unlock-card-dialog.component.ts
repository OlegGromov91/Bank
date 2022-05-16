import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {FormBuilder, Validators} from "@angular/forms";
import {CardService} from "../../../services/card..service";
import {HttpErrorResponse} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UnlockRequestService} from "../../../../operator/services/unlock-request.service";

@Component({
  selector: 'app-unlock-card-dialog',
  templateUrl: './unlock-card-dialog.component.html',
  styleUrls: ['./unlock-card-dialog.component.scss']
})
export class UnlockCardDialogComponent implements OnInit {
  hide: boolean = true;

  constructor(@Inject(MAT_DIALOG_DATA) private data: Map<string, number>,
              private formBuilder: FormBuilder,
              private cardService: CardService,
              private snackBar: MatSnackBar,
              private unlockRequestService: UnlockRequestService) {
  }

  unlockCardForm = this.formBuilder.group({
    cardId: [this.data.get('cardId')],
    userId: [this.data.get('userId')],
    secretWord: ['', [Validators.required]]
  });

  ngOnInit(): void {
  }

  public sendRequestForUnlockCard(): void {
    this.unlockRequestService.sendRequestForCardUnlock(this.data.get('cardId')!, this.unlockCardForm.value).toPromise().then(
      () =>
        this.snackBar.open("Заявка принята", "", {
          horizontalPosition: "end",
          verticalPosition: "top",
          duration: 3000,
          panelClass: ['success']
        })
    ).catch((err: HttpErrorResponse) => {
      this.snackBar.open(JSON.stringify(err.error), "", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 5000,
        panelClass: ['error']
      })
    });
  }

}
