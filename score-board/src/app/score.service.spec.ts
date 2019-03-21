import {inject, TestBed} from '@angular/core/testing';

import {ScoreService} from './score.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('ScoreService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ScoreService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', inject([ScoreService], (service: ScoreService) => {
    expect(service).toBeTruthy();
  }));
});
