import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {LeaderBoardComponent} from './leader-board/leader-board.component';
import {PlanetsComponent} from './planets/planets.component';
import {HttpClientModule} from '@angular/common/http';
import * as THREE from 'three';
import {ChallengesStatusComponent} from './challenges-status/challenges-status.component';
import {timer} from "rxjs";

@NgModule({
  declarations: [
    AppComponent,
    LeaderBoardComponent,
    PlanetsComponent,
    ChallengesStatusComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    {provide: 'THREE', useValue: THREE},
    {provide: 'requestAnimationFrame', useValue: requestAnimationFrame},
    {provide: 'interval', useValue: timer}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
