import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExchangeRateDialogComponent } from './exchange-rate-dialog.component';

describe('ExchangeRateDialogComponent', () => {
  let component: ExchangeRateDialogComponent;
  let fixture: ComponentFixture<ExchangeRateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExchangeRateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExchangeRateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
