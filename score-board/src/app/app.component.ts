import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import PlayerScore from './player-score';
import {Subject} from 'rxjs';
import {ScoreService} from './score.service';
import {takeUntil} from 'rxjs/operators';
import {ChallengeStatusService} from './challenge-status.service';
import {PlayerChallengeStatuses} from './player-challenge-statuses';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  playerScores: Array<PlayerScore> = [];
  playerChallengesStatus: Array<PlayerChallengeStatuses> = [];
  showChallenges = false;

  private ngUnsubscribe$: Subject<any> = new Subject();

  constructor(
    private scoreService: ScoreService,
    private challengeStatusService: ChallengeStatusService,
    @Inject('interval') private interval,
  ) {
  }

  ngOnInit(): void {
    this.scoreService.getAll().pipe(takeUntil(this.ngUnsubscribe$)).subscribe(
      playerScores => this.playerScores = playerScores
    );
    // this.challengeStatusService.getAll().pipe(takeUntil(this.ngUnsubscribe$)).subscribe(
    //   playerChallengesStatus => this.playerChallengesStatus = playerChallengesStatus
    // );
    // this.interval(15_000, 15_000).pipe(takeUntil(this.ngUnsubscribe$)).subscribe(() => {
    //   this.showChallenges = !this.showChallenges;
    // })
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe$.next();
    this.ngUnsubscribe$.complete();
  }
}

