import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleContentComponent } from './role-content.component';

describe('RoleContentComponent', () => {
  let component: RoleContentComponent;
  let fixture: ComponentFixture<RoleContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoleContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
