import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { TestingResultsComponent } from './testing-results.component';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextarea } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';

describe('TestingResultsComponent', () => {
  let component: TestingResultsComponent;
  let fixture: ComponentFixture<TestingResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        TestingResultsComponent,
        FormsModule,
        TableModule,
        InputTextModule,
        DropdownModule,
        ButtonModule,
        CheckboxModule,
        InputTextarea,
        InputNumberModule,
        CardModule,
        NoopAnimationsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TestingResultsComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with default values', () => {
    expect(component.borrowerName).toBe('BMO Transamerica Funding Ventures');
    expect(component.borrowerUen).toBe('10002/84');
    expect(component.borrowerFiscalYearEnd).toBe('FY31');
    expect(component.monthlyDueDays).toBe(30);
    expect(component.quarterlyDueDaysNA).toBe(true);
    expect(component.semiAnnualDueDaysNA).toBe(true);
    expect(component.annualDueDays).toBe(365);
    expect(component.approvalStatus).toBe('Outstanding');
    expect(component.loading).toBe(false);
  });

  it('should have correct fiscal year options', () => {
    const expectedOptions = [
      { label: 'FY31', value: 'FY31' },
      { label: 'FY30', value: 'FY30' },
      { label: 'FY29', value: 'FY29' }
    ];
    expect(component.fiscalYearOptions).toEqual(expectedOptions);
  });

  it('should have correct approval status options', () => {
    const expectedOptions = [
      { label: 'Outstanding', value: 'Outstanding' },
      { label: 'Defaults', value: 'Defaults' },
      { label: 'Approved', value: 'Approved' },
      { label: 'Pending Review', value: 'Pending Review' }
    ];
    expect(component.approvalStatusOptions).toEqual(expectedOptions);
  });

  it('should have correct pass/fail options', () => {
    const expectedOptions = [
      { label: 'Pass', value: 'Pass' },
      { label: 'Fail', value: 'Fail' },
      { label: 'Pending', value: 'Pending' }
    ];
    expect(component.passFailOptions).toEqual(expectedOptions);
  });

  it('should load testing results on init', () => {
    component.ngOnInit();
    
    expect(component.testingResults.length).toBe(4);
    expect(component.filteredTestingResults.length).toBe(4);
    expect(component.testingResults[0].trackingItem).toBe('BMO SK Funded Ratio EBITDA - test');
    expect(component.testingResults[0].passFail).toBe('Pass');
  });

  it('should handle audit action', () => {
    spyOn(console, 'log');
    const mockResult = component.testingResults[0];
    
    component.onAudit(mockResult);
    
    expect(console.log).toHaveBeenCalledWith('Auditing testing result:', mockResult);
  });

  it('should handle filter change', () => {
    spyOn(console, 'log');
    component.approvalStatus = 'Approved';
    
    component.onFilterChange();
    
    expect(console.log).toHaveBeenCalledWith('Filter changed to:', 'Approved');
  });

  it('should count pass results correctly', () => {
    component.ngOnInit();
    const passCount = component.getPassCount();
    expect(passCount).toBe(4);
  });

  it('should count fail results correctly', () => {
    component.ngOnInit();
    component.testingResults[0].passFail = 'Fail';
    const failCount = component.getFailCount();
    expect(failCount).toBe(1);
  });

  it('should count pending results correctly', () => {
    component.ngOnInit();
    component.testingResults[0].passFail = 'Pending';
    const pendingCount = component.getPendingCount();
    expect(pendingCount).toBe(1);
  });

  it('should return correct result class for pass condition', () => {
    const result = component.getResultClass(2.5, 2.0);
    expect(result).toBe('pass');
  });

  it('should return correct result class for warning condition', () => {
    const result = component.getResultClass(1.9, 2.0);
    expect(result).toBe('warning');
  });

  it('should return correct result class for fail condition', () => {
    const result = component.getResultClass(1.5, 2.0);
    expect(result).toBe('fail');
  });

  it('should return correct result icon for pass condition', () => {
    const icon = component.getResultIcon(2.5, 2.0);
    expect(icon).toBe('pi pi-check');
  });

  it('should return correct result icon for warning condition', () => {
    const icon = component.getResultIcon(1.9, 2.0);
    expect(icon).toBe('pi pi-exclamation-triangle');
  });

  it('should return correct result icon for fail condition', () => {
    const icon = component.getResultIcon(1.5, 2.0);
    expect(icon).toBe('pi pi-times');
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

  it('should return correct status icon for Saved', () => {
    const icon = component.getStatusIcon('Saved');
    expect(icon).toBe('pi pi-save');
  });

  it('should return correct status icon for Approved', () => {
    const icon = component.getStatusIcon('Approved');
    expect(icon).toBe('pi pi-verified');
  });

  it('should return default icon for unknown status', () => {
    const icon = component.getStatusIcon('Unknown');
    expect(icon).toBe('pi pi-question-circle');
  });

  it('should handle view action', () => {
    spyOn(console, 'log');
    const mockResult = component.testingResults[0];
    
    component.onView(mockResult);
    
    expect(console.log).toHaveBeenCalledWith('Viewing testing result:', mockResult);
  });

  it('should handle edit action', () => {
    spyOn(console, 'log');
    const mockResult = component.testingResults[0];
    
    component.onEdit(mockResult);
    
    expect(console.log).toHaveBeenCalledWith('Editing testing result:', mockResult);
  });

  it('should handle refresh with loading state', fakeAsync(() => {
    component.ngOnInit();
    expect(component.loading).toBe(false);
    
    component.onRefresh();
    expect(component.loading).toBe(true);
    
    tick(1000);
    expect(component.loading).toBe(false);
    expect(component.testingResults.length).toBe(4);
    expect(component.filteredTestingResults.length).toBe(4);
  }));

  it('should handle export action', () => {
    spyOn(console, 'log');
    
    component.onExport();
    
    expect(console.log).toHaveBeenCalledWith('Exporting testing results');
  });

  it('should handle add new test action', () => {
    spyOn(console, 'log');
    
    component.onAddNewTest();
    
    expect(console.log).toHaveBeenCalledWith('Adding new test');
  });

  it('should clear filters correctly', () => {
    component.ngOnInit();
    component.approvalStatus = 'Approved';
    
    component.onClearFilters();
    
    expect(component.approvalStatus).toBe('');
    expect(component.filteredTestingResults.length).toBe(component.testingResults.length);
  });

  it('should handle boundary values for result calculations', () => {
    expect(component.getResultClass(2.2, 2.0)).toBe('pass');
    expect(component.getResultClass(2.0, 2.0)).toBe('warning');
    expect(component.getResultClass(1.8, 2.0)).toBe('warning');
    expect(component.getResultClass(1.79, 2.0)).toBe('fail');
  });

  it('should handle zero and negative values', () => {
    expect(component.getResultClass(0, 2.0)).toBe('fail');
    expect(component.getResultClass(-1, 2.0)).toBe('fail');
    expect(component.getResultClass(2.5, 0)).toBe('pass');
  });

  it('should render component without errors', () => {
    fixture.detectChanges();
    expect(fixture.nativeElement).toBeTruthy();
  });
});
