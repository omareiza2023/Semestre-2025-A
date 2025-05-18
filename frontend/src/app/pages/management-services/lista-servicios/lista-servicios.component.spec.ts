import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ListaServiciosComponent } from './lista-servicios.component';

describe('ListaServiciosComponent', () => {
  let component: ListaServiciosComponent;
  let fixture: ComponentFixture<ListaServiciosComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [ListaServiciosComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ListaServiciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
