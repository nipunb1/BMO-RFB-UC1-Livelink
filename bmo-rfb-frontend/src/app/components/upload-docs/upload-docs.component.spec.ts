import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { UploadDocsComponent } from './upload-docs.component';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputNumberModule } from 'primeng/inputnumber';
import { CardModule } from 'primeng/card';
import { TreeModule } from 'primeng/tree';
import { FileUploadModule } from 'primeng/fileupload';
import { TreeNode } from 'primeng/api';

describe('UploadDocsComponent', () => {
  let component: UploadDocsComponent;
  let fixture: ComponentFixture<UploadDocsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        UploadDocsComponent,
        FormsModule,
        InputTextModule,
        DropdownModule,
        ButtonModule,
        CheckboxModule,
        InputNumberModule,
        CardModule,
        TreeModule,
        FileUploadModule,
        NoopAnimationsModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UploadDocsComponent);
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
  });

  it('should have correct fiscal year options', () => {
    const expectedOptions = [
      { label: 'FY31', value: 'FY31' },
      { label: 'FY30', value: 'FY30' },
      { label: 'FY29', value: 'FY29' }
    ];
    expect(component.fiscalYearOptions).toEqual(expectedOptions);
  });

  it('should load document tree on init', () => {
    component.ngOnInit();
    
    expect(component.documentTree.length).toBe(2);
    expect(component.documentTree[0].key).toBe('2024');
    expect(component.documentTree[1].key).toBe('2025');
  });

  it('should create correct document tree structure', () => {
    component.loadDocumentTree();
    
    const tree2024 = component.documentTree[0];
    expect(tree2024.label).toBe('2024');
    expect(tree2024.children?.length).toBe(2);
    expect(tree2024.children?.[0].label).toBe('Q1 Documents');
    expect(tree2024.children?.[0].children?.length).toBe(2);
    
    const tree2025 = component.documentTree[1];
    expect(tree2025.label).toBe('2025');
    expect(tree2025.children?.length).toBe(1);
    expect(tree2025.children?.[0].children?.length).toBe(0);
  });

  it('should handle add to favorites action', () => {
    spyOn(console, 'log');
    
    component.onAddToFavorites();
    
    expect(console.log).toHaveBeenCalledWith('Adding to favorites:', component.borrowerName);
  });

  it('should handle save borrower action', () => {
    spyOn(console, 'log');
    
    component.onSaveBorrower();
    
    expect(console.log).toHaveBeenCalledWith('Saving borrower information');
  });

  it('should reset form on new search', () => {
    component.borrowerName = 'Test';
    component.borrowerUen = 'Test';
    component.borrowerFiscalYearEnd = 'FY30';
    component.monthlyDueDays = 45;
    component.quarterlyDueDaysNA = false;
    component.semiAnnualDueDaysNA = false;
    component.annualDueDays = 300;

    component.onNewSearch();

    expect(component.borrowerName).toBe('');
    expect(component.borrowerUen).toBe('');
    expect(component.borrowerFiscalYearEnd).toBe('');
    expect(component.monthlyDueDays).toBe(0);
    expect(component.quarterlyDueDaysNA).toBe(false);
    expect(component.semiAnnualDueDaysNA).toBe(false);
    expect(component.annualDueDays).toBe(0);
  });

  it('should handle workflow favorites action', () => {
    spyOn(console, 'log');
    
    component.onWorkflowFavorites();
    
    expect(console.log).toHaveBeenCalledWith('Opening workflow favorites');
  });

  it('should handle audit action', () => {
    spyOn(console, 'log');
    
    component.onAudit();
    
    expect(console.log).toHaveBeenCalledWith('Opening audit view');
  });

  it('should handle entity folder action', () => {
    spyOn(console, 'log');
    
    component.onEntityFolder();
    
    expect(console.log).toHaveBeenCalledWith('Opening entity folder');
  });

  it('should handle file upload', () => {
    spyOn(console, 'log');
    const mockEvent = { files: [new File(['test'], 'test.pdf')] };
    
    component.onUpload(mockEvent);
    
    expect(console.log).toHaveBeenCalledWith('Files uploaded:', mockEvent.files);
  });

  it('should handle view all documents action', () => {
    spyOn(console, 'log');
    
    component.onViewAllDocuments();
    
    expect(console.log).toHaveBeenCalledWith('Viewing all documents');
  });

  it('should handle file select', () => {
    spyOn(console, 'log');
    const mockEvent = { files: [new File(['test'], 'test.pdf')] };
    
    component.onFileSelect(mockEvent);
    
    expect(console.log).toHaveBeenCalledWith('Files selected:', mockEvent.files);
  });

  it('should calculate total file size correctly', () => {
    const files = [
      new File(['a'.repeat(1000)], 'file1.pdf'),
      new File(['b'.repeat(2000)], 'file2.pdf')
    ];
    
    const totalSize = component.getTotalFileSize(files);
    expect(totalSize).toBe('2.93 KB');
  });

  it('should format file size correctly', () => {
    expect(component.formatFileSize(0)).toBe('0 Bytes');
    expect(component.formatFileSize(1024)).toBe('1 KB');
    expect(component.formatFileSize(1048576)).toBe('1 MB');
    expect(component.formatFileSize(1073741824)).toBe('1 GB');
    expect(component.formatFileSize(500)).toBe('500 Bytes');
    expect(component.formatFileSize(1536)).toBe('1.5 KB');
  });

  it('should return correct file icon for PDF', () => {
    const icon = component.getFileIcon('document.pdf');
    expect(icon).toBe('pi pi-file-pdf');
  });

  it('should return correct file icon for Word documents', () => {
    expect(component.getFileIcon('document.doc')).toBe('pi pi-file-word');
    expect(component.getFileIcon('document.docx')).toBe('pi pi-file-word');
  });

  it('should return correct file icon for Excel documents', () => {
    expect(component.getFileIcon('document.xls')).toBe('pi pi-file-excel');
    expect(component.getFileIcon('document.xlsx')).toBe('pi pi-file-excel');
  });

  it('should return default file icon for unknown extensions', () => {
    expect(component.getFileIcon('document.txt')).toBe('pi pi-file');
    expect(component.getFileIcon('document')).toBe('pi pi-file');
  });

  it('should handle file removal', () => {
    spyOn(console, 'log');
    
    component.removeFile(0);
    
    expect(console.log).toHaveBeenCalledWith('Removing file at index:', 0);
  });

  it('should handle node selection', () => {
    spyOn(console, 'log');
    const mockEvent = { node: { key: 'test', label: 'Test Node' } };
    
    component.onNodeSelect(mockEvent);
    
    expect(console.log).toHaveBeenCalledWith('Node selected:', mockEvent.node);
  });

  it('should handle node expansion', () => {
    spyOn(console, 'log');
    const mockEvent = { node: { key: 'test', label: 'Test Node' } };
    
    component.onNodeExpand(mockEvent);
    
    expect(console.log).toHaveBeenCalledWith('Node expanded:', mockEvent.node);
  });

  it('should return correct node icon color for folder', () => {
    const folderNode: TreeNode = { data: { type: 'folder' } };
    const color = component.getNodeIconColor(folderNode);
    expect(color).toBe('#1976d2');
  });

  it('should return correct node icon color for file', () => {
    const fileNode: TreeNode = { data: { type: 'file' } };
    const color = component.getNodeIconColor(fileNode);
    expect(color).toBe('#666');
  });

  it('should handle adding files to folder', () => {
    spyOn(console, 'log');
    const mockNode: TreeNode = { key: 'test', label: 'Test Folder' };
    
    component.addFilesToFolder(mockNode);
    
    expect(console.log).toHaveBeenCalledWith('Adding files to folder:', mockNode.label);
  });

  it('should handle viewing folder contents', () => {
    spyOn(console, 'log');
    const mockNode: TreeNode = { key: 'test', label: 'Test Folder' };
    
    component.viewFolderContents(mockNode);
    
    expect(console.log).toHaveBeenCalledWith('Viewing folder contents:', mockNode.label);
  });

  it('should render component without errors', () => {
    fixture.detectChanges();
    expect(fixture.nativeElement).toBeTruthy();
  });
});
