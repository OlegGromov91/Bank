import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./authorization/component/login/login.component";
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {
    path: 'user/:id',
    canActivate: [AuthGuard],
    canDeactivate: [AuthGuard],
    loadChildren: () => import('./core/components/user/user.module').then((m) => m.UserModule)
  },
  {
    path: 'operator/:id',
    loadChildren: () => import('./core/components/operator/operator.module').then((m) => m.OperatorModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
