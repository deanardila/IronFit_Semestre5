import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisPlanes } from './mis-planes';

describe('MisPlanes', () => {
  let component: MisPlanes;
  let fixture: ComponentFixture<MisPlanes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MisPlanes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisPlanes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
