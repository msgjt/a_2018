import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BugsViewComponent } from './bugs-view.component';

describe('BugsViewComponent', () => {
  let component: BugsViewComponent;
  let fixture: ComponentFixture<BugsViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BugsViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BugsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
