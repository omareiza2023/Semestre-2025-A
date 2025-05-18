import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CrearServiciosComponent } from './crear-servicios.component';

describe('CrearServiciosComponent', () => {
  let component: CrearServiciosComponent;
  let fixture: ComponentFixture<CrearServiciosComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [CrearServiciosComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CrearServiciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
