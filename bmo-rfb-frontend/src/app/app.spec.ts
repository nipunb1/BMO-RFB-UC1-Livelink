import { TestBed, ComponentFixture } from '@angular/core/testing';
import { App } from './app';
import { MyAssignmentsComponent } from './components/my-assignments/my-assignments.component';
import { UploadDocsComponent } from './components/upload-docs/upload-docs.component';
import { TestingResultsComponent } from './components/testing-results/testing-results.component';
import { NotificationRecipientsComponent } from './components/notification-recipients/notification-recipients.component';
import { TabViewModule } from 'primeng/tabview';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('App', () => {
  let component: App;
  let fixture: ComponentFixture<App>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        App,
        TabViewModule,
        MyAssignmentsComponent,
        UploadDocsComponent,
        TestingResultsComponent,
        NotificationRecipientsComponent,
        NoopAnimationsModule
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(App);
    component = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should have correct initial values', () => {
    expect(component.title).toBe('BMO RFB Application');
    expect(component.activeTabIndex).toBe(0);
    expect(component.currentPageTitle).toBe('My Assignments');
  });

  it('should have correct tab titles', () => {
    const expectedTitles = [
      'My Assignments',
      'Covenants, Triggers & Other Monitoring',
      'Covenants, Triggers & Other Monitoring',
      'Covenants, Triggers & Other Monitoring'
    ];
    expect(component['tabTitles']).toEqual(expectedTitles);
  });

  it('should change tab and update page title', () => {
    const mockEvent = { index: 1 };
    component.onTabChange(mockEvent);
    
    expect(component.activeTabIndex).toBe(1);
    expect(component.currentPageTitle).toBe('Covenants, Triggers & Other Monitoring');
  });

  it('should handle tab change for all tab indices', () => {
    for (let i = 0; i < 4; i++) {
      const mockEvent = { index: i };
      component.onTabChange(mockEvent);
      
      expect(component.activeTabIndex).toBe(i);
      expect(component.currentPageTitle).toBe(component['tabTitles'][i]);
    }
  });

  it('should handle edge case tab indices', () => {
    const mockEvent = { index: 10 };
    component.onTabChange(mockEvent);
    
    expect(component.activeTabIndex).toBe(10);
    expect(component.currentPageTitle).toBeUndefined();
  });

  it('should render with correct initial state', () => {
    fixture.detectChanges();
    expect(fixture.nativeElement).toBeTruthy();
  });
});
