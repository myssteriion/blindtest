import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { StartGameComponent } from './features/start-game/start-game.component'
import { UserResourceService } from './resources/userResources'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'blindtest';

  constructor(private _modalService: NgbModal,
    private _request: UserResourceService) { }
 
  public ngOnInit() {
  }
} 
