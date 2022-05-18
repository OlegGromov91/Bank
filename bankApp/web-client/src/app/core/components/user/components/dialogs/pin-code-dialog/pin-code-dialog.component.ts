import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {TokenStorageService} from "../../../../../../authorization/services/token-storage.service";
import {Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {CardService} from "../../../services/card..service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-pin-code-dialog',
  templateUrl: './pin-code-dialog.component.html',
  styleUrls: ['./pin-code-dialog.component.scss']
})
export class PinCodeDialogComponent implements OnInit {
  hide: boolean = true;

  constructor(@Inject(MAT_DIALOG_DATA) private data: number,
              private tokenService: TokenStorageService,
              private router: Router,
              private cardService: CardService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
  }

  checkPinCodeForm = this.formBuilder.group({
    pinCode: ['', [Validators.required, Validators.pattern('\\d{4}')]]
  });

  public checkCardPinCode(): void {
    this.cardService.validatePinCode(this.data, this.checkPinCodeForm.value).toPromise().then(() => {
        this.router.navigate(['user/' + this.tokenService.getUserId() + `/card/${this.data}`]);
        this.snackBar.open("Успешно", "OK", {
          horizontalPosition: "end",
          verticalPosition: "top",
          duration: 3000,
          panelClass: ['success']
        })
      }
    ).catch((err: HttpErrorResponse) =>
      this.snackBar.open(JSON.stringify(err.error), "OK", {
        horizontalPosition: "end",
        verticalPosition: "top",
        duration: 3000,
        panelClass: ['error']
      })
    )
  }
}
