import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditoriaReportes } from './auditoria-reportes';

describe('AuditoriaReportes', () => {
  let component: AuditoriaReportes;
  let fixture: ComponentFixture<AuditoriaReportes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AuditoriaReportes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuditoriaReportes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
