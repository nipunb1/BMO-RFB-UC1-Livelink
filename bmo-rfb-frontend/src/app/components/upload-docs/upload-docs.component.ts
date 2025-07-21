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
  borrowerName: string = 'XYZ Transamerica Funding Ventures';
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
        key: '2024',
        label: '2024',
        icon: 'pi pi-folder',
        expanded: false,
        data: { type: 'folder', fileCount: 3 },
        children: [
          {
            key: '2024-q1',
            label: 'Q1 Documents',
            icon: 'pi pi-folder',
            data: { type: 'folder', fileCount: 2 },
            children: [
              { 
                key: '2024-q1-fs',
                label: 'Financial_Statement_Q1.pdf', 
                icon: 'pi pi-file-pdf',
                data: { type: 'file', size: '2.5 MB' }
              },
              { 
                key: '2024-q1-cr',
                label: 'Covenant_Report_Q1.xlsx', 
                icon: 'pi pi-file-excel',
                data: { type: 'file', size: '1.2 MB' }
              }
            ]
          },
          {
            key: '2024-q2',
            label: 'Q2 Documents',
            icon: 'pi pi-folder',
            data: { type: 'folder', fileCount: 1 },
            children: [
              { 
                key: '2024-q2-fs',
                label: 'Financial_Statement_Q2.pdf', 
                icon: 'pi pi-file-pdf',
                data: { type: 'file', size: '2.8 MB' }
              }
            ]
          }
        ]
      },
      {
        key: '2025',
        label: '2025',
        icon: 'pi pi-folder',
        expanded: false,
        data: { type: 'folder', fileCount: 0 },
        children: [
          {
            key: '2025-q1',
            label: 'Q1 Documents',
            icon: 'pi pi-folder',
            data: { type: 'folder', fileCount: 0 },
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

  selectedNode: TreeNode | null = null;

  onViewAllDocuments() {
    console.log('Viewing all documents');
  }

  onFileSelect(event: any) {
    console.log('Files selected:', event.files);
  }

  getTotalFileSize(files: File[]): string {
    const totalBytes = files.reduce((sum, file) => sum + file.size, 0);
    return this.formatFileSize(totalBytes);
  }

  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }

  getFileIcon(fileName: string): string {
    const extension = fileName.split('.').pop()?.toLowerCase();
    switch (extension) {
      case 'pdf':
        return 'pi pi-file-pdf';
      case 'doc':
      case 'docx':
        return 'pi pi-file-word';
      case 'xls':
      case 'xlsx':
        return 'pi pi-file-excel';
      default:
        return 'pi pi-file';
    }
  }

  removeFile(index: number) {
    console.log('Removing file at index:', index);
  }

  onNodeSelect(event: any) {
    console.log('Node selected:', event.node);
  }

  onNodeExpand(event: any) {
    console.log('Node expanded:', event.node);
  }

  getNodeIconColor(node: TreeNode): string {
    if (node.data?.type === 'folder') {
      return '#1976d2';
    }
    return '#666';
  }

  addFilesToFolder(node: TreeNode) {
    console.log('Adding files to folder:', node.label);
  }

  viewFolderContents(node: TreeNode) {
    console.log('Viewing folder contents:', node.label);
  }
}
