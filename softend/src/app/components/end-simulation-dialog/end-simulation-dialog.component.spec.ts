import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EndSimulationDialogComponent } from './end-simulation-dialog.component';

describe('EndSimulationDialogComponent', () => {
  let component: EndSimulationDialogComponent;
  let fixture: ComponentFixture<EndSimulationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EndSimulationDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EndSimulationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
