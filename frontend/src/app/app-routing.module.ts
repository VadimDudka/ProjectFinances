import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TestComponent} from './component/test/test.component';
import {AuthGuard} from './helper/auth-guard';

const routes: Routes = [
    {
        path: '',
        redirectTo: 'content',
        pathMatch: 'full'
    },
    {
        path: 'content',
        component: TestComponent,
        canActivate: [AuthGuard]
    },
    {
        path: 'auth',
        loadChildren: 'src/app/module/auth/auth.module#AuthModule'
    },
    {
        path: '**',
        redirectTo: ''
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
