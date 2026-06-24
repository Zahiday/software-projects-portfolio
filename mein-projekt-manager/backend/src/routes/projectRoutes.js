import { Router } from 'express';
import { createProject, deleteProject, listProjects, updateProject } from '../controllers/projectController.js';
import { requireAuth } from '../middlewares/authMiddleware.js';
import { requireFields } from '../middlewares/validate.js';

export const projectRoutes = Router();
projectRoutes.use(requireAuth);
projectRoutes.get('/', listProjects);
projectRoutes.post('/', requireFields(['name']), createProject);
projectRoutes.patch('/:id', updateProject);
projectRoutes.delete('/:id', deleteProject);
