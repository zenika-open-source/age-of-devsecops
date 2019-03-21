import {async, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {MockComponent} from 'ng-mocks';
import {LeaderBoardComponent} from './leader-board/leader-board.component';
import {PlanetsComponent} from './planets/planets.component';
import {ScoreService} from './score.service';
import {BehaviorSubject} from 'rxjs/internal/BehaviorSubject';
import {ChallengesStatusComponent} from './challenges-status/challenges-status.component';
import {ChallengeStatusService} from './challenge-status.service';
import {EMPTY} from "rxjs";

describe('AppComponent', () => {
  let app;
  let fixture;

  beforeEach(async(() => {
    return TestBed
      .configureTestingModule({
        declarations: [
          AppComponent,
          MockComponent(PlanetsComponent),
          MockComponent(LeaderBoardComponent),
          MockComponent(ChallengesStatusComponent),
        ],
        providers: [
          {
            provide: ScoreService,
            useValue: {
              getAll: () => new BehaviorSubject([])
            }
          },
          {
            provide: ChallengeStatusService,
            useValue: {
              getAll: () => new BehaviorSubject([])
            }
          },
          {
            provide: 'interval', useValue: () => EMPTY
          }
        ],
      })
      .compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(AppComponent);
        app = fixture.debugElement.componentInstance;
        fixture.detectChanges();
      });
  }));

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

});
