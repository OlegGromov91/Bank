import {BasicTransaction} from "./BasicTransaction";
import {BanksCommission} from "./BanksCommission";

export interface SendingTransaction extends BasicTransaction{

  cardRecipientNumber: string;
  commissionBankType: BanksCommission;
}
