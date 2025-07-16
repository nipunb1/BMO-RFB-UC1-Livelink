import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { AutoCompleteModule } from 'primeng/autocomplete';

interface Recipient {
  id: string;
  name: string;
  email: string;
  department: string;
}

@Component({
  selector: 'app-notification-recipients',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    InputTextModule,
    DropdownModule,
    ButtonModule,
    CheckboxModule,
    InputNumberModule,
    CardModule,
    AutoCompleteModule
  ],
  templateUrl: './notification-recipients.component.html',
  styleUrl: './notification-recipients.component.scss'
})
export class NotificationRecipientsComponent implements OnInit {
  borrowerName: string = 'BMO Transamerica Funding Ventures';
  borrowerUen: string = '10002/84';
  borrowerFiscalYearEnd: string = 'FY31';
  monthlyDueDays: number = 30;
  quarterlyDueDaysNA: boolean = true;
  semiAnnualDueDaysNA: boolean = true;
  annualDueDays: number = 365;

  recipient1: Recipient | null = null;
  recipient2: Recipient | null = null;
  recipient3: Recipient | null = null;
  receivePassNotifications: boolean = true;

  availableRecipients: Recipient[] = [];
  filteredRecipients: Recipient[] = [];
  
  fiscalYearOptions = [
    { label: 'FY31', value: 'FY31' },
    { label: 'FY30', value: 'FY30' },
    { label: 'FY29', value: 'FY29' }
  ];

  ngOnInit() {
    this.loadAvailableRecipients();
  }

  loadAvailableRecipients() {
    this.availableRecipients = [
      {
        id: '1',
        name: 'John Smith',
        email: 'john.smith@bmo.com',
        department: 'Risk Management'
      },
      {
        id: '2',
        name: 'Sarah Johnson',
        email: 'sarah.johnson@bmo.com',
        department: 'Credit Analysis'
      },
      {
        id: '3',
        name: 'Michael Brown',
        email: 'michael.brown@bmo.com',
        department: 'Compliance'
      },
      {
        id: '4',
        name: 'Emily Davis',
        email: 'emily.davis@bmo.com',
        department: 'Portfolio Management'
      },
      {
        id: '5',
        name: 'David Wilson',
        email: 'david.wilson@bmo.com',
        department: 'Risk Management'
      },
      {
        id: '6',
        name: 'Lisa Anderson',
        email: 'lisa.anderson@bmo.com',
        department: 'Credit Analysis'
      },
      {
        id: '7',
        name: 'Robert Taylor',
        email: 'robert.taylor@bmo.com',
        department: 'Audit'
      },
      {
        id: '8',
        name: 'Jennifer Martinez',
        email: 'jennifer.martinez@bmo.com',
        department: 'Compliance'
      }
    ];

    this.recipient1 = this.availableRecipients[0];
    this.recipient2 = this.availableRecipients[1];
    this.recipient3 = this.availableRecipients[2];
  }

  searchRecipients(event: any) {
    const query = event.query.toLowerCase();
    this.filteredRecipients = this.availableRecipients.filter(recipient =>
      recipient.name.toLowerCase().includes(query) ||
      recipient.email.toLowerCase().includes(query) ||
      recipient.department.toLowerCase().includes(query)
    );
  }

  onSave() {
    const recipients = [this.recipient1, this.recipient2, this.recipient3].filter(r => r !== null);
    console.log('Saving notification recipients:', {
      recipients,
      receivePassNotifications: this.receivePassNotifications
    });
  }

  onAudit() {
    console.log('Opening audit view for notification recipients');
  }

  getRecipientDisplay(recipient: Recipient): string {
    return recipient ? `${recipient.name} (${recipient.email})` : '';
  }

  getActiveRecipientsCount(): number {
    let count = 0;
    if (this.recipient1) count++;
    if (this.recipient2) count++;
    if (this.recipient3) count++;
    return count;
  }

  clearRecipient(recipientNumber: number) {
    switch (recipientNumber) {
      case 1:
        this.recipient1 = null;
        break;
      case 2:
        this.recipient2 = null;
        break;
      case 3:
        this.recipient3 = null;
        break;
    }
  }

  onTestNotifications() {
    console.log('Testing notifications for all recipients');
  }

  onReset() {
    this.recipient1 = null;
    this.recipient2 = null;
    this.recipient3 = null;
    this.receivePassNotifications = false;
    console.log('Reset all notification settings');
  }

  getNotificationFrequency(): string {
    return this.receivePassNotifications ? 'All Events' : 'Failures Only';
  }

  sendTestEmail(recipient: any) {
    console.log('Sending test email to:', recipient);
  }
}
