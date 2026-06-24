import { db } from '../models/fileDatabase.js';

export function listProjects(req, res) {
  const projects = db.all('projects').filter((project) => project.ownerId === req.user.id);
  const tasks = db.all('tasks');
  res.json(projects.map((project) => ({
    ...project,
    taskCount: tasks.filter((task) => task.projectId === project.id).length,
    doneCount: tasks.filter((task) => task.projectId === project.id && task.status === 'done').length,
  })));
}

export function createProject(req, res) {
  const project = db.insert('projects', {
    ownerId: req.user.id,
    name: req.body.name,
    description: req.body.description || '',
    status: req.body.status || 'active',
  });
  res.status(201).json(project);
}

export function updateProject(req, res, next) {
  const project = db.findById('projects', req.params.id);
  if (!project || project.ownerId !== req.user.id) {
    res.status(404);
    return next(new Error('Projekt nicht gefunden'));
  }
  const updated = db.update('projects', req.params.id, {
    name: req.body.name ?? project.name,
    description: req.body.description ?? project.description,
    status: req.body.status ?? project.status,
  });
  res.json(updated);
}

export function deleteProject(req, res, next) {
  const project = db.findById('projects', req.params.id);
  if (!project || project.ownerId !== req.user.id) {
    res.status(404);
    return next(new Error('Projekt nicht gefunden'));
  }
  db.all('tasks')
    .filter((task) => task.projectId === project.id)
    .forEach((task) => db.remove('tasks', task.id));
  db.remove('projects', project.id);
  res.status(204).send();
}
