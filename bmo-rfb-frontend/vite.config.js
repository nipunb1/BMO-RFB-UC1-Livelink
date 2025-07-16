import { defineConfig } from 'vite';

export default defineConfig({
  server: {
    allowedHosts: [
      'assignment-management-app-tunnel-kwl0mfjg.devinapps.com',
      'localhost',
      '127.0.0.1',
      '0.0.0.0'
    ]
  }
});
