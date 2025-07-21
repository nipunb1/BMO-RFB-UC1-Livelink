import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TabViewModule } from 'primeng/tabview';
import { MyAssignmentsComponent } from './components/my-assignments/my-assignments.component';
import { UploadDocsComponent } from './components/upload-docs/upload-docs.component';
import { TestingResultsComponent } from './components/testing-results/testing-results.component';
import { NotificationRecipientsComponent } from './components/notification-recipients/notification-recipients.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet, 
    TabViewModule,
    MyAssignmentsComponent,
    UploadDocsComponent,
    TestingResultsComponent,
    NotificationRecipientsComponent
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  title = 'XYZ RFB Application';
  activeTabIndex = 0;
  currentPageTitle = 'My Assignments';

  private tabTitles = [
    'My Assignments',
    'Covenants, Triggers & Other Monitoring',
    'Covenants, Triggers & Other Monitoring',
    'Covenants, Triggers & Other Monitoring'
  ];

  onTabChange(event: any) {
    this.activeTabIndex = event.index;
    this.currentPageTitle = this.tabTitles[event.index];
  }
}
