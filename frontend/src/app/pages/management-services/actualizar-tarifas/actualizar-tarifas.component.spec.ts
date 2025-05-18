import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ActualizarTarifasComponent } from './actualizar-tarifas.component';

describe('ActualizarTarifasComponent', () => {
  let component: ActualizarTarifasComponent;
  let fixture: ComponentFixture<ActualizarTarifasComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [ActualizarTarifasComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ActualizarTarifasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
