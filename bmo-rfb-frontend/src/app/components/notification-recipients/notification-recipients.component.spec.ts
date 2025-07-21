import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NotificationRecipientsComponent } from './notification-recipients.component';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { AutoCompleteModule } from 'primeng/autocomplete';

describe('NotificationRecipientsComponent', () => {
  let component: NotificationRecipientsComponent;
  let fixture: ComponentFixture<NotificationRecipientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        NotificationRecipientsComponent,
        FormsModule,
        InputTextModule,
        DropdownModule,
        ButtonModule,
        CheckboxModule,
        InputNumberModule,
        CardModule,
        AutoCompleteModule,
        NoopAnimationsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(NotificationRecipientsComponent);
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
    expect(component.receivePassNotifications).toBe(true);
  });

  it('should have correct fiscal year options', () => {
    const expectedOptions = [
      { label: 'FY31', value: 'FY31' },
      { label: 'FY30', value: 'FY30' },
      { label: 'FY29', value: 'FY29' }
    ];
    expect(component.fiscalYearOptions).toEqual(expectedOptions);
  });

  it('should load available recipients on init', () => {
    component.ngOnInit();
    
    expect(component.availableRecipients.length).toBe(8);
    expect(component.recipient1?.name).toBe('John Smith');
    expect(component.recipient2?.name).toBe('Sarah Johnson');
    expect(component.recipient3?.name).toBe('Michael Brown');
  });

  it('should search recipients by name', () => {
    component.ngOnInit();
    const mockEvent = { query: 'john' };
    
    component.searchRecipients(mockEvent);
    
    expect(component.filteredRecipients.length).toBe(2);
    expect(component.filteredRecipients[0].name).toBe('John Smith');
    expect(component.filteredRecipients[1].name).toBe('Sarah Johnson');
  });

  it('should search recipients by email', () => {
    component.ngOnInit();
    const mockEvent = { query: 'bmo.com' };
    
    component.searchRecipients(mockEvent);
    
    expect(component.filteredRecipients.length).toBe(8);
  });

  it('should search recipients by department', () => {
    component.ngOnInit();
    const mockEvent = { query: 'risk' };
    
    component.searchRecipients(mockEvent);
    
    expect(component.filteredRecipients.length).toBe(2);
    expect(component.filteredRecipients.every(r => r.department === 'Risk Management')).toBe(true);
  });

  it('should handle case insensitive search', () => {
    component.ngOnInit();
    const mockEvent = { query: 'JOHN' };
    
    component.searchRecipients(mockEvent);
    
    expect(component.filteredRecipients.length).toBe(2);
  });

  it('should handle save action', () => {
    spyOn(console, 'log');
    component.ngOnInit();
    
    component.onSave();
    
    expect(console.log).toHaveBeenCalledWith('Saving notification recipients:', {
      recipients: [component.recipient1, component.recipient2, component.recipient3],
      receivePassNotifications: component.receivePassNotifications
    });
  });

  it('should handle audit action', () => {
    spyOn(console, 'log');
    
    component.onAudit();
    
    expect(console.log).toHaveBeenCalledWith('Opening audit view for notification recipients');
  });

  it('should get recipient display correctly', () => {
    const recipient = {
      id: '1',
      name: 'John Smith',
      email: 'john.smith@bmo.com',
      department: 'Risk Management'
    };
    
    const display = component.getRecipientDisplay(recipient);
    expect(display).toBe('John Smith (john.smith@bmo.com)');
  });

  it('should handle null recipient display', () => {
    const display = component.getRecipientDisplay(null as any);
    expect(display).toBe('');
  });

  it('should count active recipients correctly', () => {
    component.ngOnInit();
    
    const count = component.getActiveRecipientsCount();
    expect(count).toBe(3);
    
    component.recipient2 = null;
    const newCount = component.getActiveRecipientsCount();
    expect(newCount).toBe(2);
  });

  it('should clear recipient 1', () => {
    component.ngOnInit();
    expect(component.recipient1).not.toBeNull();
    
    component.clearRecipient(1);
    expect(component.recipient1).toBeNull();
  });

  it('should clear recipient 2', () => {
    component.ngOnInit();
    expect(component.recipient2).not.toBeNull();
    
    component.clearRecipient(2);
    expect(component.recipient2).toBeNull();
  });

  it('should clear recipient 3', () => {
    component.ngOnInit();
    expect(component.recipient3).not.toBeNull();
    
    component.clearRecipient(3);
    expect(component.recipient3).toBeNull();
  });

  it('should handle invalid recipient number', () => {
    component.ngOnInit();
    const originalRecipients = [component.recipient1, component.recipient2, component.recipient3];
    
    component.clearRecipient(99);
    
    expect(component.recipient1).toBe(originalRecipients[0]);
    expect(component.recipient2).toBe(originalRecipients[1]);
    expect(component.recipient3).toBe(originalRecipients[2]);
  });

  it('should handle test notifications action', () => {
    spyOn(console, 'log');
    
    component.onTestNotifications();
    
    expect(console.log).toHaveBeenCalledWith('Testing notifications for all recipients');
  });

  it('should handle reset action', () => {
    spyOn(console, 'log');
    component.ngOnInit();
    component.receivePassNotifications = true;
    
    component.onReset();
    
    expect(component.recipient1).toBeNull();
    expect(component.recipient2).toBeNull();
    expect(component.recipient3).toBeNull();
    expect(component.receivePassNotifications).toBe(false);
    expect(console.log).toHaveBeenCalledWith('Reset all notification settings');
  });

  it('should get notification frequency for all events', () => {
    component.receivePassNotifications = true;
    
    const frequency = component.getNotificationFrequency();
    expect(frequency).toBe('All Events');
  });

  it('should get notification frequency for failures only', () => {
    component.receivePassNotifications = false;
    
    const frequency = component.getNotificationFrequency();
    expect(frequency).toBe('Failures Only');
  });

  it('should handle send test email action', () => {
    spyOn(console, 'log');
    const mockRecipient = { name: 'Test User', email: 'test@bmo.com' };
    
    component.sendTestEmail(mockRecipient);
    
    expect(console.log).toHaveBeenCalledWith('Sending test email to:', mockRecipient);
  });

  it('should handle empty search query', () => {
    component.ngOnInit();
    const mockEvent = { query: '' };
    
    component.searchRecipients(mockEvent);
    
    expect(component.filteredRecipients.length).toBe(0);
  });

  it('should handle search with no matches', () => {
    component.ngOnInit();
    const mockEvent = { query: 'nonexistent' };
    
    component.searchRecipients(mockEvent);
    
    expect(component.filteredRecipients.length).toBe(0);
  });

  it('should save with null recipients filtered out', () => {
    spyOn(console, 'log');
    component.ngOnInit();
    component.recipient2 = null;
    
    component.onSave();
    
    expect(console.log).toHaveBeenCalledWith('Saving notification recipients:', {
      recipients: [component.recipient1, component.recipient3],
      receivePassNotifications: component.receivePassNotifications
    });
  });

  it('should render component without errors', () => {
    fixture.detectChanges();
    expect(fixture.nativeElement).toBeTruthy();
  });
});
