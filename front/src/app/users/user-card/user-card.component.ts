import { Component, OnInit } from '@angular/core';
import { User } from '../interface/interface.user'

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent implements OnInit {

  public user: User = {
    name: "Anaïs",
    playedGames: 20,
    listenedMusics: 280,
    foundMusics: 127,
    avatar: "path"
  }

  constructor() { }

  ngOnInit() {
  }

}
