import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MyAssignmentsComponent } from './my-assignments.component';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextarea } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';

describe('MyAssignmentsComponent', () => {
  let component: MyAssignmentsComponent;
  let fixture: ComponentFixture<MyAssignmentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MyAssignmentsComponent,
        FormsModule,
        TableModule,
        InputTextModule,
        DropdownModule,
        ButtonModule,
        CheckboxModule,
        InputTextarea,
        InputNumberModule,
        CardModule,
        DividerModule,
        NoopAnimationsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MyAssignmentsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with default values', () => {
    expect(component.borrowerName).toBe('');
    expect(component.borrowerUen).toBe('');
    expect(component.borrowerFiscalYearEnd).toBe('');
    expect(component.monthlyDueDays).toBe(0);
    expect(component.quarterlyDueDays).toBe(0);
    expect(component.semiAnnualDueDays).toBe(0);
    expect(component.annualDueDays).toBe(0);
    expect(component.annualTargetDefaultLogic).toBe(false);
    expect(component.restrictedAccess).toBe('N/A');
  });

  it('should have correct fiscal year options', () => {
    const expectedOptions = [
      { label: 'Borrower Fiscal Year End', value: '' },
      { label: 'FY31', value: 'FY31' },
      { label: 'FY30', value: 'FY30' },
      { label: 'FY29', value: 'FY29' }
    ];
    expect(component.fiscalYearOptions).toEqual(expectedOptions);
  });

  it('should have correct pass/fail options', () => {
    const expectedOptions = [
      { label: 'Pass', value: 'Pass' },
      { label: 'Fail', value: 'Fail' },
      { label: 'Pending', value: 'Pending' }
    ];
    expect(component.passFailOptions).toEqual(expectedOptions);
  });

  it('should load mock data on init', () => {
    component.ngOnInit();
    
    expect(component.favoriteBorrowers.length).toBe(2);
    expect(component.assignments.length).toBe(2);
    
    expect(component.favoriteBorrowers[0].name).toBe('DB / Transamerica Funding Ventures');
    expect(component.assignments[0].uen).toBe('10002/78');
    expect(component.assignments[0].borrowerName).toBe('Transamerica Funding Ventures');
  });

  it('should reset form on new search', () => {
    component.borrowerName = 'Test';
    component.borrowerUen = 'Test';
    component.borrowerFiscalYearEnd = 'FY31';
    component.monthlyDueDays = 30;
    component.quarterlyDueDays = 90;
    component.semiAnnualDueDays = 180;
    component.annualDueDays = 365;
    component.annualTargetDefaultLogic = true;
    component.restrictedAccess = 'Yes';

    component.onNewSearch();

    expect(component.borrowerName).toBe('');
    expect(component.borrowerUen).toBe('');
    expect(component.borrowerFiscalYearEnd).toBe('');
    expect(component.monthlyDueDays).toBe(0);
    expect(component.quarterlyDueDays).toBe(0);
    expect(component.semiAnnualDueDays).toBe(0);
    expect(component.annualDueDays).toBe(0);
    expect(component.annualTargetDefaultLogic).toBe(false);
    expect(component.restrictedAccess).toBe('N/A');
  });

  it('should handle audit action', () => {
    spyOn(console, 'log');
    const mockAssignment = component.assignments[0];
    
    component.onAudit(mockAssignment);
    
    expect(console.log).toHaveBeenCalledWith('Auditing assignment:', mockAssignment);
  });

  it('should handle view action', () => {
    spyOn(console, 'log');
    const mockAssignment = component.assignments[0];
    
    component.onView(mockAssignment);
    
    expect(console.log).toHaveBeenCalledWith('Viewing assignment:', mockAssignment);
  });

  it('should handle edit action', () => {
    spyOn(console, 'log');
    const mockAssignment = component.assignments[0];
    
    component.onEdit(mockAssignment);
    
    expect(console.log).toHaveBeenCalledWith('Editing assignment:', mockAssignment);
  });

  it('should return correct result class for pass condition', () => {
    const result = component.getResultClass(1.5, 1.25);
    expect(result).toBe('pass');
  });

  it('should return correct result class for warning condition', () => {
    const result = component.getResultClass(1.2, 1.25);
    expect(result).toBe('warning');
  });

  it('should return correct result class for fail condition', () => {
    const result = component.getResultClass(1.0, 1.25);
    expect(result).toBe('fail');
  });

  it('should return correct status class for Pass', () => {
    const statusClass = component.getStatusClass('Pass');
    expect(statusClass).toBe('status-pass');
  });

  it('should return correct status class for Fail', () => {
    const statusClass = component.getStatusClass('Fail');
    expect(statusClass).toBe('status-fail');
  });

  it('should return correct status class for Pending', () => {
    const statusClass = component.getStatusClass('Pending');
    expect(statusClass).toBe('status-pending');
  });

  it('should return empty string for unknown status', () => {
    const statusClass = component.getStatusClass('Unknown');
    expect(statusClass).toBe('');
  });

  it('should return correct status icon for Pass', () => {
    const icon = component.getStatusIcon('Pass');
    expect(icon).toBe('pi pi-check-circle');
  });

  it('should return correct status icon for Fail', () => {
    const icon = component.getStatusIcon('Fail');
    expect(icon).toBe('pi pi-times-circle');
  });

  it('should return correct status icon for Pending', () => {
    const icon = component.getStatusIcon('Pending');
    expect(icon).toBe('pi pi-clock');
  });

  it('should return default icon for unknown status', () => {
    const icon = component.getStatusIcon('Unknown');
    expect(icon).toBe('pi pi-question-circle');
  });

  it('should handle boundary values for result calculations', () => {
    expect(component.getResultClass(1.375, 1.25)).toBe('pass');
    expect(component.getResultClass(1.1249, 1.25)).toBe('warning');
    expect(component.getResultClass(1.125, 1.25)).toBe('warning');
    expect(component.getResultClass(1.1249, 1.25)).toBe('warning');
    expect(component.getResultClass(0.5, 1.25)).toBe('fail');
  });

  it('should handle zero and negative values', () => {
    expect(component.getResultClass(0, 1.25)).toBe('fail');
    expect(component.getResultClass(-1, 1.25)).toBe('fail');
    expect(component.getResultClass(1.5, 0)).toBe('pass');
  });

  it('should render component without errors', () => {
    fixture.detectChanges();
    expect(fixture.nativeElement).toBeTruthy();
  });
});
