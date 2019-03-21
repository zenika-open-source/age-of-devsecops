import {Component, Input} from '@angular/core';
import {PlayerChallengeStatuses} from '../player-challenge-statuses';
import {toSnakeCase} from '../text-utils';

@Component({
  selector: 'app-challenges-status',
  templateUrl: './challenges-status.component.html',
  styleUrls: ['./challenges-status.component.scss']
})
export class ChallengesStatusComponent {

  public playerRows: Array<PlayerRow>;
  public challengeNames: Array<string>;

  @Input()
  set playerChallengesStatus(values: Array<PlayerChallengeStatuses>) {
    if (values == null || values.length === 0) {
      this.playerRows = [];
      this.challengeNames = [];
      return;
    }

    this.playerRows = values
      .map(value => {
        const challengesStatuses = {};
        value.challengeStatuses.forEach(challengeStatus => challengesStatuses[challengeStatus.challenge.name] = challengeStatus.completed);
        return {
          playerId: value.playerId,
          score: value.score,
          challengesStatuses,
          publicFlags: value.flagStatuses.publicPresent,
          totalPublicFlags: value.flagStatuses.publicTotal,
          secretFlags: value.flagStatuses.secretPresent
        };
      })
      .sort((a, b) => b.score - a.score);

    this.challengeNames = values[0].challengeStatuses
      .map(challengeStatus => challengeStatus.challenge.name);
  }

  constructor() {
  }

  toSnakeCase(s: string) {
    return toSnakeCase(s);
  }
}

interface PlayerRow {
  playerId: string;
  challengesStatuses: ChallengeStatuses;
  publicFlags: number;
  totalPublicFlags: number;
  secretFlags: number;
}

interface ChallengeStatuses {
  [key: string]: boolean;
}
