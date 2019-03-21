import {Injectable} from '@angular/core';
import {Observable, of, timer} from 'rxjs';
import PlayerScore from './player-score';
import {catchError, flatMap, tap, timeout} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScoreService {

  private scores: PlayerScore[] = [];

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<PlayerScore[]> {
    const observable: Observable<PlayerScore[]> = this.http.get<PlayerScore[]>('/api/scores').pipe(
      timeout(4_000),
      tap(scores => this.scores = scores),
      catchError(() => of(this.scores))
    );

    return timer(0, 5_000).pipe(
      flatMap(() => observable)
    );
  }
}
