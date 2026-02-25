import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Asistencias } from './asistencias';

describe('Asistencias', () => {
  let component: Asistencias;
  let fixture: ComponentFixture<Asistencias>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [Asistencias]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Asistencias);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
