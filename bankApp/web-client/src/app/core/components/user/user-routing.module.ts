import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserDashboardComponent} from "./components/core/user-dashboard/user-dashboard.component";
import {HomeComponent} from "./components/core/home/home.component";
import {CardComponent} from "./components/bank/card/card.component";

const routes: Routes = [
  {
    path: '', component: UserDashboardComponent,
    children: [
      {path: 'home', component: HomeComponent},
      {path: 'card/:cardId', component: CardComponent},
      {path: '', redirectTo: 'home', pathMatch: 'full'}
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {
}
