import {Component, OnInit} from '@angular/core';
import {CardService} from "../../../services/card..service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpErrorResponse} from "@angular/common/http";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-create-card-dialog',
  templateUrl: './create-card-dialog.component.html',
  styleUrls: ['./create-card-dialog.component.scss']
})
export class CreateCardDialogComponent implements OnInit {
  hide = true;

  constructor(private cardService: CardService,
              private formBuilder: FormBuilder,
              private tokenService: TokenStorageService,
              private snackBar: MatSnackBar,
              private dialogRef: MatDialogRef<CreateCardDialogComponent>) {
  }

  cardCreateForm = this.formBuilder.group({
    pinCode: ['', [Validators.required, Validators.pattern('\\d{4}')]],
    paymentSystem: ['', [Validators.required]],
    currencyType: ['', [Validators.required]],
    cardType: ['', [Validators.required]]
  });

  ngOnInit(): void {
  }

  createNewCard(addCardForm: FormGroup) {
    this.cardService.createNewCard(Number(this.tokenService.getUserId()), addCardForm.value).toPromise().then((data) => {
      this.snackBar.open("Карта с номером " + data!.cardNumber + " выпущена", "OK", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 3000,
        panelClass: ['success']
      }).afterOpened().subscribe(() => {
        this.dialogRef.close();
      })
    }).catch((err: HttpErrorResponse) => {
      this.snackBar.open(JSON.stringify(err.error), "OK", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 5000,
        panelClass: ['error']
      })
    });
  }


}
