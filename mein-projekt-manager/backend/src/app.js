import express from 'express';
import cors from 'cors';
import morgan from 'morgan';
import { authRoutes } from './routes/authRoutes.js';
import { projectRoutes } from './routes/projectRoutes.js';
import { taskRoutes } from './routes/taskRoutes.js';
import { errorHandler, notFound } from './middlewares/errorHandler.js';

export function createApp() {
  const app = express();
  app.use(cors({ origin: true, credentials: true }));
  app.use(express.json());
  app.use(morgan('dev'));

  app.get('/api/health', (req, res) => res.json({ status: 'ok', service: 'mein-projekt-manager-backend' }));
  app.use('/api/auth', authRoutes);
  app.use('/api/projects', projectRoutes);
  app.use('/api/tasks', taskRoutes);

  app.use(notFound);
  app.use(errorHandler);
  return app;
}
