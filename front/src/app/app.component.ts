import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StartGameComponent } from './start-game/start-game.component'
import { ResourceService } from './resources/resources'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'blindtest';

  constructor(private _modalService: NgbModal,
    private _request: ResourceService) { }

  public ngOnInit() {
    this._request.getAllUsers().subscribe(data => {
      console.log("data", data)
    })
    this._modalService.open(StartGameComponent, { size: 'lg', keyboard: false, backdrop: 'static', centered: true });
  }
} 
