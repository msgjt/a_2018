import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailBugComponent } from './detail-bug.component';

describe('DetailBugComponent', () => {
  let component: DetailBugComponent;
  let fixture: ComponentFixture<DetailBugComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailBugComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailBugComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
