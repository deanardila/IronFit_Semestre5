import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisClientes } from './mis-clientes';

describe('MisClientes', () => {
  let component: MisClientes;
  let fixture: ComponentFixture<MisClientes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MisClientes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisClientes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
