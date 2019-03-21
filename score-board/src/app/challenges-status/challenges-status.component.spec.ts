import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChallengesStatusComponent} from './challenges-status.component';

describe('ChallengesStatusComponent', () => {
  let component: ChallengesStatusComponent;
  let fixture: ComponentFixture<ChallengesStatusComponent>;

  beforeEach(async(() => {
    TestBed
      .configureTestingModule({
        declarations: [ChallengesStatusComponent]
      })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChallengesStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
