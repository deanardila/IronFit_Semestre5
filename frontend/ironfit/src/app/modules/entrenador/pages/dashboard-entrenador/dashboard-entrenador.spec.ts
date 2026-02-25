import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardEntrenador } from './dashboard-entrenador';

describe('DashboardEntrenador', () => {
  let component: DashboardEntrenador;
  let fixture: ComponentFixture<DashboardEntrenador>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardEntrenador]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardEntrenador);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
