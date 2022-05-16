import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, Validators} from "@angular/forms";
import {CardService} from "../../../services/card..service";
import {Card} from "../../../../../model/bank/card/Card";

@Component({
  selector: 'app-balance-dialog',
  templateUrl: './balance-dialog.component.html',
  styleUrls: ['./balance-dialog.component.scss']
})
export class BalanceDialogComponent implements OnInit {
  currencyType!: string;
  increaseBalanceForm = this.formBuilder.group({
    money: ['', [Validators.required, Validators.pattern('^\\d*\\.?(\\d{1}|\\d{2})$'), Validators.max(5000000)]]
  });


  constructor(@Inject(MAT_DIALOG_DATA) private data: any, private formBuilder: FormBuilder, private cardService: CardService,
              private dialogRef: MatDialogRef<BalanceDialogComponent>) {
    this.checkCurrencyType();
  }

  ngOnInit(): void {
  }

  public increaseBalance(): void {
    this.cardService.increaseBalance(this.data, this.increaseBalanceForm.value).subscribe(data => {
      this.dialogRef.close(data);
    });
  }

  public close(): void {
    location.reload();
  }

  private checkCurrencyType(): void {
    this.cardService.getCardById(this.data).subscribe((data: Card) => {
      this.currencyType = data.currencyType;
    });
  }

}
