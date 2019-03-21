import {Injectable} from '@angular/core';
import {Observable, of, timer} from 'rxjs';
import {catchError, flatMap, tap, timeout} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {PlayerChallengeStatuses} from './player-challenge-statuses';

@Injectable({
  providedIn: 'root'
})
export class ChallengeStatusService {

  private challengeStatuses: PlayerChallengeStatuses[] = [];

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<PlayerChallengeStatuses[]> {
    const observable: Observable<PlayerChallengeStatuses[]> = this.http.get<PlayerChallengeStatuses[]>('/api/challenges/status').pipe(
      timeout(4_000),
      tap(statuses => this.challengeStatuses = statuses),
      catchError(() => of(this.challengeStatuses))
    );

    return timer(0, 5_000).pipe(
      flatMap(() => observable)
    );
  }
}
