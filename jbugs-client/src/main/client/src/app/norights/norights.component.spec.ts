import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NorightsComponent } from './norights.component';

describe('NorightsComponent', () => {
  let component: NorightsComponent;
  let fixture: ComponentFixture<NorightsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NorightsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NorightsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
