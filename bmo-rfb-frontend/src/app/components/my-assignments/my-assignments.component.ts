import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextarea } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';

interface Assignment {
  uen: string;
  borrowerName: string;
  statementDesc: string;
  trackingItem: string;
  dueDays: number;
  threshold: number;
  result: number;
  passFail: string;
  comments: string;
  submittedBy: string;
}

interface FavoriteBorrower {
  date: string;
  name: string;
}

@Component({
  selector: 'app-my-assignments',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TableModule,
    InputTextModule,
    DropdownModule,
    ButtonModule,
    CheckboxModule,
    InputTextarea,
    InputNumberModule,
    CardModule,
    DividerModule
  ],
  templateUrl: './my-assignments.component.html',
  styleUrl: './my-assignments.component.scss'
})
export class MyAssignmentsComponent implements OnInit {
  borrowerName: string = '';
  borrowerUen: string = '';
  borrowerFiscalYearEnd: string = '';
  monthlyDueDays: number = 0;
  quarterlyDueDays: number = 0;
  semiAnnualDueDays: number = 0;
  annualDueDays: number = 0;
  annualTargetDefaultLogic: boolean = false;
  restrictedAccess: string = 'N/A';

  favoriteBorrowers: FavoriteBorrower[] = [];
  assignments: Assignment[] = [];
  
  fiscalYearOptions = [
    { label: 'Borrower Fiscal Year End', value: '' },
    { label: 'FY31', value: 'FY31' },
    { label: 'FY30', value: 'FY30' },
    { label: 'FY29', value: 'FY29' }
  ];

  passFailOptions = [
    { label: 'Pass', value: 'Pass' },
    { label: 'Fail', value: 'Fail' },
    { label: 'Pending', value: 'Pending' }
  ];

  ngOnInit() {
    this.loadMockData();
  }

  loadMockData() {
    this.favoriteBorrowers = [
      { date: '3/20/2024', name: 'DB / Transamerica Funding Ventures' },
      { date: '4/30/2024', name: 'MKTG47 GRUMMAN GM' }
    ];

    this.assignments = [
      {
        uen: '10002/78',
        borrowerName: 'Transamerica Funding Ventures',
        statementDesc: 'Q4 2024 Financial Statement',
        trackingItem: 'BMO SK Funded Ratio EBITDA',
        dueDays: 30,
        threshold: 1.25,
        result: 1.45,
        passFail: 'Pass',
        comments: 'Meets minimum requirements',
        submittedBy: 'John Smith'
      },
      {
        uen: '10002/80',
        borrowerName: 'Transamerica Funding Ventures',
        statementDesc: 'Q4 2024 Covenant Testing',
        trackingItem: 'BMO Minimum Equity to Assets',
        dueDays: 30,
        threshold: 0.35,
        result: 0.42,
        passFail: 'Pass',
        comments: 'Above threshold',
        submittedBy: 'Jane Doe'
      }
    ];
  }

  onNewSearch() {
    this.borrowerName = '';
    this.borrowerUen = '';
    this.borrowerFiscalYearEnd = '';
    this.monthlyDueDays = 0;
    this.quarterlyDueDays = 0;
    this.semiAnnualDueDays = 0;
    this.annualDueDays = 0;
    this.annualTargetDefaultLogic = false;
    this.restrictedAccess = 'N/A';
  }

  onAudit(assignment: Assignment) {
    console.log('Auditing assignment:', assignment);
  }
}
