import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './component/user-list/user-list.component';
import { UserFormComponent } from './component/user-form/user-form.component';

// the Routes array instructs the router which component to display
// when a user clicks a link or specifies a URL into the browser address bar.
// Path –  a string that matches the URL in the browser address bar
// Component – the component to create when the route is active (navigated)
// If the user clicks the List Users button, which links to the /users path,
// or enters the URL in the browser address bar,
// the router will render the UserListComponent component's template file in the <router-outlet> placeholder.
const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: "adduser", component: UserFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
