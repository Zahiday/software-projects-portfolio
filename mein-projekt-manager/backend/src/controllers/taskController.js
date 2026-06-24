import { db } from '../models/fileDatabase.js';

function assertProjectAccess(projectId, userId) {
  const project = db.findById('projects', projectId);
  return project && project.ownerId === userId;
}

export function listTasks(req, res, next) {
  const { projectId } = req.query;
  if (projectId && !assertProjectAccess(projectId, req.user.id)) {
    res.status(404);
    return next(new Error('Projekt nicht gefunden'));
  }
  let tasks = db.all('tasks').filter((task) => task.ownerId === req.user.id);
  if (projectId) tasks = tasks.filter((task) => task.projectId === projectId);
  res.json(tasks);
}

export function createTask(req, res, next) {
  const { projectId, title, description, priority, status, dueDate } = req.body;
  if (!assertProjectAccess(projectId, req.user.id)) {
    res.status(404);
    return next(new Error('Projekt nicht gefunden'));
  }
  const task = db.insert('tasks', {
    ownerId: req.user.id,
    projectId,
    title,
    description: description || '',
    priority: priority || 'medium',
    status: status || 'todo',
    dueDate: dueDate || '',
  });
  res.status(201).json(task);
}

export function updateTask(req, res, next) {
  const task = db.findById('tasks', req.params.id);
  if (!task || task.ownerId !== req.user.id) {
    res.status(404);
    return next(new Error('Task nicht gefunden'));
  }
  const updated = db.update('tasks', req.params.id, {
    title: req.body.title ?? task.title,
    description: req.body.description ?? task.description,
    priority: req.body.priority ?? task.priority,
    status: req.body.status ?? task.status,
    dueDate: req.body.dueDate ?? task.dueDate,
  });
  res.json(updated);
}

export function deleteTask(req, res, next) {
  const task = db.findById('tasks', req.params.id);
  if (!task || task.ownerId !== req.user.id) {
    res.status(404);
    return next(new Error('Task nicht gefunden'));
  }
  db.remove('tasks', task.id);
  res.status(204).send();
}
