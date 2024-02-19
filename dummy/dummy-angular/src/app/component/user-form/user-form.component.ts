import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import {UserSaved} from "../../model/user-saved";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html'//,
  // styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {

  user: UserSaved = {}

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService) {
  }

  onSubmit() {
    this.userService.save(this.user).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }
}
