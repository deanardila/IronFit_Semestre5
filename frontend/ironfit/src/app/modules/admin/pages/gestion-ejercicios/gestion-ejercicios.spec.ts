import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionEjercicios } from './gestion-ejercicios';

describe('GestionEjercicios', () => {
  let component: GestionEjercicios;
  let fixture: ComponentFixture<GestionEjercicios>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GestionEjercicios]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionEjercicios);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
