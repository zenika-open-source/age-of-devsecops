import {Component, Input} from '@angular/core';
import PlayerScore from '../player-score';
import {toSnakeCase} from '../text-utils';

@Component({
  selector: 'app-leader-board',
  templateUrl: './leader-board.component.html',
  styleUrls: ['./leader-board.component.scss']
})
export class LeaderBoardComponent {

  private _playerScores: Array<PlayerScore>;

  constructor() { }

  get playerScores(): Array<PlayerScore> {
    return this._playerScores;
  }

  @Input()
  set playerScores(value: Array<PlayerScore>) {
    this._playerScores = value.sort((a, b) => b.score - a.score);
  }

  toSnakeCase(s: string) {
    return toSnakeCase(s);
  }


}
