import { Router } from 'express';
import { createTask, deleteTask, listTasks, updateTask } from '../controllers/taskController.js';
import { requireAuth } from '../middlewares/authMiddleware.js';
import { requireFields } from '../middlewares/validate.js';

export const taskRoutes = Router();
taskRoutes.use(requireAuth);
taskRoutes.get('/', listTasks);
taskRoutes.post('/', requireFields(['projectId', 'title']), createTask);
taskRoutes.patch('/:id', updateTask);
taskRoutes.delete('/:id', deleteTask);
