import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanesRutinas } from './planes-rutinas';

describe('PlanesRutinas', () => {
  let component: PlanesRutinas;
  let fixture: ComponentFixture<PlanesRutinas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlanesRutinas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanesRutinas);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
