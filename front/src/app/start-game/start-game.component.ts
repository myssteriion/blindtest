import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-start-game',
  templateUrl: './start-game.component.html',
  styleUrls: ['./start-game.component.css']
})
export class StartGameComponent implements OnInit {

  public gameFullySet: boolean = false;
  constructor(private _activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

  public onClose() {
    this._activeModal.close('Close click')
  }
}
