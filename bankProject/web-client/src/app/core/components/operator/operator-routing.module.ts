import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {OperatorDashboardComponent} from "./components/core/operator-dashboard/operator-dashboard.component";
import {HomeComponent} from "./components/core/home/home.component";

const routes: Routes = [
  {
    path: '', component: OperatorDashboardComponent,
    children: [
      {path: 'home', component: HomeComponent},
      {path: '', redirectTo: 'home', pathMatch: 'full'}
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OperatorRoutingModule {
}
