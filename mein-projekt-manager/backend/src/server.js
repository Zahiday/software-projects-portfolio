import { createApp } from './app.js';
import { env } from './config/env.js';

const app = createApp();
app.listen(env.port, () => {
  console.log(`Backend läuft auf http://localhost:${env.port}`);
  console.log(`Healthcheck: http://localhost:${env.port}/api/health`);
});
