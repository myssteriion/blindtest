import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StartGameComponent } from './start-game/start-game.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'blindtest';

  constructor(private _modalService: NgbModal) { }

  public ngOnInit() {
    this._modalService.open(StartGameComponent, { size: 'lg', keyboard: false, backdrop: 'static', centered: true });
  }
}
