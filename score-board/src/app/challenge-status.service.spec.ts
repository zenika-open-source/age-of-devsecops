import { TestBed, inject } from '@angular/core/testing';

import { ChallengeStatusService } from './challenge-status.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('ChallengeStatusServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChallengeStatusService],
      imports: [
        HttpClientTestingModule
      ]
    });
  });

  it('should be created', inject([ChallengeStatusService], (service: ChallengeStatusService) => {
    expect(service).toBeTruthy();
  }));
});
