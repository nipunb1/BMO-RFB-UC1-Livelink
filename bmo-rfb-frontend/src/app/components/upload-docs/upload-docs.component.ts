import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { TreeModule } from 'primeng/tree';
import { FileUploadModule } from 'primeng/fileupload';
import { TreeNode } from 'primeng/api';

@Component({
  selector: 'app-upload-docs',
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
    TreeModule,
    FileUploadModule
  ],
  templateUrl: './upload-docs.component.html',
  styleUrl: './upload-docs.component.scss'
})
export class UploadDocsComponent implements OnInit {
  borrowerName: string = 'BMO Transamerica Funding Ventures';
  borrowerUen: string = '10002/84';
  borrowerFiscalYearEnd: string = 'FY31';
  monthlyDueDays: number = 30;
  quarterlyDueDaysNA: boolean = true;
  semiAnnualDueDaysNA: boolean = true;
  annualDueDays: number = 365;

  documentTree: TreeNode[] = [];
  
  fiscalYearOptions = [
    { label: 'FY31', value: 'FY31' },
    { label: 'FY30', value: 'FY30' },
    { label: 'FY29', value: 'FY29' }
  ];

  ngOnInit() {
    this.loadDocumentTree();
  }

  loadDocumentTree() {
    this.documentTree = [
      {
        label: '2024',
        icon: 'pi pi-folder',
        expanded: false,
        children: [
          {
            label: 'Q1 Documents',
            icon: 'pi pi-folder',
            children: [
              { label: 'Financial_Statement_Q1.pdf', icon: 'pi pi-file-pdf' },
              { label: 'Covenant_Report_Q1.xlsx', icon: 'pi pi-file-excel' }
            ]
          },
          {
            label: 'Q2 Documents',
            icon: 'pi pi-folder',
            children: [
              { label: 'Financial_Statement_Q2.pdf', icon: 'pi pi-file-pdf' }
            ]
          }
        ]
      },
      {
        label: '2025',
        icon: 'pi pi-folder',
        expanded: false,
        children: [
          {
            label: 'Q1 Documents',
            icon: 'pi pi-folder',
            children: []
          }
        ]
      }
    ];
  }

  onAddToFavorites() {
    console.log('Adding to favorites:', this.borrowerName);
  }

  onSaveBorrower() {
    console.log('Saving borrower information');
  }

  onNewSearch() {
    this.borrowerName = '';
    this.borrowerUen = '';
    this.borrowerFiscalYearEnd = '';
    this.monthlyDueDays = 0;
    this.quarterlyDueDaysNA = false;
    this.semiAnnualDueDaysNA = false;
    this.annualDueDays = 0;
  }

  onWorkflowFavorites() {
    console.log('Opening workflow favorites');
  }

  onAudit() {
    console.log('Opening audit view');
  }

  onEntityFolder() {
    console.log('Opening entity folder');
  }

  onUpload(event: any) {
    console.log('Files uploaded:', event.files);
  }
}
