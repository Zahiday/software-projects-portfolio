import { useCallback, useEffect, useState } from 'react';
import { api } from '../services/api.js';

export function useTasks(projectId) {
  const [tasks, setTasks] = useState([]);

  const loadTasks = useCallback(async () => {
    if (!projectId) return setTasks([]);
    const res = await api.get(`/tasks?projectId=${projectId}`);
    setTasks(res.data);
  }, [projectId]);

  useEffect(() => {
    loadTasks();
  }, [loadTasks]);

  async function createTask(payload) {
    await api.post('/tasks', { ...payload, projectId });
    await loadTasks();
  }

  async function updateTask(id, patch) {
    await api.patch(`/tasks/${id}`, patch);
    await loadTasks();
  }

  async function deleteTask(id) {
    await api.delete(`/tasks/${id}`);
    await loadTasks();
  }

  return { tasks, createTask, updateTask, deleteTask, reload: loadTasks };
}
