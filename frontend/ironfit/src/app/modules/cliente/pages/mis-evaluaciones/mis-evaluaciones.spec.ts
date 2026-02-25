import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisEvaluaciones } from './mis-evaluaciones';

describe('MisEvaluaciones', () => {
  let component: MisEvaluaciones;
  let fixture: ComponentFixture<MisEvaluaciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MisEvaluaciones]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisEvaluaciones);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
