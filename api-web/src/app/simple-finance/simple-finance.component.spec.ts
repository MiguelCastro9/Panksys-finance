import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimpleFinanceComponent } from './simple-finance.component';

describe('SimpleFinanceComponent', () => {
  let component: SimpleFinanceComponent;
  let fixture: ComponentFixture<SimpleFinanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimpleFinanceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SimpleFinanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
