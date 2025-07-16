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

interface TestingResult {
  statementDesc: string;
  trackingItem: string;
  dueDays: number;
  threshold: number;
  result: number;
  passFail: string;
  comments: string;
  status: string;
}

@Component({
  selector: 'app-testing-results',
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
    CardModule
  ],
  templateUrl: './testing-results.component.html',
  styleUrl: './testing-results.component.scss'
})
export class TestingResultsComponent implements OnInit {
  borrowerName: string = 'BMO Transamerica Funding Ventures';
  borrowerUen: string = '10002/84';
  borrowerFiscalYearEnd: string = 'FY31';
  monthlyDueDays: number = 30;
  quarterlyDueDaysNA: boolean = true;
  semiAnnualDueDaysNA: boolean = true;
  annualDueDays: number = 365;

  approvalStatus: string = 'Outstanding';
  testingResults: TestingResult[] = [];
  
  fiscalYearOptions = [
    { label: 'FY31', value: 'FY31' },
    { label: 'FY30', value: 'FY30' },
    { label: 'FY29', value: 'FY29' }
  ];

  approvalStatusOptions = [
    { label: 'Outstanding', value: 'Outstanding' },
    { label: 'Defaults', value: 'Defaults' },
    { label: 'Approved', value: 'Approved' },
    { label: 'Pending Review', value: 'Pending Review' }
  ];

  passFailOptions = [
    { label: 'Pass', value: 'Pass' },
    { label: 'Fail', value: 'Fail' },
    { label: 'Pending', value: 'Pending' }
  ];

  ngOnInit() {
    this.loadTestingResults();
  }

  loadTestingResults() {
    this.testingResults = [
      {
        statementDesc: 'Q4 2024 Financial Statement Analysis - Comprehensive review of financial position including balance sheet, income statement, and cash flow analysis for covenant compliance verification.',
        trackingItem: 'BMO SK Funded Ratio EBITDA - test',
        dueDays: 30,
        threshold: 1.25,
        result: 1.45,
        passFail: 'Pass',
        comments: 'Ratio exceeds minimum threshold. Strong performance maintained throughout the quarter with consistent EBITDA growth.',
        status: 'Saved'
      },
      {
        statementDesc: 'Q4 2024 Equity Analysis - Detailed assessment of equity position relative to total assets for maintaining required capital adequacy ratios.',
        trackingItem: 'BMO Minimum Equity to Assets - test',
        dueDays: 30,
        threshold: 0.35,
        result: 0.42,
        passFail: 'Pass',
        comments: 'Equity ratio well above minimum requirement. Capital position remains strong with adequate buffer for operational flexibility.',
        status: 'Saved'
      },
      {
        statementDesc: 'Q4 2024 Asset Quality Review - Comprehensive evaluation of asset portfolio quality and debt service coverage capabilities.',
        trackingItem: 'BMO PMI Assets/Debt - Testing Limit 1',
        dueDays: 45,
        threshold: 2.0,
        result: 2.35,
        passFail: 'Pass',
        comments: 'Asset to debt ratio demonstrates strong financial stability. Portfolio quality metrics indicate low risk profile with diversified holdings.',
        status: 'Saved'
      },
      {
        statementDesc: 'Q4 2024 Liquidity Assessment - Analysis of current liquidity position and short-term debt obligations coverage.',
        trackingItem: 'BMO Current Ratio - Liquidity Test',
        dueDays: 30,
        threshold: 1.5,
        result: 1.8,
        passFail: 'Pass',
        comments: 'Current ratio indicates adequate liquidity position. Short-term obligations are well covered by current assets.',
        status: 'Saved'
      }
    ];
  }

  onAudit(result: TestingResult) {
    console.log('Auditing testing result:', result);
  }

  onFilterChange() {
    console.log('Filter changed to:', this.approvalStatus);
  }
}
