import test from 'node:test';
import assert from 'node:assert/strict';
import request from 'supertest';
import { createApp } from '../src/app.js';
import { db } from '../src/models/fileDatabase.js';

const app = createApp();

test('health endpoint returns ok', async () => {
  const response = await request(app).get('/api/health');
  assert.equal(response.status, 200);
  assert.equal(response.body.status, 'ok');
});

test('user can register, create project and create task', async () => {
  db.reset();
  const email = `mo-${Date.now()}@example.com`;
  const register = await request(app).post('/api/auth/register').send({
    name: 'Mo',
    email,
    password: 'password123',
  });
  assert.equal(register.status, 201);
  const token = register.body.token;

  const project = await request(app)
    .post('/api/projects')
    .set('Authorization', `Bearer ${token}`)
    .send({ name: 'Portfolio App', description: 'Testprojekt' });
  assert.equal(project.status, 201);

  const task = await request(app)
    .post('/api/tasks')
    .set('Authorization', `Bearer ${token}`)
    .send({ projectId: project.body.id, title: 'README schreiben' });
  assert.equal(task.status, 201);
  assert.equal(task.body.title, 'README schreiben');
});
