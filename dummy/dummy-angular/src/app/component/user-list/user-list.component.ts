import {Component, OnInit} from '@angular/core';

import { User } from '../../model/user';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'user-list', // the HTML selector used to bind the component to the HTML template file
  templateUrl: 'user-list.component.html'
})

export class UserListComponent implements OnInit {
  users?: User[];

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }
}
